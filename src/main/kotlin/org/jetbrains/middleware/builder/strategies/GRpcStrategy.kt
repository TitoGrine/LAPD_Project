package org.jetbrains.middleware.builder.strategies

import arrow.core.Either
import com.google.protobuf.GeneratedMessageV3
import com.google.protobuf.Message
import com.google.protobuf.MessageOrBuilder
import com.google.protobuf.util.JsonFormat
import com.grpc.models.Void
import io.grpc.CallOptions
import io.grpc.ManagedChannelBuilder
import io.grpc.MethodDescriptor
import io.grpc.stub.ClientCalls
import org.jetbrains.middleware.builder.RequestData
import org.jetbrains.middleware.builder.RequestParams
import org.jetbrains.middleware.builder.RequestResponse
import java.util.concurrent.Executors
import kotlin.reflect.full.isSubclassOf

class GRpcStrategy(host: String, port: Int) :
    APITypeStrategy<GRpcStrategy.ParamsData, MessageOrBuilder> {
    private val executor = Executors.newCachedThreadPool()
    private val channel = ManagedChannelBuilder
        .forAddress(host, port)
        .usePlaintext()
        .executor(executor)
        .build()

    override suspend fun sendRequest(
        host: String,
        url: String,
        requestData: RequestData<ParamsData, MessageOrBuilder>,
        parameters: String
    ): Either<String, String> {
        val params = requestData.params.encode(parameters)
        val future = ClientCalls.futureUnaryCall(channel.newCall(params.descriptor, CallOptions.DEFAULT), params.data)
        val response = future.get()
        return if (response::class.isSubclassOf(MessageOrBuilder::class)) {
            requestData.response.decode(response as MessageOrBuilder)
        } else
            Either.Left("Wrong type of response")
    }

    data class ParamsData(val data: MessageOrBuilder, val descriptor: MethodDescriptor<MessageOrBuilder, *>)

    data class GRpcParams(private val descriptor: MethodDescriptor<MessageOrBuilder, *>) :
        RequestParams<ParamsData> {
        val data: Message.Builder? = descriptor.requestMarshaller.run {
            val field = this::class.java.getDeclaredField("defaultInstance")
            field.isAccessible = true
            val messageV3 = field.get(this) as GeneratedMessageV3
            if (messageV3 is Void)
                null
            else
                messageV3.newBuilderForType()
        }

        override fun encode(body: String): ParamsData = data?.let {
            val builder = it.clone()
            JsonFormat.parser().ignoringUnknownFields().merge(body, builder)
            return ParamsData(builder.build(), descriptor)
        } ?: ParamsData(Void.getDefaultInstance(), descriptor)

    }

    data class GRpcResponse(private val marshaller: MethodDescriptor.ReflectableMarshaller<*>) :
        RequestResponse<ParamsData, MessageOrBuilder> {
        override suspend fun decode(response: MessageOrBuilder): Either<String, String> =
            if (marshaller.messageClass.isAssignableFrom(response::class.java))
                Either.Right(JsonFormat.printer().print(response))
            else
                Either.Left("Expected type ${marshaller.messageClass}, got ${response::class.java}")
    }
}
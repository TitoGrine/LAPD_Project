package org.jetbrains.middleware.builder.strategies

import arrow.core.Either
import com.google.protobuf.GeneratedMessageV3
import com.google.protobuf.Message
import com.google.protobuf.MessageOrBuilder
import com.google.protobuf.util.JsonFormat
import io.grpc.CallOptions
import io.grpc.ManagedChannel
import io.grpc.MethodDescriptor
import io.grpc.stub.ClientCalls
import org.jetbrains.middleware.builder.RequestData
import org.jetbrains.middleware.builder.RequestParams
import org.jetbrains.middleware.builder.RequestResponse
import org.jetbrains.middleware.builder.server.requests.GRpcRequestBuilder
import kotlin.reflect.full.functions
import kotlin.reflect.full.isSubclassOf

class GRpcStrategy(private val channel: ManagedChannel) :
    APITypeStrategy<GRpcStrategy.ParamsData, MessageOrBuilder> {
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
            requestData.response.decode(future.get() as MessageOrBuilder)
        } else
            Either.Left("Wrong type of response")
    }

    data class ParamsData(val data: MessageOrBuilder, val descriptor: MethodDescriptor<MessageOrBuilder, *>)

    data class GRpcParams(private val descriptor: MethodDescriptor<MessageOrBuilder, *>) :
        RequestParams<ParamsData> {
        val data: Message.Builder = descriptor.requestMarshaller.run {
            val func = this::class.functions.first { it.name == GRpcRequestBuilder.GET_MESSAGE_PROTOTYPE }
            func.call()?.let { message ->
                if (message::class.isSubclassOf(GeneratedMessageV3::class)) run {
                    val messageV3 = message as GeneratedMessageV3
                    return@let messageV3.newBuilderForType()
                } else
                    throw IllegalArgumentException("Message is not of type GeneratedV3!")
            } ?: run {
                throw IllegalArgumentException("Message is not of type GeneratedV3!")
            }
        }

        override fun encode(body: String): ParamsData {
            val builder = data.clone()
            JsonFormat.parser().ignoringUnknownFields().merge(body, builder)
            return ParamsData(builder.build(), descriptor)
        }

    }

    data class GRpcResponse(private val marshaller: MethodDescriptor.ReflectableMarshaller<*>) :
        RequestResponse<ParamsData, MessageOrBuilder> {
        override suspend fun decode(response: MessageOrBuilder): Either<String, String> =
            Either.Right(JsonFormat.printer().print(response))

    }
}
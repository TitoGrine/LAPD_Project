package org.jetbrains.middleware.builder.strategies

import arrow.core.Either
import io.grpc.MethodDescriptor
import org.jetbrains.middleware.builder.RequestData
import org.jetbrains.middleware.builder.RequestParams
import org.jetbrains.middleware.builder.RequestResponse

class GRpcStrategy: APITypeStrategy<List<*>, Any> {
    override suspend fun sendRequest(
        host: String,
        url: String,
        requestData: RequestData<List<*>, Any>,
        parameters: String
    ): Either<String, String> {
        TODO("Not yet implemented")
    }

    data class GRpcParams(private val marshaller: MethodDescriptor.ReflectableMarshaller<*>): RequestParams<List<*>>{
        override fun encode(body: String): List<*> {
            TODO("Not yet implemented")
        }

    }

    data class GRpcResponse(private val marshaller: MethodDescriptor.ReflectableMarshaller<*>): RequestResponse<List<*>, Any>{
        override suspend fun decode(response: Any): Either<String, String> {
            TODO("Not yet implemented")
        }

    }
}
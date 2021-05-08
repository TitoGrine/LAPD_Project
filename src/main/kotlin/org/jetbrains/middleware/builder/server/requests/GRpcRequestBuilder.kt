package org.jetbrains.middleware.builder.server.requests

import com.google.protobuf.MessageOrBuilder
import io.grpc.MethodDescriptor
import io.grpc.ServiceDescriptor
import org.jetbrains.middleware.builder.RequestData
import org.jetbrains.middleware.builder.RequestDetails
import org.jetbrains.middleware.builder.server.MiddlewareServer
import org.jetbrains.middleware.builder.strategies.GRpcStrategy
import kotlin.reflect.full.functions

class GRpcRequestBuilder(private val serviceDescriptor: ServiceDescriptor) :
    RequestBuilder<GRpcStrategy.ParamsData, MessageOrBuilder> {

    companion object {
        const val GET_MESSAGE_PROTOTYPE: String = "getMessagePrototype"
    }

    @Suppress("UNCHECKED_CAST")
    override fun addRequests(builder: MiddlewareServer.Builder<GRpcStrategy.ParamsData, MessageOrBuilder>) {
        serviceDescriptor.methods.forEach { descriptor ->
            if (isReflectable(descriptor.requestMarshaller) && isReflectable(descriptor.responseMarshaller)) {
                builder.addRequest(
                    RequestDetails(
                        descriptor.fullMethodName.replace(".", "/"),
                        RequestData(
                            GRpcStrategy.GRpcParams(descriptor as MethodDescriptor<MessageOrBuilder, *>),
                            GRpcStrategy.GRpcResponse(descriptor.responseMarshaller as MethodDescriptor.ReflectableMarshaller<*>)
                        )
                    )
                )
            }
        }
    }

    private fun isReflectable(requestMarshaller: MethodDescriptor.Marshaller<out Any>): Boolean =
        requestMarshaller::class.functions.stream()
            .anyMatch { it.name == GET_MESSAGE_PROTOTYPE } && MethodDescriptor.ReflectableMarshaller::class.java.isAssignableFrom(
            requestMarshaller::class.java
        )
}
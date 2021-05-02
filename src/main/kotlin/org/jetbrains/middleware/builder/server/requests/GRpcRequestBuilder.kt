package org.jetbrains.middleware.builder.server.requests

import io.grpc.MethodDescriptor
import io.grpc.ServiceDescriptor
import org.jetbrains.middleware.builder.RequestData
import org.jetbrains.middleware.builder.RequestDetails
import org.jetbrains.middleware.builder.server.MiddlewareServer
import org.jetbrains.middleware.builder.strategies.GRpcStrategy

class GRpcRequestBuilder(private val serviceDescriptor: ServiceDescriptor) : RequestBuilder<List<*>, Any> {

    override fun addRequests(builder: MiddlewareServer.Builder<List<*>, Any>) {
        serviceDescriptor.methods.forEach { descriptor ->
            if (descriptor.requestMarshaller is MethodDescriptor.ReflectableMarshaller && descriptor.responseMarshaller is MethodDescriptor.ReflectableMarshaller) {
                builder.addRequest(
                    RequestDetails(
                        descriptor.fullMethodName,
                        RequestData(
                            GRpcStrategy.GRpcParams(descriptor.requestMarshaller as MethodDescriptor.ReflectableMarshaller<*>),
                            GRpcStrategy.GRpcResponse(descriptor.responseMarshaller as MethodDescriptor.ReflectableMarshaller<*>)
                        )
                    )
                )
            }
        }
    }
}
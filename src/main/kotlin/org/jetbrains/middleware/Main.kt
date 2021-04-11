package org.jetbrains.middleware

import com.google.protobuf.*
import com.proto.models.Colors
import org.jetbrains.middleware.builder.*
import org.jetbrains.middleware.builder.server.MiddlewareServer
import org.jetbrains.middleware.builder.strategies.ProtobufStrategy

// Example to play around with
fun main() {
    MiddlewareServer.Builder<Message>()
        .portToServe(8000)
        .setStrategy(ProtobufStrategy())
        .serverUrl("http://localhost:8080")
        .addRequest(
            RequestDetails(
                "/cmyk/random",
                RequestData(
                    ProtobufStrategy.ProtobufParams(Colors.Color.newBuilder()),
                    ProtobufStrategy.ProtobufResponse(Colors.Color.getDescriptor())
                )
            )
        )
        .addRequest(
            RequestDetails(
                "cmyk/convert",
                RequestData(
                    ProtobufStrategy.ProtobufParams(Colors.ColorConversionRequest.newBuilder()),
                    ProtobufStrategy.ProtobufResponse(Colors.ColorConversionResponse.getDescriptor())
                )
            )
        )
        .build()
        .start()
}
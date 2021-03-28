package org.jetbrains.middleware

import com.google.protobuf.*
import org.jetbrains.middleware.builder.*
import org.jetbrains.middleware.builder.strategies.ProtobufStrategy
import org.jetbrains.middleware.proto.*

// Example to play around with
fun main() {
    MiddlewareServer.Builder<Message>()
        .portToServe(8000)
        .setStrategy(ProtobufStrategy())
        .serverUrl("http://localhost:8080")
        .addRequest(
            RequestDetails(
                "/message",
                RequestData(
                    ProtobufStrategy.ProtobufParams(MessageOuterClass.Message.newBuilder()),
                    ProtobufStrategy.ProtobufResponse(MessageOuterClass.Message.getDescriptor())
                )
            )
        )
        .build()
        .start()
}
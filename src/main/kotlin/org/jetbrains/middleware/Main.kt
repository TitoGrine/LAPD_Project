package org.jetbrains.middleware

import com.google.protobuf.*
import org.jetbrains.middleware.builder.*
import org.jetbrains.middleware.builder.strategies.ProtobufStrategy
import java.io.InputStream

// Example to play around with
fun main() {
    MiddlewareServer.Builder<Message>()
        .portToServe(8000)
        .setStrategy(ProtobufStrategy())
        .serverUrl("http://localhost:8000")
        /*.addRequest(
            RequestDetails(
                "/meas",
                RequestData(
                    ProtobufStrategy.ProtobufParams(),
                    ProtobufStrategy.ProtobufResponse()
                )
            )
        )*/
        .build()
        .start()
}
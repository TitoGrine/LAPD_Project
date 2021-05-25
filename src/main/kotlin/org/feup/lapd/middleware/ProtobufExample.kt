@file:JvmName("ProtobufExample")
package org.feup.lapd.middleware

import com.google.protobuf.*
import com.proto.models.Colors
import io.ktor.client.statement.*
import org.feup.lapd.middleware.builder.*
import org.feup.lapd.middleware.builder.server.MiddlewareServer
import org.feup.lapd.middleware.builder.strategies.ProtobufStrategy

// Example to play around with
fun main() {
    MiddlewareServer.Builder<Message, HttpResponse>()
        .portToServe(8000)
        .setStrategy(ProtobufStrategy())
        .serverUrl("http://localhost:8080")
        .addRequest(
            RequestDetails(
                "/hex/random",
                RequestData(
                    ProtobufStrategy.ProtobufParams(Colors.Color.newBuilder()),
                    ProtobufStrategy.ProtobufResponse(Colors.Color.getDescriptor())
                )
            )
        )
        .addRequest(
            RequestDetails(
                "hex/convert",
                RequestData(
                    ProtobufStrategy.ProtobufParams(Colors.ColorConversionRequest.newBuilder()),
                    ProtobufStrategy.ProtobufResponse(Colors.ColorConversionResponse.getDescriptor())
                )
            )
        )
        .addRequest(
            RequestDetails(
                "hex/palette",
                RequestData(
                    ProtobufStrategy.ProtobufParams(Colors.ColorPaletteRequest.newBuilder()),
                    ProtobufStrategy.ProtobufResponse(Colors.ColorPaletteResponse.getDescriptor())
                )
            )
        )
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
        .addRequest(
            RequestDetails(
                "/hsv/random",
                RequestData(
                    ProtobufStrategy.ProtobufParams(Colors.Color.newBuilder()),
                    ProtobufStrategy.ProtobufResponse(Colors.Color.getDescriptor())
                )
            )
        )
        .addRequest(
            RequestDetails(
                "hsv/convert",
                RequestData(
                    ProtobufStrategy.ProtobufParams(Colors.ColorConversionRequest.newBuilder()),
                    ProtobufStrategy.ProtobufResponse(Colors.ColorConversionResponse.getDescriptor())
                )
            )
        )
        .addRequest(
            RequestDetails(
                "hsv/palette",
                RequestData(
                    ProtobufStrategy.ProtobufParams(Colors.ColorPaletteRequest.newBuilder()),
                    ProtobufStrategy.ProtobufResponse(Colors.ColorPaletteResponse.getDescriptor())
                )
            )
        )
        .build()
        .start()
}
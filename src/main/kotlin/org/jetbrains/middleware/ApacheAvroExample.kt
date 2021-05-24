package org.jetbrains.middleware

import com.google.protobuf.*
import com.apache.models.Colors
import io.ktor.client.statement.*
import org.jetbrains.middleware.builder.*
import org.jetbrains.middleware.builder.server.MiddlewareServer
import org.jetbrains.middleware.builder.strategies.AvroStrategy

// Example to play around with
fun main() {
    MiddlewareServer.Builder<ByteArray, HttpResponse>()
        .portToServe(6061)
        .setStrategy(AvroStrategy())
        .serverUrl("http://localhost:6060")
        .addRequest(
            RequestDetails(
                "/hex/random",
                RequestData(
                    AvroStrategy.AvroParams(Colors.Color::class.java),
                    AvroStrategy.AvroResponse(Colors.Color::class.java)
                )
            )
        )
        .addRequest(
            RequestDetails(
                "/hex/convert",
                RequestData(
                    AvroStrategy.AvroParams(Colors.ColorConversionRequest::class.java),
                    AvroStrategy.AvroResponse(Colors.ColorConversionResponse::class.java)
                )
            )
        )
            /*
        .addRequest(
            RequestDetails(
                "hex/palette",
                RequestData(
                    AvroStrategy.AvroParams(Colors.ColorPaletteRequest.newBuilder()),
                    AvroStrategy.AvroResponse(Colors.ColorPaletteResponse.getDescriptor())
                )
            )
        )
        .addRequest(
            RequestDetails(
                "/cmyk/random",
                RequestData(
                    AvroStrategy.AvroParams(Colors.Color.newBuilder()),
                    AvroStrategy.AvroResponse(Colors.Color.getDescriptor())
                )
            )
        )
        .addRequest(
            RequestDetails(
                "cmyk/convert",
                RequestData(
                    AvroStrategy.AvroParams(Colors.ColorConversionRequest.newBuilder()),
                    AvroStrategy.AvroResponse(Colors.ColorConversionResponse.getDescriptor())
                )
            )
        )
        .addRequest(
            RequestDetails(
                "/hsv/random",
                RequestData(
                    AvroStrategy.AvroParams(Colors.Color.newBuilder()),
                    AvroStrategy.AvroResponse(Colors.Color.getDescriptor())
                )
            )
        )
        .addRequest(
            RequestDetails(
                "hsv/convert",
                RequestData(
                    AvroStrategy.AvroParams(Colors.ColorConversionRequest.newBuilder()),
                    AvroStrategy.AvroResponse(Colors.ColorConversionResponse.getDescriptor())
                )
            )
        )
        .addRequest(
            RequestDetails(
                "hsv/palette",
                RequestData(
                    AvroStrategy.AvroParams(Colors.ColorPaletteRequest.newBuilder()),
                    AvroStrategy.AvroResponse(Colors.ColorPaletteResponse.getDescriptor())
                )
            )
        )
        */
        .build()
        .start()
}
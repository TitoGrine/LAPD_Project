@file:JvmName("ApacheAvroExample")
package org.jetbrains.middleware

import com.apache.models.Colors
import io.ktor.client.statement.*
import org.jetbrains.middleware.builder.RequestData
import org.jetbrains.middleware.builder.RequestDetails
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
                    AvroStrategy.AvroParams(Colors.Color::class.java, Colors.Color.serializer()),
                    AvroStrategy.AvroResponse(Colors.Color.serializer())
                )
            )
        )
        .addRequest(
            RequestDetails(
                "/hex/convert",
                RequestData(
                    AvroStrategy.AvroParams(Colors.ColorConversionRequest::class.java, Colors.ColorConversionRequest.serializer()),
                    AvroStrategy.AvroResponse(Colors.ColorConversionResponse.serializer())
                )
            )
        )
        .addRequest(
            RequestDetails(
                "/hex/palette",
                RequestData(
                    AvroStrategy.AvroParams(Colors.ColorPaletteRequest::class.java, Colors.ColorPaletteRequest.serializer()),
                    AvroStrategy.AvroResponse(Colors.ColorPaletteResponse.serializer())
                )
            )
        )
        .addRequest(
            RequestDetails(
                "/rgb/random",
                RequestData(
                    AvroStrategy.AvroParams(Colors.Color::class.java, Colors.Color.serializer()),
                    AvroStrategy.AvroResponse(Colors.Color.serializer())
                )
            )
        )
        .addRequest(
            RequestDetails(
                "/rgb/convert",
                RequestData(
                    AvroStrategy.AvroParams(Colors.ColorConversionRequest::class.java, Colors.ColorConversionRequest.serializer()),
                    AvroStrategy.AvroResponse(Colors.ColorConversionResponse.serializer())
                )
            )
        )
        .addRequest(
            RequestDetails(
                "/rgb/palette",
                RequestData(
                    AvroStrategy.AvroParams(Colors.ColorPaletteRequest::class.java, Colors.ColorPaletteRequest.serializer()),
                    AvroStrategy.AvroResponse(Colors.ColorPaletteResponse.serializer())
                )
            )
        )
        .addRequest(
            RequestDetails(
                "/cmyk/random",
                RequestData(
                    AvroStrategy.AvroParams(Colors.Color::class.java, Colors.Color.serializer()),
                    AvroStrategy.AvroResponse(Colors.Color.serializer())
                )
            )
        )
        .addRequest(
            RequestDetails(
                "/cmyk/convert",
                RequestData(
                    AvroStrategy.AvroParams(Colors.ColorConversionRequest::class.java, Colors.ColorConversionRequest.serializer()),
                    AvroStrategy.AvroResponse(Colors.ColorConversionResponse.serializer())
                )
            )
        )
        .addRequest(
            RequestDetails(
                "/cmyk/palette",
                RequestData(
                    AvroStrategy.AvroParams(Colors.ColorPaletteRequest::class.java, Colors.ColorPaletteRequest.serializer()),
                    AvroStrategy.AvroResponse(Colors.ColorPaletteResponse.serializer())
                )
            )
        )
        .addRequest(
            RequestDetails(
                "/hsv/random",
                RequestData(
                    AvroStrategy.AvroParams(Colors.Color::class.java, Colors.Color.serializer()),
                    AvroStrategy.AvroResponse(Colors.Color.serializer())
                )
            )
        )
        .addRequest(
            RequestDetails(
                "/hsv/convert",
                RequestData(
                    AvroStrategy.AvroParams(Colors.ColorConversionRequest::class.java, Colors.ColorConversionRequest.serializer()),
                    AvroStrategy.AvroResponse(Colors.ColorConversionResponse.serializer())
                )
            )
        )
        .addRequest(
            RequestDetails(
                "/hsv/palette",
                RequestData(
                    AvroStrategy.AvroParams(Colors.ColorPaletteRequest::class.java, Colors.ColorPaletteRequest.serializer()),
                    AvroStrategy.AvroResponse(Colors.ColorPaletteResponse.serializer())
                )
            )
        )
        .build()
        .start()
}
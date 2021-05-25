package org.jetbrains.middleware.builder.strategies

import arrow.core.Either
import com.google.protobuf.Descriptors
import com.google.protobuf.DynamicMessage
import org.jetbrains.middleware.builder.RequestData
import org.jetbrains.middleware.builder.RequestParams
import com.google.protobuf.Message
import com.google.protobuf.util.JsonFormat
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.content.*
import io.ktor.http.*
import org.jetbrains.middleware.builder.RequestResponse


class ProtobufStrategy : APITypeStrategy<Message, HttpResponse> {
    val client = HttpClient(CIO)

    override suspend fun sendRequest(
        host: String,
        url: String,
        requestData: RequestData<Message, HttpResponse>,
        parameters: String
    ): Either<String, String> {
        val data = requestData.params.encode(parameters)

        val response: HttpResponse = client.post {
            url("$host/$url")
            body = ByteArrayContent(data.toByteArray(), ContentType.Application.ProtoBuf)
        }

        return requestData.response.decode(response)
    }

    data class ProtobufParams(val data: Message.Builder) : RequestParams<Message> {
        override fun encode(body: String): Message {
            val builder = data.clone()
            JsonFormat.parser().ignoringUnknownFields().merge(body, builder)
            return builder.build()
        }
    }

    data class ProtobufResponse(val data: Descriptors.Descriptor) : RequestResponse<Message, HttpResponse> {
        override suspend fun decode(response: HttpResponse): Either<String, String> =
            if (response.status == HttpStatusCode.OK) {
                val responseText = response.readBytes()
                val decodedData = DynamicMessage.parseFrom(data, responseText)
                Either.Right(JsonFormat.printer().print(decodedData))
            } else {
                Either.Left(response.readText())
            }
    }
}

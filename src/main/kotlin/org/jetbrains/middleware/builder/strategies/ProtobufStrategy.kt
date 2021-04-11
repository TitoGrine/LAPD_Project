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

class ProtobufStrategy: APITypeStrategy<Message> {
    override suspend fun sendRequest(host: String, url: String, requestData: RequestData<Message>, parameters: String): Either<String, String> {
        val data = requestData.params.encode(parameters)
        val client = HttpClient(CIO);

        val response : HttpResponse = client.post{
            url("$host/$url")
            body = ByteArrayContent(data.toByteArray(), ContentType.Application.ProtoBuf)
        }

        return if (response.status == HttpStatusCode.OK) {
            val responseText = response.readBytes()
            val decodedData = requestData.response.decode(responseText)
            Either.Right(JsonFormat.printer().print(decodedData))
        } else {
            Either.Left(response.readText())
        }
    }

    data class ProtobufParams(val data: Message.Builder): RequestParams<Message> {
        override fun encode(body: String): Message {
            val builder = data.clone()
            JsonFormat.parser().ignoringUnknownFields().merge(body, builder)
            return builder.build()
        }
    }

    data class ProtobufResponse(val data: Descriptors.Descriptor): RequestResponse<Message> {
        override fun decode(body: ByteArray): Message = DynamicMessage.parseFrom(data, body)
    }
}

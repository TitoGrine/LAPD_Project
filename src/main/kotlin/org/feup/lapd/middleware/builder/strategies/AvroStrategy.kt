package org.feup.lapd.middleware.builder.strategies

import arrow.core.Either
import com.github.avrokotlin.avro4k.Avro
import com.google.gson.Gson
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.content.*
import io.ktor.http.*
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import org.feup.lapd.middleware.builder.RequestData
import org.feup.lapd.middleware.builder.RequestParams
import org.feup.lapd.middleware.builder.RequestResponse


class AvroStrategy : APITypeStrategy<ByteArray, HttpResponse> {
    val client = HttpClient(CIO)

    override suspend fun sendRequest(
        host: String,
        url: String,
        requestData: RequestData<ByteArray, HttpResponse>,
        parameters: String
    ): Either<String, String> {
        val data = requestData.params.encode(parameters)

        val response: HttpResponse = client.post {
            url("$host/$url")
            body = ByteArrayContent(data, ContentType.parse("application/*+avro"))
        }

        return requestData.response.decode(response)
    }

    data class AvroParams<T : Any>(val data: Class<T>, val serializer: SerializationStrategy<T>) :
        RequestParams<ByteArray> {
        private val jsonMapper = Gson()

        override fun encode(body: String): ByteArray {
            val message = jsonMapper.fromJson(body, data)
            return Avro.default.encodeToByteArray(serializer, message)
        }
    }

    data class AvroResponse<T : Any>(val deserializer: DeserializationStrategy<T>) :
        RequestResponse<ByteArray, HttpResponse> {
        private val jsonMapper = Gson()


        override suspend fun decode(response: HttpResponse): Either<String, String> =
            if (response.status == HttpStatusCode.OK) {
                val responseText = response.readBytes()
                val value = Avro.default.decodeFromByteArray(deserializer, responseText)
                Either.Right(jsonMapper.toJson(value))
            } else {
                Either.Left(response.readText())
            }
    }
}

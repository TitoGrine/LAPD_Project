package org.jetbrains.middleware.builder.strategies

import arrow.core.Either
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.avro.AvroFactory
import com.fasterxml.jackson.dataformat.avro.AvroMapper
import com.fasterxml.jackson.dataformat.avro.AvroSchema
import com.fasterxml.jackson.dataformat.avro.schema.AvroSchemaGenerator
import com.github.avrokotlin.avro4k.io.AvroFormat
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.content.*
import io.ktor.http.*
import org.apache.avro.Schema
import org.jetbrains.middleware.builder.RequestData
import org.jetbrains.middleware.builder.RequestParams
import org.jetbrains.middleware.builder.RequestResponse


class AvroStrategy : APITypeStrategy<ByteArray, HttpResponse> {
    val client = HttpClient(CIO)

    override suspend fun sendRequest(
        host: String,
        url: String,
        requestData: RequestData<ByteArray, HttpResponse>,
        parameters: String
    ): Either<String, String> {
        print('A')
        val data = requestData.params.encode(parameters)
        print('B')

        val response: HttpResponse = client.post {
            url("$host/$url")
            body = ByteArrayContent(data, ContentType.parse("application/*+avro"))
        }

        return requestData.response.decode(response)
    }

    data class AvroParams<T: Any> (val data: Class<T>) : RequestParams<ByteArray> {
        private var mapper = AvroMapper()

        override fun encode(body: String): ByteArray {
            print("D")
            try {
                print(mapper.writeValueAsBytes(body))
            } catch(e: Exception) {
                print(e)
            }
            print("S")
            return mapper.writeValueAsBytes(body)
        }
    }

    data class AvroResponse<T: Any> (val data: Class<T>) : RequestResponse<ByteArray, HttpResponse> {
        private val mapper = AvroMapper()

        override suspend fun decode(response: HttpResponse): Either<String, String> =
            if (response.status == HttpStatusCode.OK) {
                val responseText = response.readBytes()
                val decodedData = mapper.readerFor(data).readValue<String>(responseText)
                Either.Right(decodedData)
            } else {
                Either.Left(response.readText())
            }
    }
}

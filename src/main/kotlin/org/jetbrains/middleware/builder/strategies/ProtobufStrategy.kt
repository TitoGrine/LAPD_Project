package org.jetbrains.middleware.builder.strategies

import com.google.protobuf.Descriptors
import com.google.protobuf.DynamicMessage
import org.jetbrains.middleware.builder.RequestData
import org.jetbrains.middleware.builder.RequestParams
import com.google.protobuf.Message
import com.google.protobuf.util.JsonFormat
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import org.jetbrains.middleware.builder.RequestResponse

class ProtobufStrategy: APITypeStrategy<Message>() {
    override suspend fun sendRequest(url: String, requestData: RequestData<Message>, parameters: String): String {
        //requestData.params.encode(parameters)
        val client = HttpClient(CIO);
        val response = client.get<String>(url+"/1")
        print(requestData.response.decode(response))
        return requestData.response.decode(response).toString();
    }

    data class ProtobufParams(val data: Message.Builder): RequestParams<Message> {
        override fun encode(body: String): Message {
            val builder = data.clone()
            JsonFormat.parser().ignoringUnknownFields().merge(body, builder)
            return builder.build()
        }
    }

    data class ProtobufResponse(val data: Descriptors.Descriptor): RequestResponse<Message> {
        override fun decode(body: String): Message = DynamicMessage.parseFrom(data, body.toByteArray())
    }
}

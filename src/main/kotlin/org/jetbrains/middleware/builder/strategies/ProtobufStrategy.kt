package org.jetbrains.middleware.builder.strategies

import com.google.protobuf.Descriptors
import com.google.protobuf.DynamicMessage
import org.jetbrains.middleware.builder.RequestData
import org.jetbrains.middleware.builder.RequestParams
import com.google.protobuf.Message
import com.google.protobuf.util.JsonFormat
import org.jetbrains.middleware.builder.RequestResponse

class ProtobufStrategy<K>: APITypeStrategy<Message,K>() {
    override fun sendRequest(url: String, requestData: RequestData<Message, K>) {

    }

    data class ProtobufParams(override val data: Message.Builder): RequestParams<Message, Message.Builder> {
        override fun encode(body: String): Message {
            val builder = data.clone()
            JsonFormat.parser().ignoringUnknownFields().merge(body, builder)
            return builder.build()
        }
    }

    data class ProtobufResponse(override val data: Descriptors.Descriptor): RequestResponse<Message, Descriptors.Descriptor> {
        override fun decode(body: String): Message = DynamicMessage.parseFrom(data, body.toByteArray())
    }
}
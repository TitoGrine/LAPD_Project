package com.proto.serializers

import com.proto.models.Colors.Color
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.util.pipeline.*
import io.ktor.utils.io.*
import io.ktor.utils.io.jvm.javaio.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ColorSerializer : ContentConverter {
    override suspend fun convertForReceive(context: PipelineContext<ApplicationReceiveRequest, ApplicationCall>): Any? {
        val request = context.subject
        val channel = request.value as? ByteReadChannel ?: return null

        return withContext(Dispatchers.IO) {
            Color.parseFrom(channel.toInputStream())
        }
    }

    override suspend fun convertForSend(
        context: PipelineContext<Any, ApplicationCall>,
        contentType: ContentType,
        value: Any
    ): Any? {
        if(!contentType.match(ContentType.Application.ProtoBuf) || value !is Color)
            return null

        return (value as? Color)?.toByteArray()
    }
}
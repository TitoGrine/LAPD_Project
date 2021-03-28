package com.proto.models

import kotlinx.serialization.Serializable


@Serializable
data class Message(val id: String, val sender: String, val receiver: String, val content: String)


val messageStorage = mutableListOf<Message>()

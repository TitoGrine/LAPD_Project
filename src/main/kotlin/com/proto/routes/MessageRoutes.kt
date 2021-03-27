package com.proto.routes

import com.proto.models.Message
import com.proto.models.messageStorage
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.*

fun Route.messageRouting() {
    route("/message") {
        get {
            if(messageStorage.isNotEmpty()){
                call.respond(messageStorage)
            } else {
                call.respondText("No messages found.", status = HttpStatusCode.NotFound)
            }
        }
        get("{id}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )

            val message =
                messageStorage.find { it.id == id } ?: return@get call.respondText(
                    "No message with id $id",
                    status = HttpStatusCode.NotFound
                )

            call.respond(message)
        }
        post {
            val message = call.receive<Message>()
            messageStorage.add(message)
            call.respondText("Message stored", status = HttpStatusCode.Accepted)
        }
        delete("{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (messageStorage.removeIf { it.id == id }) {
                call.respondText("Message removed", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not found", status = HttpStatusCode.NotFound)
            }
        }
    }
}

fun Application.registerMessageRoutes() {
    routing {
        messageRouting()
    }
}
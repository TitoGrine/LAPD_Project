package org.jetbrains.middleware

import org.jetbrains.middleware.builder.*

// Example to play around with
fun main() {
    MiddlewareServer.Builder<String>()
        .portToServe(8000)
        .serverUrl("http://localhost:8000")
        .addRequest(RequestDetails("/meias", RequestData(object : RequestParams<String> {
            override fun encode(): String {
                return "Socks"
            }
        }, object : RequestResponse {
            override fun decode() {

            }
        })))
        .build()
        .start()
}
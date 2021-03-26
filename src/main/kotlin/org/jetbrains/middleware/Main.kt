package org.jetbrains.middleware

import org.jetbrains.middleware.builder.*

// Example to play around with
fun main() {
    MiddlewareServer.Builder<Int, String>()
        .portToServe(8000)
        .serverUrl("http://localhost:8000")
        .addRequest(RequestDetails("/meias", RequestData(object : RequestParams<Int, String> {
            override val data: String
                get() = "1"

            override fun encode(): Int {
                return 1
            }
        }, object : RequestResponse<Int, String> {
            override fun decode(): String = "1"

            override val data: Int
                get() = 1
        })))
        .build()
        .start()
}
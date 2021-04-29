package com.grpc.server

import io.grpc.Server
import io.grpc.ServerBuilder

class GRPCServer (
    val port: Int,
    private val server: Server = ServerBuilder.forPort(port).addService(ColorsServiceImpl()).build()
) {
    fun start() {
        server.start()

        println("Server started. Listening on port $port")

        Runtime.getRuntime().addShutdownHook(Thread {
            println("Shutting down server")
            server.shutdown()
            println("Server closed")
        })
    }

    fun blockAndAwaitTermination() {
        server.awaitTermination()
    }
}

fun main(args: Array<String>) {
    val server = GRPCServer(8808)

    server.start()
    server.blockAndAwaitTermination()
}
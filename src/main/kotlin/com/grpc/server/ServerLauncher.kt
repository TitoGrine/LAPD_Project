@file:JvmName("ServerLauncher")
package com.grpc.server

fun main(args: Array<String>) {
    val server = GRPCServer(8808)

    server.start()
    server.blockAndAwaitTermination()
}
@file:JvmName("ClientLauncher")
package com.grpc.client

import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors

fun main() {
    Executors.newCachedThreadPool().asCoroutineDispatcher().use { dispatcher ->
        GRPCClient(
            ManagedChannelBuilder.forAddress("localhost", 8808).usePlaintext(), dispatcher
        ).use { client ->
            println("Starting client on port 8808")
            client.start()
        }
    }
}
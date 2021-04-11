package org.jetbrains.middleware.builder.server.requests

import org.jetbrains.middleware.builder.server.MiddlewareServer

interface RequestBuilder<T> {
    fun addRequests(builder: MiddlewareServer.Builder<T>)
}

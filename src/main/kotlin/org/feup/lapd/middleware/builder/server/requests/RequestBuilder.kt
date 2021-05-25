package org.feup.lapd.middleware.builder.server.requests

import org.feup.lapd.middleware.builder.server.MiddlewareServer

interface RequestBuilder<T, K> {
    fun addRequests(builder: MiddlewareServer.Builder<T, K>)
}

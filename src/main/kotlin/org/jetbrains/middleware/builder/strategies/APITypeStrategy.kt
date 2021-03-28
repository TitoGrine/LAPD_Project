package org.jetbrains.middleware.builder.strategies

import org.jetbrains.middleware.builder.RequestData

abstract class APITypeStrategy<T> {
    abstract suspend fun sendRequest(url: String, requestData: RequestData<T>, parameters: String): String
}

package org.jetbrains.middleware.builder.strategies

import org.jetbrains.middleware.builder.RequestData

abstract class APITypeStrategy<T, K> {
    abstract fun sendRequest(url: String, requestData: RequestData<T, K>)
}

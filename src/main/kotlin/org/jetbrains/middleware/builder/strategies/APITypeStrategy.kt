package org.jetbrains.middleware.builder.strategies

import arrow.core.Either
import org.jetbrains.middleware.builder.RequestData

interface APITypeStrategy<T, K> {
    suspend fun sendRequest(host: String, url: String, requestData: RequestData<T, K>, parameters: String): Either<String, String>
}

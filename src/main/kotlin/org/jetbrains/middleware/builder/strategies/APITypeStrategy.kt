package org.jetbrains.middleware.builder.strategies

import arrow.core.Either
import org.jetbrains.middleware.builder.RequestData

interface APITypeStrategy<T> {
    suspend fun sendRequest(host: String, url: String, requestData: RequestData<T>, parameters: String): Either<String, String>
}

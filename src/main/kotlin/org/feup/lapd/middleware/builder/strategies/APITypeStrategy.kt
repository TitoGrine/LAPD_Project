package org.feup.lapd.middleware.builder.strategies

import arrow.core.Either
import org.feup.lapd.middleware.builder.RequestData

interface APITypeStrategy<T, K> {
    suspend fun sendRequest(host: String, url: String, requestData: RequestData<T, K>, parameters: String): Either<String, String>
}

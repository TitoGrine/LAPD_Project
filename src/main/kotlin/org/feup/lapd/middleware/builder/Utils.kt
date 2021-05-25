package org.feup.lapd.middleware.builder

inline fun <T: Any, K: Any> guardLet(vararg elements: T?, element2: K?, closure: () -> Nothing): List<T> {
    return if (elements.all { it != null } && element2 != null) {
        elements.filterNotNull()
    } else {
        closure()
    }
}

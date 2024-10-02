package com.lampotrias.gtd.tools

class SingleEvent<out T>(
    private val content: T,
) {
    private var hasBeenHandled = false

    fun getContentIfNotHandled(): T? =
        if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }

    @Suppress("unused")
    fun peekContent(): T = content
}

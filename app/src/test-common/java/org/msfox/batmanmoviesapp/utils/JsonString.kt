package org.msfox.batmanmoviesapp.utils

import okio.Okio

object JsonString {

    fun getJsonAsString(fileName: String): String {
        val inputStream = javaClass.classLoader!!
            .getResourceAsStream("api-response/$fileName")

        return Okio.buffer(Okio.source(inputStream)).readString(Charsets.UTF_8)
    }
}
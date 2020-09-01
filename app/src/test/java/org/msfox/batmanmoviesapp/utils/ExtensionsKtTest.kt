package org.msfox.batmanmoviesapp.utils

import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*

class ExtensionsKtTest {

    @Test
    fun toNextPage() {
        val list = mutableListOf<String>()
        for (i in 0..80)
            list.add("")

        val nextPageNumber = list.toNextPage(10)

        Assert.assertEquals(9, nextPageNumber)
    }
}
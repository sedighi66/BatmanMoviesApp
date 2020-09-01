package org.msfox.batmanmoviesapp.utils

import kotlin.math.floor


val <T> T.exhaustive : T
    get() = this

fun List<Any>.toNextPage(offset: Int): Int =
    (floor(this.count().toDouble() / offset) + 1).toInt()

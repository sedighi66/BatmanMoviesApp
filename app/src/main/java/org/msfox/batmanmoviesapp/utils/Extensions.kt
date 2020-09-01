package org.msfox.batmanmoviesapp.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.math.floor


val <T> T.exhaustive : T
    get() = this

fun List<Any>.toNextPage(offset: Int): Int =
    (floor(this.count().toDouble() / offset) + 1).toInt()


inline fun <T> MutableLiveData<T>.combineWith(
    liveData: LiveData<T>,
    crossinline block: (T?, T?) -> T
) {
    liveData.observeForever { this.value = block(this.value, it) }
}
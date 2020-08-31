package org.msfox.batmanmoviesapp.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import kotlinx.coroutines.delay

/**
 * Gets the value of a [LiveData] or waits for it to have one, with a timeout.
 *
 * Use this extension from host-side (JVM) tests. It's recommended to use it alongside
 * `InstantTaskExecutorRule` or a similar mechanism to execute tasks synchronously.
 */
fun <T> LiveData<T>.getOrAwaitValue(
        time: Long = 2000,
        timeUnit: TimeUnit = TimeUnit.SECONDS,
        afterObserve: () -> Unit = {}
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }
    this.observeForever(observer)

    afterObserve.invoke()

    // Don't wait indefinitely if the LiveData is not set.
    if (!latch.await(time, timeUnit)) {
        this.removeObserver(observer)
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}

/**
 * Gets the value of a [LiveData] or waits for it to have one, with a timeout.
 *
 * Use this extension from host-side (JVM) tests. It's recommended to use it alongside
 * `InstantTaskExecutorRule` or a similar mechanism to execute tasks synchronously.
 * @param forceDelay:(unit: milliSeconds) It delays liveData. It is useful when you pass liveData value
 * several times, but you need the last one not the first one.
 * @param infiniteTime: (unit: milliSeconds) It waits until the liveData gets the first value.
 */
suspend fun <T> LiveData<T>.getOrAwaitValue(
    wait: Boolean,
    forceDelay: Long = 250,
    infiniteTime: Long = 2000,
    afterObserve: () -> Unit = {}
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            if(!wait)
                this@getOrAwaitValue.removeObserver(this)
        }
    }
    this.observeForever(observer)

    afterObserve.invoke()

    // Don't wait indefinitely if the LiveData is not set.
    @Suppress("BlockingMethodInNonBlockingContext")
    if (!latch.await(infiniteTime, TimeUnit.MILLISECONDS)) {
        this.removeObserver(observer)
        throw TimeoutException("LiveData value was never set.")
    }
    else if(wait){
        delay(forceDelay)
        this.removeObserver(observer)
    }
    @Suppress("UNCHECKED_CAST")
    return data as T
}

package org.msfox.batmanmoviesapp

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * Created by mohsen on 05,July,2020
 */
class AppCoroutineDispatchers(
    val IO: CoroutineDispatcher,
    val main: CoroutineDispatcher,
    val default: CoroutineDispatcher
) {
    @Inject
    constructor():this(
        IO = Dispatchers.IO,
        main = Dispatchers.Main,
        default = Dispatchers.Default
    )


}
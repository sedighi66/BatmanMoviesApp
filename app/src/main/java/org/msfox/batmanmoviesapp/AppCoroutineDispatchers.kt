package org.msfox.batmanmoviesapp

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

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
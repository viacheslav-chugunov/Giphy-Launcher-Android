package viacheslav.chugunov.core.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface CoroutineDispatchers {
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val main: CoroutineDispatcher
    val unconfined: CoroutineDispatcher

    class Default(
        override val io: CoroutineDispatcher = Dispatchers.IO,
        override val default: CoroutineDispatcher = Dispatchers.Default,
        override val main: CoroutineDispatcher = Dispatchers.Main,
        override val unconfined: CoroutineDispatcher = Dispatchers.Unconfined
    ) : CoroutineDispatchers

    class Test(
        override val io: CoroutineDispatcher = Dispatchers.Unconfined,
        override val default: CoroutineDispatcher = Dispatchers.Unconfined,
        override val main: CoroutineDispatcher = Dispatchers.Unconfined,
        override val unconfined: CoroutineDispatcher = Dispatchers.Unconfined
    ) : CoroutineDispatchers
}
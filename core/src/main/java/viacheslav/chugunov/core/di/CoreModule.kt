package viacheslav.chugunov.core.di

import org.koin.dsl.module
import viacheslav.chugunov.core.util.CoroutineDispatchers

val CoreModule = module {
    factory<CoroutineDispatchers> {
        CoroutineDispatchers.Default()
    }
}
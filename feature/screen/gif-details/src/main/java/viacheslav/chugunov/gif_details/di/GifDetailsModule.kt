package viacheslav.chugunov.gif_details.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import viacheslav.chugunov.gif_details.ui.screen.GifDetailsViewModel

val GifDetailsModule = module {
    viewModel { params ->
        GifDetailsViewModel(params.get())
    }
}
package viacheslav.chugunov.gifs_list.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import viacheslav.chugunov.gifs_list.ui.screen.GifsListViewModel

val GifsListModule = module {
    viewModel {
        GifsListViewModel(get(), get())
    }
}
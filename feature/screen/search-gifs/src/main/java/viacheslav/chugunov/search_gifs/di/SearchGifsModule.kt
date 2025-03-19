package viacheslav.chugunov.search_gifs.di

import org.koin.dsl.module
import viacheslav.chugunov.search_gifs.ui.screen.SearchGifsViewModel

val SearchGifsModule = module {
    factory<SearchGifsViewModel> {
        SearchGifsViewModel(get())
    }
}
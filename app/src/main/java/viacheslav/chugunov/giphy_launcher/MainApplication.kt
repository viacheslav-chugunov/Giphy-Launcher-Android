package viacheslav.chugunov.giphy_launcher

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import viacheslav.chugunov.core.di.CoreModule
import viacheslav.chugunov.gif_details.di.GifDetailsModule
import viacheslav.chugunov.gifs_list.di.GifsListModule
import viacheslav.chugunov.giphy_launcher.di.AppModule
import viacheslav.chugunov.network.di.NetworkModule
import viacheslav.chugunov.search_gifs.di.SearchGifsModule

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(AppModule, CoreModule, NetworkModule, GifsListModule, GifDetailsModule, SearchGifsModule)
        }
    }

}
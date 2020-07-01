package io.github.janbarari.foodapp

import android.app.Application
import io.github.janbarari.foodapp.data.database.AppDatabase
import io.github.janbarari.foodapp.data.network.Api
import io.github.janbarari.foodapp.data.network.NetworkConnectionInterceptor
import io.github.janbarari.foodapp.data.preference.PreferenceManager
import io.github.janbarari.foodapp.data.repository.DrinkRepository
import io.github.janbarari.foodapp.data.repository.IngredientRepository
import io.github.janbarari.foodapp.ui.feature.details.DetailsViewModelFactory
import io.github.janbarari.foodapp.ui.feature.search.SearchViewModelFactory
import io.github.janbarari.foodapp.ui.host.HostViewModelFactory
import io.fabric.sdk.android.Fabric
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class App : Application(), KodeinAware {

    override fun onCreate() {
        super.onCreate()
        Fabric.with(this)
    }

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@App))
        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { Api(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { PreferenceManager(instance()) }
        bind() from singleton { DrinkRepository(instance()) }
        bind() from singleton { IngredientRepository(instance(), instance(), instance()) }
        bind() from provider { SearchViewModelFactory(instance(), instance()) }
        bind() from provider { DetailsViewModelFactory(instance()) }
        bind() from provider { HostViewModelFactory() }
    }
}
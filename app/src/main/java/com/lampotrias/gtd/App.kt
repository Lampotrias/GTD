package com.lampotrias.gtd

import android.app.Application
import com.lampotrias.gtd.di.AppModule
import com.lampotrias.gtd.di.databaseModule
import com.lampotrias.gtd.di.repositoryModule
import com.lampotrias.gtd.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            fragmentFactory()
            androidContext(this@App)
            modules(
                AppModule,
                databaseModule,
                viewModelsModule,
                repositoryModule,
            )
        }
    }
}

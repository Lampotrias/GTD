package com.lampotrias.gtd

import android.app.Application
import com.lampotrias.gtd.di.AppModule
import com.lampotrias.gtd.di.databaseModule
import com.lampotrias.gtd.di.repositoryModule
import com.lampotrias.gtd.di.viewModelsModule
import com.lampotrias.gtd.lifecycle.ActivityLifecycleDelegate
import com.lampotrias.gtd.notification.NotificationLifecycleListener
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {
    private val activityLifecycleDelegate by inject<ActivityLifecycleDelegate>()
    private val notificationLifecycleListener by inject<NotificationLifecycleListener>()

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

        activityLifecycleDelegate.addListener(notificationLifecycleListener)
    }
}

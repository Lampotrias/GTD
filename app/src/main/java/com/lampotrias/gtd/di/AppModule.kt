package com.lampotrias.gtd.di

import com.lampotrias.gtd.data.TaskAlarmManager
import com.lampotrias.gtd.lifecycle.ActivityLifecycleDelegate
import com.lampotrias.gtd.notification.NotificationLifecycleListener
import com.lampotrias.gtd.notification.NotificationSystemHelper
import com.lampotrias.gtd.ui.inbox.InputBoxFragment
import com.lampotrias.gtd.ui.listprojectselector.ListProjectsSelectorFragment
import org.koin.androidx.fragment.dsl.fragment
import org.koin.dsl.module

val AppModule =
    module {
        single<DispatcherProvider> { DefaultDispatcherProvider() }

        single { TaskAlarmManager(get()) }

        single { ActivityLifecycleDelegate() }
        factory { NotificationLifecycleListener(get()) }
        factory { NotificationSystemHelper(get()) }
        fragment { InputBoxFragment() }
        fragment { ListProjectsSelectorFragment() }
    }

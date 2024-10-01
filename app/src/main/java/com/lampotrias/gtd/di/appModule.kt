package com.lampotrias.gtd.di

import com.lampotrias.gtd.ui.inbox.InputBoxFragment
import com.lampotrias.gtd.ui.listprojectselector.ListProjectsSelectorFragment
import org.koin.androidx.fragment.dsl.fragment
import org.koin.dsl.module

val appModule = module {
    single<DispatcherProvider> { DefaultDispatcherProvider() }

    fragment { InputBoxFragment() }
    fragment { ListProjectsSelectorFragment() }
}
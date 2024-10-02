package com.lampotrias.gtd.domain.usecases

import com.lampotrias.gtd.data.database.tasks.TaskEntity.Companion.LIST_CALENDAR
import com.lampotrias.gtd.data.database.tasks.TaskEntity.Companion.LIST_INBOX
import com.lampotrias.gtd.data.database.tasks.TaskEntity.Companion.LIST_NEXT
import com.lampotrias.gtd.data.database.tasks.TaskEntity.Companion.LIST_SOMEDAY
import com.lampotrias.gtd.data.database.tasks.TaskEntity.Companion.LIST_WAITING
import com.lampotrias.gtd.di.DispatcherProvider
import com.lampotrias.gtd.domain.model.ListDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GetListsUseCase(
    @Suppress("unused") private val dispatcherProvider: DispatcherProvider,
) {
    operator fun invoke(): Flow<List<ListDomainModel>> =
        flowOf(
            listOf(
                ListDomainModel(
                    name = "Inbox",
                    code = LIST_INBOX,
                    iconName = LIST_INBOX,
                ),
                ListDomainModel(
                    name = "Next",
                    code = LIST_NEXT,
                    iconName = LIST_NEXT,
                ),
                ListDomainModel(
                    name = "Waiting",
                    code = LIST_WAITING,
                    iconName = LIST_WAITING,
                ),
                ListDomainModel(
                    name = "Calendar",
                    code = LIST_CALENDAR,
                    iconName = LIST_CALENDAR,
                ),
                ListDomainModel(
                    name = "Someday",
                    code = LIST_SOMEDAY,
                    iconName = LIST_SOMEDAY,
                ),
            ),
        )
}

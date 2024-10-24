package com.lampotrias.gtd.domain.usecases

import com.lampotrias.gtd.data.TaskAlarmManager
import com.lampotrias.gtd.di.DispatcherProvider
import com.lampotrias.gtd.domain.TaskRepository
import com.lampotrias.gtd.domain.model.TaskAddUpdateModel
import kotlinx.coroutines.withContext

class SaveTaskUseCase(
    private val taskRepository: TaskRepository,
    private val taskAlarmManager: TaskAlarmManager,
    private val dispatcherProvider: DispatcherProvider,
) {
    suspend operator fun invoke(taskAddUpdateModel: TaskAddUpdateModel) =
        withContext(dispatcherProvider.io) {
            taskRepository.insertTask(taskAddUpdateModel)
        }
}

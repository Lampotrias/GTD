package com.lampotrias.gtd.domain.usecases

import com.lampotrias.gtd.di.DispatcherProvider
import com.lampotrias.gtd.domain.TaskRepository
import kotlinx.coroutines.withContext

class UpdateTaskCompleteUseCase(
    private val taskRepository: TaskRepository,
    private val dispatcherProvider: DispatcherProvider,
) {
    suspend operator fun invoke(
        taskId: Long,
        isCompleted: Boolean,
    ) {
        withContext(dispatcherProvider.io) {
            taskRepository.updateTaskComplete(taskId, isCompleted)
        }
    }
}

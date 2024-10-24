package com.lampotrias.gtd.domain.usecases

import com.lampotrias.gtd.di.DispatcherProvider
import com.lampotrias.gtd.domain.TaskRepository
import com.lampotrias.gtd.domain.model.TaskDomainModel
import kotlinx.coroutines.flow.Flow

class GetTaskByIdUseCase(
    private val taskRepository: TaskRepository,
    @Suppress("unused")
    private val dispatcherProvider: DispatcherProvider,
) {
    operator fun invoke(taskId: Long): Flow<TaskDomainModel?> = taskRepository.getTaskById(taskId)
}

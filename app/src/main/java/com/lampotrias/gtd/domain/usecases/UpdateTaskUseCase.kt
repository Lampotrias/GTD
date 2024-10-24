package com.lampotrias.gtd.domain.usecases

import android.util.Log
import com.lampotrias.gtd.data.TaskAlarmManager
import com.lampotrias.gtd.di.DispatcherProvider
import com.lampotrias.gtd.domain.TaskRepository
import com.lampotrias.gtd.domain.model.TaskAddUpdateModel
import kotlinx.coroutines.withContext

class UpdateTaskUseCase(
    private val taskRepository: TaskRepository,
    private val taskAlarmManager: TaskAlarmManager,
    private val dispatcherProvider: DispatcherProvider,
) {
    suspend operator fun invoke(taskAddUpdateModel: TaskAddUpdateModel) =
        withContext(dispatcherProvider.io) {
            taskRepository.updateTask(taskAddUpdateModel)

            taskAddUpdateModel.notificationTime?.let {
                taskAlarmManager.setTaskAlarm(taskAddUpdateModel.taskId, it.toEpochMilliseconds())
                val ss = taskAlarmManager.isTaskAlarmSet(taskAddUpdateModel.taskId)
                Log.e("ss", ss.toString())
            }
        }
}

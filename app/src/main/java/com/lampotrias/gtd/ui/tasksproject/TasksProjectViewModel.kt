package com.lampotrias.gtd.ui.tasksproject

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampotrias.gtd.domain.ProjectsRepository
import com.lampotrias.gtd.domain.model.ProjectWithTasksDomainModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class TasksProjectScreenUi(
    val title: String = "",
    val isLoading: Boolean = false,
    val project: ProjectWithTasksDomainModel? = null,
)

class TasksProjectViewModel(
    @Suppress("unused") private val handle: SavedStateHandle,
    projectId: Long,
    projectsRepository: ProjectsRepository,
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)

    val uiState: StateFlow<TasksProjectScreenUi> =
        combine(
            projectsRepository.getAllProjectByIdWithTasks(projectId),
            _isLoading,
        ) { tasks, isLoading ->
            TasksProjectScreenUi(
                isLoading = isLoading,
                project = tasks,
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(WHILE_SUBSCRIBED_TIMEOUT),
            initialValue = TasksProjectScreenUi(isLoading = true),
        )

//    @Suppress("UNUSED_PARAMETER")
//    fun taskClick(task: TaskDomainModel) {
//        TODO("Not yet implemented")
//    }
//
//    @Suppress("UNUSED_PARAMETER")
//    fun taskFavoriteClick(task: TaskDomainModel) {
//        TODO("Not yet implemented")
//    }

//    fun taskCompleteChange(task: TaskDomainModel) {
//        viewModelScope.launch {
//            projectsRepository.invoke(task.id, !task.isCompleted)
//        }
//    }

    companion object {
        private const val WHILE_SUBSCRIBED_TIMEOUT = 5000L
    }
}

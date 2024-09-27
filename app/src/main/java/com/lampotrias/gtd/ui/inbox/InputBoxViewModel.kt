package com.lampotrias.gtd.ui.inbox

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampotrias.gtd.data.database.tasks.TaskWithTagsAndProjectEntity
import com.lampotrias.gtd.domain.TaskRepository
import com.lampotrias.gtd.domain.model.TaskDomainModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class InboxScreenUi(
    val title: String = "",
    val isLoading: Boolean = false,
    val items: List<TaskDomainModel> = emptyList()
)

class InputBoxViewModel(
    val handle: SavedStateHandle,
    private val taskRepository: TaskRepository,
    private val sss: String
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)

    val uiState: StateFlow<InboxScreenUi> = combine(taskRepository.getAllTasks(), _isLoading) { tasks, isLoading ->
        delay(2000)
        InboxScreenUi(
            isLoading = isLoading,
            items = tasks
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = InboxScreenUi(isLoading = true)
    )

    fun taskClick(task: TaskDomainModel) {
        TODO("Not yet implemented")
    }

    fun taskFavoriteClick(task: TaskDomainModel) {
        TODO("Not yet implemented")
    }

    fun taskCompleteChange(task: TaskDomainModel) {
        viewModelScope.launch {
            taskRepository.updateTaskComplete(task.id, !task.isCompleted)
        }
    }
}
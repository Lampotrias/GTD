package com.lampotrias.gtd.ui.inbox

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampotrias.gtd.domain.model.TaskDomainModel
import com.lampotrias.gtd.domain.usecases.GetInboxTasksUseCase
import com.lampotrias.gtd.domain.usecases.UpdateTaskCompleteUseCase
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
    @Suppress("unused") private val handle: SavedStateHandle,
    getInboxTasksUseCase: GetInboxTasksUseCase,
    private val updateTaskCompleteUseCase: UpdateTaskCompleteUseCase,
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)

    val uiState: StateFlow<InboxScreenUi> =
        combine(getInboxTasksUseCase.invoke(), _isLoading) { tasks, isLoading ->
            InboxScreenUi(
                isLoading = isLoading,
                items = tasks
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(WHILE_SUBSCRIBED_TIMEOUT),
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
            updateTaskCompleteUseCase.invoke(task.id, !task.isCompleted)
        }
    }

    companion object {
        private const val WHILE_SUBSCRIBED_TIMEOUT = 5000L
    }
}
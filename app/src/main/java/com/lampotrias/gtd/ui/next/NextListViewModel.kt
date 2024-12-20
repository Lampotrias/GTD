package com.lampotrias.gtd.ui.next

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampotrias.gtd.domain.model.TaskDomainModel
import com.lampotrias.gtd.domain.usecases.GetNextTasksUseCase
import com.lampotrias.gtd.domain.usecases.UpdateTaskCompleteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class NextListScreenUi(
    val title: String = "",
    val isLoading: Boolean = false,
    val items: List<TaskDomainModel> = emptyList(),
)

class NextListViewModel(
    @Suppress("unused") private val handle: SavedStateHandle,
    getNextTasksUseCase: GetNextTasksUseCase,
    private val updateTaskCompleteUseCase: UpdateTaskCompleteUseCase,
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)

    val uiState: StateFlow<NextListScreenUi> =
        combine(getNextTasksUseCase.invoke(), _isLoading) { tasks, isLoading ->
            NextListScreenUi(
                isLoading = isLoading,
                items = tasks,
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(WHILE_SUBSCRIBED_TIMEOUT),
            initialValue = NextListScreenUi(isLoading = true),
        )

    @Suppress("UNUSED_PARAMETER")
    fun taskClick(task: TaskDomainModel) {
        TODO("Not yet implemented")
    }

    @Suppress("UNUSED_PARAMETER")
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

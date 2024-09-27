package com.lampotrias.gtd.ui.addtask

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampotrias.gtd.domain.TaskRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ScreenUI(
    val isLoading: Boolean = false,
    val data: String? = null, // Можно заменить на нужный тип данных, например, список задач
    val errorMessage: String? = null
)

class TaskAddUpdateViewModel(
    @Suppress("unused") private val handle: SavedStateHandle,
    private val taskRepository: TaskRepository,
) : ViewModel() {

    private val _screenUIState = MutableStateFlow(ScreenUI())
    val uiState: StateFlow<ScreenUI> = _screenUIState

    fun saveTask(name: String, description: String) {
        _screenUIState.value = _screenUIState.value.copy(isLoading = true)

        viewModelScope.launch {
            delay(1500)
            taskRepository.insertTask(name, null, listOf(1), description, "")

            _screenUIState.value = _screenUIState.value.copy(
                data = "Задача успешно добавлена",
                isLoading = false
            )
        }
    }
}

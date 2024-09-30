package com.lampotrias.gtd.ui.addtask

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampotrias.gtd.domain.TaskRepository
import com.lampotrias.gtd.domain.model.TagDomainModel
import com.lampotrias.gtd.domain.usecases.GetCustomTagsUseCase
import com.lampotrias.gtd.tools.SingleEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ScreenUI(
    val isLoading: Boolean = false,
    val data: String? = null,
    val errorMessage: String? = null,
    val tagsDialog: SingleEvent<List<TagDomainModel>>? = null,
)

class TaskAddUpdateViewModel(
    @Suppress("unused") private val handle: SavedStateHandle,
    private val taskRepository: TaskRepository,
    private val getCustomTagsUseCase: GetCustomTagsUseCase,
) : ViewModel() {

    private val _screenUIState = MutableStateFlow(ScreenUI())
    val uiState: StateFlow<ScreenUI> = _screenUIState

    fun saveTask(
        name: String,
        description: String,
        selectedCustomTags: MutableList<TagDomainModel>
    ) {
        _screenUIState.value = _screenUIState.value.copy(isLoading = true)

        viewModelScope.launch {
            delay(FAKE_DELAY)
            taskRepository.insertTask(name, null, selectedCustomTags.map { it.id }, description, "")

            _screenUIState.value = _screenUIState.value.copy(
                data = "Задача успешно добавлена",
                isLoading = false
            )
        }
    }

    fun clickOpenDialogTags() {
        viewModelScope.launch {
            val customTags = getCustomTagsUseCase.invoke()

            _screenUIState.value = _screenUIState.value.copy(
                tagsDialog = SingleEvent(customTags)
            )
        }
    }

    companion object {
        private const val FAKE_DELAY = 500L
    }
}

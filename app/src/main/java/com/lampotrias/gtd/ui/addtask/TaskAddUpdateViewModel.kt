package com.lampotrias.gtd.ui.addtask

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampotrias.gtd.data.database.tasks.TaskEntity
import com.lampotrias.gtd.domain.ProjectsRepository
import com.lampotrias.gtd.domain.TaskRepository
import com.lampotrias.gtd.domain.model.ListDomainModel
import com.lampotrias.gtd.domain.model.ProjectDomainModel
import com.lampotrias.gtd.domain.model.TagDomainModel
import com.lampotrias.gtd.domain.model.TaskDomainModel
import com.lampotrias.gtd.domain.usecases.GetCustomTagsUseCase
import com.lampotrias.gtd.domain.usecases.GetListsUseCase
import com.lampotrias.gtd.tools.SingleEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ScreenUI(
    val isLoading: Boolean = false,
    val data: String? = null,
    val lists: List<ListDomainModel> = emptyList(),
    val projects: List<ProjectDomainModel> = emptyList(),
    val errorMessage: String? = null,
    val tagsDialog: SingleEvent<List<TagDomainModel>>? = null,
    val selectedList: ListDomainModel? = null,
    val selectedProject: ProjectDomainModel? = null
)

class TaskAddUpdateViewModel(
    @Suppress("unused") private val handle: SavedStateHandle,
    private val taskRepository: TaskRepository,
    private val getCustomTagsUseCase: GetCustomTagsUseCase,
    getListsUseCase: GetListsUseCase,
    projectsRepository: ProjectsRepository,
    updatedTask: TaskDomainModel? = null
) : ViewModel() {

    private val _innerScreenUI = MutableStateFlow(
        ScreenUI(
            selectedList = updatedTask?.list?.let {
                ListDomainModel(
                    code = it,
                    name = "Inbox",
                    iconName = ""
                )
            },
            selectedProject = updatedTask?.project
        )
    )

    val uiState: StateFlow<ScreenUI> =
        combine(
            projectsRepository.getAllProjects(),
            getListsUseCase.invoke(),
            _innerScreenUI
        ) { projects, lists, innerScreenUI ->
            innerScreenUI.copy(
                projects = projects,
                lists = lists,
                selectedList = innerScreenUI.selectedList ?: lists.firstOrNull(),
                selectedProject = innerScreenUI.selectedProject
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(WHILE_SUBSCRIBED_TIMEOUT),
            initialValue = ScreenUI()
        )

    fun saveTask(
        name: String,
        description: String,
        selectedCustomTags: MutableList<TagDomainModel>
    ) {
        _innerScreenUI.value = _innerScreenUI.value.copy(isLoading = true)

        viewModelScope.launch {
            delay(FAKE_DELAY)
            taskRepository.insertTask(
                name,
                uiState.value.selectedProject?.id,
                selectedCustomTags.map { it.id },
                description,
                uiState.value.selectedList?.code ?: TaskEntity.LIST_INBOX
            )

            _innerScreenUI.value = _innerScreenUI.value.copy(
                data = "Задача успешно добавлена",
                isLoading = false
            )
        }
    }

    fun clickOpenDialogTags() {
        viewModelScope.launch {
            val customTags = getCustomTagsUseCase.invoke()

            _innerScreenUI.value = _innerScreenUI.value.copy(
                tagsDialog = SingleEvent(customTags)
            )
        }
    }

    fun applyListProject(listCode: String, projectId: Long) {
        _innerScreenUI.value = _innerScreenUI.value.copy(
            selectedList = uiState.value.lists.firstOrNull { it.code == listCode },
            selectedProject = uiState.value.projects.firstOrNull { it.id == projectId }
        )
    }

    companion object {
        private const val FAKE_DELAY = 500L
        private const val WHILE_SUBSCRIBED_TIMEOUT = 5000L
    }
}

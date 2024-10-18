package com.lampotrias.gtd.ui.addtask

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampotrias.gtd.data.database.tasks.TaskEntity
import com.lampotrias.gtd.domain.ProjectsRepository
import com.lampotrias.gtd.domain.TaskRepository
import com.lampotrias.gtd.domain.model.ListDomainModel
import com.lampotrias.gtd.domain.model.ProjectDomainModel
import com.lampotrias.gtd.domain.model.TagDomainModel
import com.lampotrias.gtd.domain.model.TaskAddUpdateModel
import com.lampotrias.gtd.domain.model.TaskDomainModel
import com.lampotrias.gtd.domain.usecases.GetAvailableNotifyDateTimeUseCase
import com.lampotrias.gtd.domain.usecases.GetCustomTagsUseCase
import com.lampotrias.gtd.domain.usecases.GetEnergyTagsUseCase
import com.lampotrias.gtd.domain.usecases.GetListsUseCase
import com.lampotrias.gtd.domain.usecases.GetPriorityTagsUseCase
import com.lampotrias.gtd.domain.usecases.GetTimeTagsUseCase
import com.lampotrias.gtd.tools.SingleEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class ScreenUI(
    val isLoading: Boolean = false,
    val data: String? = null,
    val lists: List<ListDomainModel> = emptyList(),
    val projects: List<ProjectDomainModel> = emptyList(),
    val errorMessage: String? = null,
    val tagsDialog: SingleEvent<List<TagDomainModel>>? = null,
    val timeDialog: SingleEvent<List<TagDomainModel>>? = null,
    val priorityDialog: SingleEvent<List<TagDomainModel>>? = null,
    val energyDialog: SingleEvent<List<TagDomainModel>>? = null,
    val selectedList: ListDomainModel? = null,
    val selectedProject: ProjectDomainModel? = null,
    val currentTask: SingleEvent<TaskDomainModel>? = null,
    val selectedCustomTags: List<TagDomainModel>? = null,
    val selectedTimeTag: TagDomainModel? = null,
    val selectedPriorityTags: TagDomainModel? = null,
    val selectedEnergyTags: TagDomainModel? = null,
)

class TaskAddUpdateViewModel(
    @Suppress("unused") private val handle: SavedStateHandle,
    private val taskRepository: TaskRepository,
    private val getCustomTagsUseCase: GetCustomTagsUseCase,
    private val getTimeTagsUseCase: GetTimeTagsUseCase,
    private val priorityTagsUseCase: GetPriorityTagsUseCase,
    private val getEnergyTagsUseCase: GetEnergyTagsUseCase,
    private val getAvailableNotifyDateTime: GetAvailableNotifyDateTimeUseCase,
    getListsUseCase: GetListsUseCase,
    projectsRepository: ProjectsRepository,
    updatedTaskId: Long,
) : ViewModel() {
    private val _innerScreenUI = MutableStateFlow(ScreenUI())

    private val currentTaskFlow = taskRepository.getTaskById(updatedTaskId)

    private var firstInitComplete = false

    val uiState: StateFlow<ScreenUI> =
        combine(
            projectsRepository.getAllProjects(),
            getListsUseCase.invoke(),
            currentTaskFlow,
            _innerScreenUI,
        ) { projects, lists, currentTask, innerScreenUI ->
            val selectedList =
                innerScreenUI.selectedList ?: currentTask?.list?.let {
                    ListDomainModel(
                        name = it,
                        code = it,
                        iconName = TaskEntity.LIST_INBOX,
                    )
                }

            innerScreenUI.copy(
                currentTask =
                    if (!firstInitComplete) {
                        currentTask?.let {
                            firstInitComplete = true
                            SingleEvent(it)
                        }
                    } else {
                        null // !!!!!
                    },
                selectedEnergyTags = innerScreenUI.selectedEnergyTags ?: currentTask?.energy,
                selectedPriorityTags = innerScreenUI.selectedPriorityTags ?: currentTask?.priority,
                selectedTimeTag = innerScreenUI.selectedTimeTag ?: currentTask?.time,
                selectedCustomTags =
                    innerScreenUI.selectedCustomTags ?: currentTask?.customTags
                        ?: emptyList(),
                projects = projects,
                lists = lists,
                selectedList = selectedList,
                selectedProject = _innerScreenUI.value.selectedProject ?: currentTask?.project,
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(WHILE_SUBSCRIBED_TIMEOUT),
            initialValue = ScreenUI(),
        )

    fun saveTask(
        name: String,
        description: String,
        isCompleted: Boolean,
    ) {
        _innerScreenUI.value = _innerScreenUI.value.copy(isLoading = true)

        viewModelScope.launch {
            delay(FAKE_DELAY)
            taskRepository.insertTask(
                TaskAddUpdateModel(
                    name = name,
                    projectId = uiState.value.selectedProject?.id,
                    tagIds =
                        mutableSetOf<Long?>()
                            .apply {
                                uiState.value.selectedCustomTags?.let { customTag ->
                                    addAll(customTag.map { it.id })
                                }
                                add(uiState.value.selectedTimeTag?.id)
                                add(uiState.value.selectedPriorityTags?.id)
                                add(uiState.value.selectedEnergyTags?.id)
                            }.filterNotNull(),
                    description = description,
                    list = uiState.value.selectedList?.code ?: TaskEntity.LIST_INBOX,
                    isCompleted = isCompleted,
                ),
            )

            _innerScreenUI.value =
                _innerScreenUI.value.copy(
                    data = "Задача успешно добавлена",
                    isLoading = false,
                )
        }
    }

    fun updateTask(
        taskId: Long,
        name: String,
        description: String,
        isCompleted: Boolean,
    ) {
        _innerScreenUI.value = _innerScreenUI.value.copy(isLoading = true)
        viewModelScope.launch {
            delay(FAKE_DELAY)

            taskRepository.updateTask(
                TaskAddUpdateModel(
                    taskId = taskId,
                    name = name,
                    projectId = uiState.value.selectedProject?.id,
                    tagIds =
                        mutableSetOf<Long?>()
                            .apply {
                                uiState.value.selectedCustomTags?.let { customTag ->
                                    addAll(customTag.map { it.id })
                                }

                                add(uiState.value.selectedTimeTag?.id)
                                add(uiState.value.selectedPriorityTags?.id)
                                add(uiState.value.selectedEnergyTags?.id)
                            }.filterNotNull(),
                    description = description,
                    list = uiState.value.selectedList?.code ?: TaskEntity.LIST_INBOX,
                    isCompleted = isCompleted,
                ),
            )
            _innerScreenUI.value =
                _innerScreenUI.value.copy(
                    data = "Задача успешно добавлена",
                    isLoading = false,
                )
        }
    }

    fun clickOpenDialogTags() {
        viewModelScope.launch {
            val customTags = getCustomTagsUseCase.invoke()

            _innerScreenUI.value =
                _innerScreenUI.value.copy(
                    tagsDialog = SingleEvent(customTags),
                )
        }
    }

    fun clickOpenDialogTime() {
        viewModelScope.launch {
            val tags = getTimeTagsUseCase.invoke()

            _innerScreenUI.value =
                _innerScreenUI.value.copy(
                    timeDialog = SingleEvent(tags),
                )
        }
    }

    fun clickOpenDialogPriority() {
        viewModelScope.launch {
            val tags = priorityTagsUseCase.invoke()

            _innerScreenUI.value =
                _innerScreenUI.value.copy(
                    priorityDialog = SingleEvent(tags),
                )
        }
    }

    fun clickOpenDialogEnergy() {
        viewModelScope.launch {
            val tags = getEnergyTagsUseCase.invoke()

            _innerScreenUI.value =
                _innerScreenUI.value.copy(
                    energyDialog = SingleEvent(tags),
                )
        }
    }

    fun applyListProject(
        listCode: String,
        projectId: Long,
    ) {
        _innerScreenUI.value =
            _innerScreenUI.value.copy(
                selectedList = uiState.value.lists.firstOrNull { it.code == listCode },
                selectedProject = uiState.value.projects.firstOrNull { it.id == projectId },
            )
    }

    fun selectCustomTags(selectedTags: List<TagDomainModel>) {
        _innerScreenUI.value =
            _innerScreenUI.value.copy(
                selectedCustomTags = selectedTags,
            )
    }

    fun selectTimeTags(timeTag: TagDomainModel) {
        _innerScreenUI.value =
            _innerScreenUI.value.copy(
                selectedTimeTag = timeTag,
            )
    }

    fun selectPriorityTags(priorityTag: TagDomainModel) {
        _innerScreenUI.value =
            _innerScreenUI.value.copy(
                selectedPriorityTags = priorityTag,
            )
    }

    fun selectEnergyTags(energyTag: TagDomainModel) {
        _innerScreenUI.value =
            _innerScreenUI.value.copy(
                selectedEnergyTags = energyTag,
            )
    }

    fun clickOpenNotificationDialog() {
        viewModelScope.launch {
            val ss =
                getAvailableNotifyDateTime.invoke(
                    Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                )

            Log.e("asdasd", ss.toString())
        }
    }

    companion object {
        private const val FAKE_DELAY = 300L
        private const val WHILE_SUBSCRIBED_TIMEOUT = 5000L
    }
}

package com.lampotrias.gtd.ui.addtask

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampotrias.gtd.data.database.tasks.TaskEntity
import com.lampotrias.gtd.domain.ProjectsRepository
import com.lampotrias.gtd.domain.model.ListDomainModel
import com.lampotrias.gtd.domain.model.ProjectDomainModel
import com.lampotrias.gtd.domain.model.TagDomainModel
import com.lampotrias.gtd.domain.model.TaskAddUpdateModel
import com.lampotrias.gtd.domain.model.TaskDomainModel
import com.lampotrias.gtd.domain.usecases.GetCustomTagsUseCase
import com.lampotrias.gtd.domain.usecases.GetEnergyTagsUseCase
import com.lampotrias.gtd.domain.usecases.GetListsUseCase
import com.lampotrias.gtd.domain.usecases.GetPriorityTagsUseCase
import com.lampotrias.gtd.domain.usecases.GetTaskByIdUseCase
import com.lampotrias.gtd.domain.usecases.GetTimeTagsUseCase
import com.lampotrias.gtd.domain.usecases.SaveTaskUseCase
import com.lampotrias.gtd.domain.usecases.UpdateTaskCompleteUseCase
import com.lampotrias.gtd.domain.usecases.UpdateTaskUseCase
import com.lampotrias.gtd.tools.SingleEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
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
    val notifyDialog: SingleEvent<Unit>? = null,
    val notification: LocalDateTime? = null,
    val selectedList: ListDomainModel? = null,
    val selectedProject: ProjectDomainModel? = null,
    val currentTask: SingleEvent<TaskDomainModel>? = null,
    val subtasks: List<TaskDomainModel>? = null,
    val selectedCustomTags: List<TagDomainModel>? = null,
    val selectedTimeTag: TagDomainModel? = null,
    val selectedPriorityTags: TagDomainModel? = null,
    val selectedEnergyTags: TagDomainModel? = null,
)

class TaskAddUpdateViewModel(
    @Suppress("unused") private val handle: SavedStateHandle,
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val getCustomTagsUseCase: GetCustomTagsUseCase,
    private val saveTaskUseCase: SaveTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val getTimeTagsUseCase: GetTimeTagsUseCase,
    private val priorityTagsUseCase: GetPriorityTagsUseCase,
    private val getEnergyTagsUseCase: GetEnergyTagsUseCase,
    private val updateTaskCompleteUseCase: UpdateTaskCompleteUseCase,
    getListsUseCase: GetListsUseCase,
    projectsRepository: ProjectsRepository,
    updatedTaskId: Long,
) : ViewModel() {
    private val _innerScreenUI = MutableStateFlow(ScreenUI())

    private val currentTaskFlow = getTaskByIdUseCase.invoke(updatedTaskId)

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

            val subtasks = innerScreenUI.subtasks ?: currentTask?.subtasks

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
                subtasks = subtasks,
                projects = projects,
                lists = lists,
                notification =
                    innerScreenUI.notification ?: currentTask?.notificationTime?.toLocalDateTime(
                        TimeZone.currentSystemDefault(),
                    ),
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
            saveTaskUseCase.invoke(
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
                    notificationTime = uiState.value.notification?.toInstant(TimeZone.currentSystemDefault()),
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

            updateTaskUseCase.invoke(
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
                    notificationTime = uiState.value.notification?.toInstant(TimeZone.currentSystemDefault()),
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

    fun clickOpenNotificationDialog() {
        viewModelScope.launch {
            _innerScreenUI.value =
                _innerScreenUI.value.copy(
                    notifyDialog = SingleEvent(Unit),
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

    fun setNotification(notification: LocalDateTime) {
        _innerScreenUI.value =
            _innerScreenUI.value.copy(
                notification = notification,
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

    fun onSubtaskCompleteChange(
        subtask: TaskDomainModel,
        checked: Boolean,
    ) {
        viewModelScope.launch {
            updateTaskCompleteUseCase.invoke(subtask.id, checked)
        }
    }

    companion object {
        private const val FAKE_DELAY = 300L
        private const val WHILE_SUBSCRIBED_TIMEOUT = 5000L
    }
}

package com.lampotrias.gtd.ui.listprojectselector

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampotrias.gtd.domain.ProjectsRepository
import com.lampotrias.gtd.domain.model.ListDomainModel
import com.lampotrias.gtd.domain.model.ProjectDomainModel
import com.lampotrias.gtd.domain.usecases.GetListsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class ListProjectSelectorScreenUI(
    val selectedListId: String = "",
    val selectedProjectId: Long = 0L,
    val lists: List<ListDomainModel> = emptyList(),
    val projects: List<ProjectDomainModel> = emptyList(),
)

class ListProjectsSelectorViewModel(
    @Suppress("unused") private val handle: SavedStateHandle,
    getListsUseCase: GetListsUseCase,
    projectsRepository: ProjectsRepository,
    selectedListId: String = "",
    selectedProjectId: Long = 0L,
) : ViewModel() {

    private val selectedListIdFlow = MutableStateFlow(selectedListId)
    private val selectedProjectIdFlow = MutableStateFlow(selectedProjectId)

    val uiState: StateFlow<ListProjectSelectorScreenUI> =
        combine(
            projectsRepository.getAllProjects(),
            getListsUseCase.invoke(),
            selectedListIdFlow,
            selectedProjectIdFlow,
        ) { projects, lists, selectedListId, selectedProjectId ->
            ListProjectSelectorScreenUI(
                projects = projects,
                lists = lists,
                selectedListId = selectedListId,
                selectedProjectId = selectedProjectId
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(WHILE_SUBSCRIBED_TIMEOUT),
            initialValue = ListProjectSelectorScreenUI(
                selectedListId = selectedListId,
                selectedProjectId = selectedProjectId,
            )
        )

    fun selectProject(projectId: Long) {
        selectedProjectIdFlow.value = projectId
    }

    fun selectList(listId: String) {
        selectedListIdFlow.value = listId
    }

    companion object {
        private const val WHILE_SUBSCRIBED_TIMEOUT = 5000L
    }
}
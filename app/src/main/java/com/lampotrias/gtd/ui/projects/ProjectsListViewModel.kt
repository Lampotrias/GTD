package com.lampotrias.gtd.ui.projects

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampotrias.gtd.domain.ProjectsRepository
import com.lampotrias.gtd.domain.model.ProjectDomainModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class ProjectsListScreenUi(
    val title: String = "",
    val isLoading: Boolean = false,
    val items: List<ProjectDomainModel> = emptyList(),
)

class ProjectsListViewModel(
    @Suppress("unused") private val handle: SavedStateHandle,
    projectsRepository: ProjectsRepository,
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)

    val uiState: StateFlow<ProjectsListScreenUi> =
        combine(projectsRepository.getAllProjects(), _isLoading) { tasks, isLoading ->
            ProjectsListScreenUi(
                isLoading = isLoading,
                items = tasks,
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(WHILE_SUBSCRIBED_TIMEOUT),
            initialValue = ProjectsListScreenUi(isLoading = true),
        )

    companion object {
        private const val WHILE_SUBSCRIBED_TIMEOUT = 5000L
    }
}

package com.lampotrias.gtd.ui.projects

import com.lampotrias.gtd.domain.model.ProjectWithTasksDomainModel

interface ProjectEventListener {
    fun onProjectClick(task: ProjectWithTasksDomainModel) = Unit
}

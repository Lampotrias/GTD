package com.lampotrias.gtd.ui.projects

import com.lampotrias.gtd.domain.model.ProjectDomainModel

interface ProjectEventListener {
    fun onProjectClick(task: ProjectDomainModel) = Unit
}

package com.lampotrias.gtd.ui.addtask

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.lampotrias.gtd.domain.TaskRepository

class TaskAddUpdateViewModel(
    private val handle: SavedStateHandle,
    private val taskRepository: TaskRepository,
) : ViewModel() {

}
package com.lampotrias.gtd.ui.inbox

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampotrias.gtd.data.database.tasks.TaskDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InputBoxViewModel(
    private val taskDao: TaskDao,
    private val sss: String
) : ViewModel() {
    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                taskDao.getAllTasksWithTags()
            }
        }
    }
}
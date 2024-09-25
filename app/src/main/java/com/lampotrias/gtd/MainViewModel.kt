package com.lampotrias.gtd

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampotrias.gtd.data.database.tags.TagDao
import com.lampotrias.gtd.data.database.tasks.TaskDao
import com.lampotrias.gtd.domain.ProjectsRepository
import kotlinx.coroutines.launch

class MainViewModel(
	private val tagsDao: TagDao,
	private val taskDao: TaskDao,
	private val projectsRepository: ProjectsRepository,
) : ViewModel() {
	init {
		viewModelScope.launch {
//			projectsRepository.getAllProjects().collect {
//				Log.e("Asdasd", "$it")
//			}
			val ss = tagsDao.getAllTags()
			val ww = taskDao.getAllTasksWithTags()

			Log.e("Asdasd", "$ss")
			Log.e("Asdasd", "$ww")
//			withContext(Dispatchers.IO) {
//				tagsDao.deleteTag(
//					TagEntity(
//						id = 11,
//						name = "asda",
//						typeId = 1,
//						iconName = "",
//					)
//				)
//			}
		}
	}
}
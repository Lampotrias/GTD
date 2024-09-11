package com.lampotrias.gtd

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampotrias.gtd.data.database.tags.TagDao
import com.lampotrias.gtd.domain.ProjectsRepository
import kotlinx.coroutines.launch

class MainViewModel(
	private val tagsDao: TagDao,
	private val projectsRepository: ProjectsRepository,
) : ViewModel() {
	init {
		viewModelScope.launch {
			projectsRepository.getAllProjects().collect {
				Log.e("Asdasd", "$it")
			}
			val ss = tagsDao.getAllTags()


			Log.e("Asdasd", "$ss")
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
package com.lampotrias.gtd.domain.usecases

import com.lampotrias.gtd.di.DispatcherProvider
import com.lampotrias.gtd.domain.TagsRepository
import com.lampotrias.gtd.domain.model.TagDomainModel
import kotlinx.coroutines.withContext

class GetCustomTagsUseCase(
    private val tagsRepository: TagsRepository,
    private val dispatcherProvider: DispatcherProvider,
) {
    suspend operator fun invoke(): List<TagDomainModel> {
        return withContext(dispatcherProvider.io) {
            tagsRepository.getTagsByTypeId(CUSTOM_TAG_TYPE_ID)
        }
    }

    companion object {
        private const val CUSTOM_TAG_TYPE_ID = 1L
    }
}

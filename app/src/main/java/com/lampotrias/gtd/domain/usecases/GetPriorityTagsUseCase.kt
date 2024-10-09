package com.lampotrias.gtd.domain.usecases

import com.lampotrias.gtd.data.database.tagtypes.TagTypeEntity.Companion.PRIORITY_TAG_TYPE_ID
import com.lampotrias.gtd.di.DispatcherProvider
import com.lampotrias.gtd.domain.TagsRepository
import com.lampotrias.gtd.domain.model.TagDomainModel
import kotlinx.coroutines.withContext

class GetPriorityTagsUseCase(
    private val tagsRepository: TagsRepository,
    private val dispatcherProvider: DispatcherProvider,
) {
    suspend operator fun invoke(): List<TagDomainModel> =
        withContext(dispatcherProvider.io) {
            tagsRepository.getTagsByTypeId(PRIORITY_TAG_TYPE_ID)
        }
}

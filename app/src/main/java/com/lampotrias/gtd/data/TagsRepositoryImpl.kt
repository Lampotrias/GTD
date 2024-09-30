package com.lampotrias.gtd.data

import com.lampotrias.gtd.data.database.tags.TagsDao
import com.lampotrias.gtd.di.DispatcherProvider
import com.lampotrias.gtd.domain.TagsRepository
import com.lampotrias.gtd.domain.mappers.TagTypeMapper
import com.lampotrias.gtd.domain.model.TagDomainModel
import kotlinx.coroutines.withContext

class TagsRepositoryImpl(
    private val tagsDao: TagsDao,
    private val tagTypeMapper: TagTypeMapper,
    private val dispatcherProvider: DispatcherProvider,
) : TagsRepository {
    override suspend fun getTagsByTypeId(typeId: Long): List<TagDomainModel> {
        return withContext(dispatcherProvider.io) {
            tagsDao.getTagsByTypeId(typeId).map {
                TagDomainModel(
                    id = it.tag.id,
                    name = it.tag.name,
                    iconName = it.tag.iconName,
                    type = tagTypeMapper.toModel(it.tagType)
                )
            }
        }
    }
}

package com.lampotrias.gtd.domain

import com.lampotrias.gtd.domain.model.TagDomainModel

interface TagsRepository {
    suspend fun getTagsByTypeId(typeId: Long): List<TagDomainModel>
}

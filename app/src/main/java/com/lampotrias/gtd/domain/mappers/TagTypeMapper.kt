package com.lampotrias.gtd.domain.mappers

import com.lampotrias.gtd.data.database.tagtypes.TagTypeEntity
import com.lampotrias.gtd.domain.model.TagTypeDomainModel

class TagTypeMapper : DataDomainMapper<TagTypeDomainModel, TagTypeEntity> {
    override fun toModel(entity: TagTypeEntity): TagTypeDomainModel {
        return TagTypeDomainModel(
            id = entity.id,
            name = entity.name
        )
    }

    override fun toEntity(model: TagTypeDomainModel): TagTypeEntity {
        return TagTypeEntity(
            id = model.id,
            name = model.name
        )
    }

}

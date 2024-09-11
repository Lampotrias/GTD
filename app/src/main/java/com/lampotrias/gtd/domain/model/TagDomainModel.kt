package com.lampotrias.gtd.domain.model

data class TagDomainModel(
    val id: Long,
    val type: TagTypeDomainModel,
    val name: String,
    val iconName: String,
) {
    companion object {
//		fun fromEntity(tagEntityWithTag: TagEntityWithTag): TagDomainModel {
//			return TagDomainModel(
//				id = tagEntityWithTag.tag.id,
//				name = tagEntityWithTag.tag.name,
//				iconName = tagEntityWithTag.tag.iconName,
//				type = TagTypeDomainModel(
//					id = tagEntityWithTag.tagType.id,
//					name = tagEntityWithTag.tagType.name
//				)
//			)
//
//		}
    }
}
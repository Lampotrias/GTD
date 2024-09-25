package com.lampotrias.gtd.domain.model

data class TagDomainModel(
    val id: Long,
    val type: TagTypeDomainModel,
    val name: String,
    val iconName: String,
) {
    companion object {
    }
}
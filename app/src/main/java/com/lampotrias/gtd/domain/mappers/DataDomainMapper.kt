package com.lampotrias.gtd.domain.mappers

interface DataDomainMapper<MODEL, ENTITY> {
    fun toModel(entity: ENTITY): MODEL
    fun toEntity(model: MODEL): ENTITY
}
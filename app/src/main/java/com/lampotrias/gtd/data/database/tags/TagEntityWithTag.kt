package com.lampotrias.gtd.data.database.tags

import androidx.room.Embedded
import androidx.room.Relation
import com.lampotrias.gtd.data.database.tagtypes.TagTypeEntity

data class TagEntityWithTag(
	@Embedded val tag: TagEntity,
	@Relation(
		parentColumn = "type_id",
		entityColumn = "id"
	)
	val tagType: TagTypeEntity,
)
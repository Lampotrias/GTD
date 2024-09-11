package com.lampotrias.gtd.data.database.tagtypes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tag_types")
data class TagTypeEntity(
	@PrimaryKey(autoGenerate = true)
	val id: Long,
	@ColumnInfo(name = "tag_type_name")
	val name: String
)
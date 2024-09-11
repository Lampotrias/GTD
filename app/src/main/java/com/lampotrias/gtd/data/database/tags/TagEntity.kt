package com.lampotrias.gtd.data.database.tags

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
	tableName = "tags",
//	foreignKeys = [
//		ForeignKey(
//			entity = TagTypeEntity::class,
//			parentColumns = ["id"],
//			childColumns = ["type_id"],
//			onDelete = ForeignKey.CASCADE
//		)
//	],
//	indices = [Index(value = ["type_id"])]
)
data class TagEntity(
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "tag_id")
	val id: Long,
	@ColumnInfo(name = "type_id")
	val typeId: Long,
	@ColumnInfo(name = "tag_name")
	val name: String,
	@ColumnInfo(name = "icon_name")
	val iconName: String,
)


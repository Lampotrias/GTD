package com.lampotrias.gtd.data.database.projects

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "projects")
data class ProjectEntity(
	@PrimaryKey(autoGenerate = true)
	val id: Long,
	val name: String,
	@ColumnInfo(name = "icon_name")
	val iconName: String
)
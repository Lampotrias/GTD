package com.lampotrias.gtd.data.database.tagtypes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tag_types")
data class TagTypeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "tag_type_name")
    val name: String,
) {
    companion object {
        const val TIME_TAG_TYPE_ID = 1L
        const val PRIORITY_TAG_TYPE_ID = 2L
        const val ENERGY_TAG_TYPE_ID = 3L
        const val CUSTOM_TAG_TYPE_ID = 4L
    }
}

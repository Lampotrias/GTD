package com.lampotrias.gtd.data.database.tags

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TagDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertTag(tag: TagEntity): Long

	@Update
	suspend fun updateTag(tag: TagEntity)

	@Delete
	suspend fun deleteTag(tag: TagEntity)

	@Query("SELECT * FROM tags WHERE tag_id = :tagId")
	suspend fun getTagById(tagId: Long): TagEntityWithTag?

	@Query("SELECT * FROM tags")
	suspend fun getAllTags(): List<TagEntityWithTag>
}
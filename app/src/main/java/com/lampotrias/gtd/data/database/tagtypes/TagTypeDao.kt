package com.lampotrias.gtd.data.database.tagtypes

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TagTypeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTagType(tagType: TagTypeEntity): Long

    @Update
    suspend fun updateTagType(tagType: TagTypeEntity)

    @Delete
    suspend fun deleteTagType(tagType: TagTypeEntity)

    @Query("SELECT * FROM tag_types WHERE id = :tagTypeId")
    suspend fun getTagTypeById(tagTypeId: Long): TagTypeEntity?

    @Query("SELECT * FROM tag_types")
    suspend fun getAllTagTypes(): List<TagTypeEntity>
}

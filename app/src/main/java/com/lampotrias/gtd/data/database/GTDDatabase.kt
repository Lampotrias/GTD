package com.lampotrias.gtd.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lampotrias.gtd.data.database.projects.ProjectDao
import com.lampotrias.gtd.data.database.projects.ProjectEntity
import com.lampotrias.gtd.data.database.tags.TagEntity
import com.lampotrias.gtd.data.database.tags.TagsDao
import com.lampotrias.gtd.data.database.tagtypes.TagTypeDao
import com.lampotrias.gtd.data.database.tagtypes.TagTypeEntity
import com.lampotrias.gtd.data.database.tasks.TaskDao
import com.lampotrias.gtd.data.database.tasks.TaskEntity
import com.lampotrias.gtd.data.database.tasks.TasksSubTasksCrossRef
import com.lampotrias.gtd.data.database.tasks.TasksTagsCrossRef

@Database(
    entities = [
        TaskEntity::class,
        TagEntity::class,
        TagTypeEntity::class,
        ProjectEntity::class,
        TasksTagsCrossRef::class,
        TasksSubTasksCrossRef::class,
    ],
    version = 1,
)
@TypeConverters(
    value = [
        DatabaseConverters::class,
    ],
)
abstract class GTDDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    abstract fun tagDao(): TagsDao

    abstract fun tagTypeDao(): TagTypeDao

    abstract fun projectDao(): ProjectDao
}

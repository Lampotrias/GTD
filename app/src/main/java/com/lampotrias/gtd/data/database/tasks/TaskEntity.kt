package com.lampotrias.gtd.data.database.tasks

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "tasks",
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task_id")
    val id: Long = 0,
    val name: String,
    val list: String, // входящие, следующие действия, лист ожидания, календарь, когда-нибудь
    @ColumnInfo(name = "project_id")
    val projectId: Long?,
    val description: String,
    @ColumnInfo(name = "is_completed")
    val isCompleted: Boolean = false
) {
    companion object {
        const val LIST_INBOX = ""
        const val LIST_NEXT = "next"
        const val LIST_WAITING = "waiting"
        const val LIST_CALENDAR = "calendar"
        const val LIST_SOMEDAY = "someday"
    }
}
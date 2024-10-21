package com.lampotrias.gtd.data.database

import androidx.room.TypeConverter
import kotlinx.datetime.Instant

object DatabaseConverters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Instant? = if (value != null) Instant.fromEpochMilliseconds(value) else null

    @TypeConverter
    fun instantToTimestamp(date: Instant?): Long? = date?.toEpochMilliseconds()
}

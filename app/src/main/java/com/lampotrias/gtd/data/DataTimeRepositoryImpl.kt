package com.lampotrias.gtd.data

import com.lampotrias.gtd.domain.DataTimeRepository
import com.lampotrias.gtd.ui.datetimeplanner.TimeIntervalHolder
import com.lampotrias.gtd.ui.datetimeplanner.TimeOptions
import kotlinx.datetime.LocalTime

class DataTimeRepositoryImpl : DataTimeRepository {
    override suspend fun getTimeOptions(): TimeIntervalHolder =
        TimeIntervalHolder(
            TimeOptions.Morning(LocalTime(9, 0).toSecondOfDay()),
            TimeOptions.Afternoon(LocalTime(12, 0).toSecondOfDay()),
            TimeOptions.Evening(LocalTime(17, 0).toSecondOfDay()),
            TimeOptions.Night(LocalTime(20, 0).toSecondOfDay()),
        )
}

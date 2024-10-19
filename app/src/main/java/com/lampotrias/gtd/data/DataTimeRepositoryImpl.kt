package com.lampotrias.gtd.data

import com.lampotrias.gtd.domain.DataTimeRepository
import com.lampotrias.gtd.ui.datetimeplanner.utils.TimeIntervalHolder
import com.lampotrias.gtd.ui.datetimeplanner.utils.TimeOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.LocalTime

class DataTimeRepositoryImpl : DataTimeRepository {
    override fun getTimeOptions(): Flow<TimeIntervalHolder> =
        flowOf(
            TimeIntervalHolder(
                TimeOptions.Morning(LocalTime(9, 0).toSecondOfDay()),
                TimeOptions.Afternoon(LocalTime(12, 0).toSecondOfDay()),
                TimeOptions.Evening(LocalTime(17, 0).toSecondOfDay()),
                TimeOptions.Night(LocalTime(20, 0).toSecondOfDay()),
            ),
        )
}

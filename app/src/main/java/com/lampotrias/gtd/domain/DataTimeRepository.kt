package com.lampotrias.gtd.domain

import com.lampotrias.gtd.ui.datetimeplanner.utils.TimeIntervalHolder
import kotlinx.coroutines.flow.Flow

interface DataTimeRepository {
    fun getTimeOptions(): Flow<TimeIntervalHolder>
}

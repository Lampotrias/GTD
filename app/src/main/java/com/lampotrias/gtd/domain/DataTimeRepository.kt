package com.lampotrias.gtd.domain

import com.lampotrias.gtd.ui.datetimeplanner.TimeIntervalHolder

interface DataTimeRepository {
    suspend fun getTimeOptions(): TimeIntervalHolder
}

package com.lampotrias.gtd.ui.datetimeplanner

data class AvailableDataTimeModel(
    val availableTodayTimeIntervals: List<TimeOptions>,
    val allTimeIntervals: List<TimeOptions>,
    val availableDateIntervals: List<DateOption>,
)

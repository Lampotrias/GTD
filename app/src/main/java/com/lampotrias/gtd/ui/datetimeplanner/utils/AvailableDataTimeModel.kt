package com.lampotrias.gtd.ui.datetimeplanner.utils

data class AvailableDataTimeModel(
    val availableTodayTimeIntervals: List<TimeOptions> = emptyList(),
    val allTimeIntervals: List<TimeOptions> = emptyList(),
    val availableDateIntervals: List<DateOption> = emptyList(),
)

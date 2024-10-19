package com.lampotrias.gtd.ui.datetimeplanner

import com.lampotrias.gtd.ui.datetimeplanner.utils.AvailableDataTimeModel
import com.lampotrias.gtd.ui.datetimeplanner.utils.DateOption
import com.lampotrias.gtd.ui.datetimeplanner.utils.TimeIntervalHolder
import com.lampotrias.gtd.ui.datetimeplanner.utils.TimeOptions
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.plus
import java.time.DayOfWeek

class DataTimeNotificationProvider {
    fun get(
        currentDateTime: LocalDateTime,
        timeIntervalHolder: TimeIntervalHolder,
    ): AvailableDataTimeModel =
        AvailableDataTimeModel(
            availableTodayTimeIntervals =
                getActualTimeIntervals(
                    currentDateTime.time,
                    timeIntervalHolder,
                ),
            allTimeIntervals =
                listOf(
                    timeIntervalHolder.morning,
                    timeIntervalHolder.afternoon,
                    timeIntervalHolder.evening,
                    timeIntervalHolder.night,
                ),
            availableDateIntervals = getActualDateIntervals(currentDateTime.date),
        )

    private fun getActualTimeIntervals(
        localTime: LocalTime,
        timeOptions: TimeIntervalHolder,
    ): List<TimeOptions> {
        val currentTimeSeconds = localTime.toSecondOfDay()
        return mutableListOf<TimeOptions>().apply {
            if (timeOptions.morning.seconds > currentTimeSeconds) {
                add(timeOptions.morning)
            }

            if (timeOptions.afternoon.seconds > currentTimeSeconds) {
                add(timeOptions.afternoon)
            }

            if (timeOptions.evening.seconds > currentTimeSeconds) {
                add(timeOptions.evening)
            }

            if (timeOptions.night.seconds > currentTimeSeconds) {
                add(timeOptions.night)
            }
        }
    }

    private fun getActualDateIntervals(date: LocalDate): List<DateOption> =
        mutableListOf<DateOption>().apply {
            add(DateOption.Today(date))
            add(DateOption.Tomorrow(date.plus(1, DateTimeUnit.DAY)))

            if (date.dayOfWeek == DayOfWeek.FRIDAY) {
                add(DateOption.Sunday(date.plus(2, DateTimeUnit.DAY))) // !!
                // it's sunday: no weekend or sunday options
            } else if (date.dayOfWeek != DayOfWeek.SATURDAY) {
                add(DateOption.OnWeekend(getNextSaturday(date)))
            }

            if (date.dayOfWeek != DayOfWeek.SUNDAY) {
                add(DateOption.NextWeek(getNextMonday(date)))
            }

            add(DateOption.InTwoWeeks(getInTwoWeeksMonday(date)))
        }

    private fun getInTwoWeeksMonday(date: LocalDate): LocalDate = getNextMonday(date).plus(7, DateTimeUnit.DAY)

    private fun getNextMonday(date: LocalDate): LocalDate {
        val daysUntilMonday = DayOfWeek.MONDAY.value - date.dayOfWeek.value
        val daysToAdd = if (daysUntilMonday > 0) daysUntilMonday else 7 + daysUntilMonday
        return date.plus(daysToAdd, DateTimeUnit.DAY)
    }

    private fun getNextSaturday(date: LocalDate): LocalDate {
        val daysUntilSaturday = DayOfWeek.SATURDAY.value - date.dayOfWeek.value
        val daysToAdd = if (daysUntilSaturday > 0) daysUntilSaturday else 7 + daysUntilSaturday
        return date.plus(daysToAdd, DateTimeUnit.DAY)
    }
}

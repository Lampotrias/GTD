package com.lampotrias.gtd

import com.google.common.truth.Truth.assertThat
import com.lampotrias.gtd.ui.datetimeplanner.DataTimeNotificationProvider
import com.lampotrias.gtd.ui.datetimeplanner.utils.DateOption
import com.lampotrias.gtd.ui.datetimeplanner.utils.TimeIntervalHolder
import com.lampotrias.gtd.ui.datetimeplanner.utils.TimeOptions
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import org.junit.Test

class DateTimeTest {
    private val timeOptions =
        TimeIntervalHolder(
            morning = TimeOptions.Morning(LocalTime(9, 0).toSecondOfDay()),
            afternoon = TimeOptions.Afternoon(LocalTime(12, 0).toSecondOfDay()),
            evening = TimeOptions.Evening(LocalTime(17, 0).toSecondOfDay()),
            night = TimeOptions.Night(LocalTime(20, 0).toSecondOfDay()),
        )

    @Test
    fun getAllActualTimeIntervals() {
        val testTime =
            LocalDateTime(
                year = 2022,
                monthNumber = 1,
                dayOfMonth = 1,
                hour = 7,
                minute = 0,
                second = 0,
                nanosecond = 0,
            )

        val dataTimeProvider = DataTimeNotificationProvider()
        val result = dataTimeProvider.get(testTime, timeOptions)

        assertThat(result.availableTodayTimeIntervals.size).isEqualTo(4)

        assertThat(result.availableTodayTimeIntervals[0]).isInstanceOf(TimeOptions.Morning::class.java)
        assertThat(result.availableTodayTimeIntervals[1]).isInstanceOf(TimeOptions.Afternoon::class.java)
        assertThat(result.availableTodayTimeIntervals[2]).isInstanceOf(TimeOptions.Evening::class.java)
        assertThat(result.availableTodayTimeIntervals[3]).isInstanceOf(TimeOptions.Night::class.java)
    }

    @Test
    fun getActualTimeAfterMorningIntervals() {
        val testTime =
            LocalDateTime(
                year = 2022,
                monthNumber = 1,
                dayOfMonth = 1,
                hour = 12,
                minute = 0,
                second = 0,
                nanosecond = 0,
            )

        val dataTimeProvider = DataTimeNotificationProvider()
        val result = dataTimeProvider.get(testTime, timeOptions)

        assertThat(result.availableTodayTimeIntervals.size).isEqualTo(2)

        assertThat(result.availableTodayTimeIntervals[0]).isInstanceOf(TimeOptions.Evening::class.java)
        assertThat(result.availableTodayTimeIntervals[1]).isInstanceOf(TimeOptions.Night::class.java)

        assertThat(result.allTimeIntervals.size).isEqualTo(4)
    }

    @Test
    fun getActualTimeDinnerIntervals() {
        val testTime =
            LocalDateTime(
                year = 2022,
                monthNumber = 1,
                dayOfMonth = 1,
                hour = 17,
                minute = 0,
                second = 0,
                nanosecond = 0,
            )

        val dataTimeProvider = DataTimeNotificationProvider()
        val result = dataTimeProvider.get(testTime, timeOptions)

        assertThat(result.availableTodayTimeIntervals.size).isEqualTo(1)
        assertThat(result.availableTodayTimeIntervals[0]).isInstanceOf(TimeOptions.Night::class.java)
        assertThat(result.allTimeIntervals.size).isEqualTo(4)
    }

    @Test
    fun getActualTimeDinner23Intervals() {
        val testTime =
            LocalDateTime(
                year = 2022,
                monthNumber = 1,
                dayOfMonth = 1,
                hour = 23,
                minute = 0,
                second = 0,
                nanosecond = 0,
            )

        val dataTimeProvider = DataTimeNotificationProvider()
        val result = dataTimeProvider.get(testTime, timeOptions)

        assertThat(result.availableTodayTimeIntervals.size).isEqualTo(0)

        assertThat(result.allTimeIntervals.size).isEqualTo(4)
    }

    @Test
    fun getDateIntervalMonday() {
        val currentDate =
            LocalDateTime(
                year = 2024,
                monthNumber = 10,
                dayOfMonth = 14,
                hour = 9,
                minute = 0,
                second = 0,
                nanosecond = 0,
            )
        val dataTimeProvider = DataTimeNotificationProvider()
        val result = dataTimeProvider.get(currentDate, timeOptions)
        val actual = result.availableDateIntervals
        assertThat(actual.size).isEqualTo(5)

        assertThat(actual[0]).isInstanceOf(DateOption.Today::class.java)
        assertThat(actual[0].date.dayOfMonth).isEqualTo(14)
        assertThat(actual[1]).isInstanceOf(DateOption.Tomorrow::class.java)
        assertThat(actual[1].date.dayOfMonth).isEqualTo(15)
        assertThat(actual[2]).isInstanceOf(DateOption.OnWeekend::class.java)
        assertThat(actual[2].date.dayOfMonth).isEqualTo(19)
        assertThat(actual[3]).isInstanceOf(DateOption.NextWeek::class.java)
        assertThat(actual[3].date.dayOfMonth).isEqualTo(21)
        assertThat(actual[4]).isInstanceOf(DateOption.InTwoWeeks::class.java)
        assertThat(actual[4].date.dayOfMonth).isEqualTo(28)
    }

    @Test
    fun getDateIntervalTuesday() {
        val currentDate =
            LocalDateTime(
                year = 2024,
                monthNumber = 10,
                dayOfMonth = 15,
                hour = 9,
                minute = 0,
                second = 0,
                nanosecond = 0,
            )
        val dataTimeProvider = DataTimeNotificationProvider()
        val result = dataTimeProvider.get(currentDate, timeOptions)
        val actual = result.availableDateIntervals

        assertThat(actual.size).isEqualTo(5)

        assertThat(actual[0]).isInstanceOf(DateOption.Today::class.java)
        assertThat(actual[0].date.dayOfMonth).isEqualTo(15)
        assertThat(actual[1]).isInstanceOf(DateOption.Tomorrow::class.java)
        assertThat(actual[1].date.dayOfMonth).isEqualTo(16)
        assertThat(actual[2]).isInstanceOf(DateOption.OnWeekend::class.java)
        assertThat(actual[2].date.dayOfMonth).isEqualTo(19)
        assertThat(actual[3]).isInstanceOf(DateOption.NextWeek::class.java)
        assertThat(actual[3].date.dayOfMonth).isEqualTo(21)
        assertThat(actual[4]).isInstanceOf(DateOption.InTwoWeeks::class.java)
        assertThat(actual[4].date.dayOfMonth).isEqualTo(28)
    }

    @Test
    fun getDateIntervalWednesday() {
        val currentDate =
            LocalDateTime(
                year = 2024,
                monthNumber = 10,
                dayOfMonth = 16,
                hour = 9,
                minute = 0,
                second = 0,
                nanosecond = 0,
            )
        val dataTimeProvider = DataTimeNotificationProvider()
        val result = dataTimeProvider.get(currentDate, timeOptions)
        val actual = result.availableDateIntervals
        assertThat(actual.size).isEqualTo(5)

        assertThat(actual[0]).isInstanceOf(DateOption.Today::class.java)
        assertThat(actual[0].date.dayOfMonth).isEqualTo(16)
        assertThat(actual[1]).isInstanceOf(DateOption.Tomorrow::class.java)
        assertThat(actual[1].date.dayOfMonth).isEqualTo(17)
        assertThat(actual[2]).isInstanceOf(DateOption.OnWeekend::class.java)
        assertThat(actual[2].date.dayOfMonth).isEqualTo(19)
        assertThat(actual[3]).isInstanceOf(DateOption.NextWeek::class.java)
        assertThat(actual[3].date.dayOfMonth).isEqualTo(21)
        assertThat(actual[4]).isInstanceOf(DateOption.InTwoWeeks::class.java)
        assertThat(actual[4].date.dayOfMonth).isEqualTo(28)
    }

    @Test
    fun getDateIntervalThursday() {
        val currentDate =
            LocalDateTime(
                year = 2024,
                monthNumber = 10,
                dayOfMonth = 17,
                hour = 9,
                minute = 0,
                second = 0,
                nanosecond = 0,
            )
        val dataTimeProvider = DataTimeNotificationProvider()
        val result = dataTimeProvider.get(currentDate, timeOptions)
        val actual = result.availableDateIntervals
        assertThat(actual.size).isEqualTo(5)

        assertThat(actual[0]).isInstanceOf(DateOption.Today::class.java)
        assertThat(actual[0].date.dayOfMonth).isEqualTo(17)
        assertThat(actual[1]).isInstanceOf(DateOption.Tomorrow::class.java)
        assertThat(actual[1].date.dayOfMonth).isEqualTo(18)
        assertThat(actual[2]).isInstanceOf(DateOption.OnWeekend::class.java)
        assertThat(actual[2].date.dayOfMonth).isEqualTo(19)
        assertThat(actual[3]).isInstanceOf(DateOption.NextWeek::class.java)
        assertThat(actual[3].date.dayOfMonth).isEqualTo(21)
        assertThat(actual[4]).isInstanceOf(DateOption.InTwoWeeks::class.java)
        assertThat(actual[4].date.dayOfMonth).isEqualTo(28)
    }

    @Test
    fun getDateIntervalFriday() {
        val currentDate =
            LocalDateTime(
                year = 2024,
                monthNumber = 10,
                dayOfMonth = 18,
                hour = 9,
                minute = 0,
                second = 0,
                nanosecond = 0,
            )
        val dataTimeProvider = DataTimeNotificationProvider()
        val result = dataTimeProvider.get(currentDate, timeOptions)
        val actual = result.availableDateIntervals
        assertThat(actual.size).isEqualTo(5)

        assertThat(actual[0]).isInstanceOf(DateOption.Today::class.java)
        assertThat(actual[0].date.dayOfMonth).isEqualTo(18)
        assertThat(actual[1]).isInstanceOf(DateOption.Tomorrow::class.java)
        assertThat(actual[1].date.dayOfMonth).isEqualTo(19)
        assertThat(actual[2]).isInstanceOf(DateOption.Sunday::class.java)
        assertThat(actual[2].date.dayOfMonth).isEqualTo(20)
        assertThat(actual[3]).isInstanceOf(DateOption.NextWeek::class.java)
        assertThat(actual[3].date.dayOfMonth).isEqualTo(21)
        assertThat(actual[4]).isInstanceOf(DateOption.InTwoWeeks::class.java)
        assertThat(actual[4].date.dayOfMonth).isEqualTo(28)
    }

    @Test
    fun getDateIntervalSaturday() {
        val currentDate =
            LocalDateTime(
                year = 2024,
                monthNumber = 10,
                dayOfMonth = 19,
                hour = 9,
                minute = 0,
                second = 0,
                nanosecond = 0,
            )
        val dataTimeProvider = DataTimeNotificationProvider()
        val result = dataTimeProvider.get(currentDate, timeOptions)
        val actual = result.availableDateIntervals
        assertThat(actual.size).isEqualTo(4)

        assertThat(actual[0]).isInstanceOf(DateOption.Today::class.java)
        assertThat(actual[0].date.dayOfMonth).isEqualTo(19)
        assertThat(actual[1]).isInstanceOf(DateOption.Tomorrow::class.java)
        assertThat(actual[1].date.dayOfMonth).isEqualTo(20)
        assertThat(actual[2]).isInstanceOf(DateOption.NextWeek::class.java)
        assertThat(actual[2].date.dayOfMonth).isEqualTo(21)
        assertThat(actual[3]).isInstanceOf(DateOption.InTwoWeeks::class.java)
        assertThat(actual[3].date.dayOfMonth).isEqualTo(28)
    }

    @Test
    fun getDateIntervalSunday() {
        val currentDate =
            LocalDateTime(
                year = 2024,
                monthNumber = 10,
                dayOfMonth = 20,
                hour = 7,
                minute = 0,
                second = 0,
                nanosecond = 0,
            )
        val dataTimeProvider = DataTimeNotificationProvider()
        val result = dataTimeProvider.get(currentDate, timeOptions)
        val actual = result.availableDateIntervals
        assertThat(actual.size).isEqualTo(4)

        assertThat(actual[0]).isInstanceOf(DateOption.Today::class.java)
        assertThat(actual[0].date.dayOfMonth).isEqualTo(20)
        assertThat(actual[1]).isInstanceOf(DateOption.Tomorrow::class.java)
        assertThat(actual[1].date.dayOfMonth).isEqualTo(21)
        assertThat(actual[2]).isInstanceOf(DateOption.OnWeekend::class.java)
        assertThat(actual[2].date.dayOfMonth).isEqualTo(26)
        assertThat(actual[3]).isInstanceOf(DateOption.InTwoWeeks::class.java)
        assertThat(actual[3].date.dayOfMonth).isEqualTo(28)
    }
}

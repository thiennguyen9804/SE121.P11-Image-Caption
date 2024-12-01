package com.example.se121p11new.core.presentation.utils

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.datetime.Clock
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object StringFromTime {
    private val instant = Clock.System.now()
    private val timeZone = TimeZone.currentSystemDefault() // Lấy múi giờ hiện tại
    private val today = instant.toLocalDateTime(timeZone)

    fun buildPictureName() : String {
        val day = today.dayOfMonth.toString().padStart(2, '0') // Đảm bảo 2 chữ số
        val month = today.monthNumber.toString().padStart(2, '0')
        val yearLastTwoDigits = (today.year % 100).toString().padStart(2, '0')
        return "PTR-${day}${month}${yearLastTwoDigits}"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun buildDateTimeString(): String {
        val currentTime = today
        val time = currentTime.time
        val hour = time.hour.toString().padStart(2, '0')
        val minute = time.minute.toString().padStart(2, '0')

        val dayOfWeek = getVietnameseDayOfWeek(currentTime.date.dayOfWeek)
        val day = currentTime.date.dayOfMonth.toString().padStart(2, '0')
        val month = currentTime.date.monthNumber.toString().padStart(2, '0')
        val year = currentTime.date.year

        return "$hour:$minute, $dayOfWeek, ngày $day tháng $month, $year."
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getVietnameseDayOfWeek(dayOfWeek: DayOfWeek): String {
        return when (dayOfWeek) {
            DayOfWeek.MONDAY -> "Thứ hai"
            DayOfWeek.TUESDAY -> "Thứ ba"
            DayOfWeek.WEDNESDAY -> "Thứ tư"
            DayOfWeek.THURSDAY -> "Thứ năm"
            DayOfWeek.FRIDAY -> "Thứ sáu"
            DayOfWeek.SATURDAY -> "Thứ bảy"
            DayOfWeek.SUNDAY -> "Chủ nhật"
        }
    }
}
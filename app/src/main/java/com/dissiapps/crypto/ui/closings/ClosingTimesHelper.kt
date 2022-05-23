package com.dissiapps.crypto.ui.closings

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.delay
import java.util.*


class ClosingTimesHelper {

    var hourly = mutableStateOf(Clock(0L))
    val daily = mutableStateOf(Clock(0L))
    val weekly = mutableStateOf(Clock(0L))
    val monthly = mutableStateOf(Clock(0L))

    private fun getHourlyClock(): Clock {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("utc"))
        val currentTime = calendar.timeInMillis
        val targetHour = calendar.get(Calendar.HOUR).plus(1)
        calendar.apply {
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.HOUR, targetHour)
        }
        val timeLeft = calendar.timeInMillis - currentTime
        return Clock(timeLeft)
    }

    private fun getDailyClock(): Clock {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("utc"))
        val currentTime = calendar.timeInMillis
        val targetDay = calendar.get(Calendar.DAY_OF_YEAR).plus(1)
        calendar.apply {
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.DAY_OF_YEAR, targetDay)
        }
        val timeLeft = calendar.timeInMillis - currentTime
        return Clock(timeLeft)
    }

    private fun getWeeklyClock(): Clock {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("utc"))
        val currentTime = calendar.timeInMillis
        val targetDay = calendar.get(Calendar.DAY_OF_YEAR).plus(7)
        calendar.apply {
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.DAY_OF_YEAR, targetDay)
        }
        val timeLeft = calendar.timeInMillis - currentTime
        return Clock(timeLeft)
    }

    private fun getMonthlyClock(): Clock {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("utc"))
        val currentTime = calendar.timeInMillis
        val targetMonth = calendar.get(Calendar.MONTH) + 1 % 12
        calendar.apply {
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.MONTH, targetMonth)
            if (targetMonth == 0)
                set(Calendar.YEAR, get(Calendar.YEAR) + 1)
        }
        val timeLeft = calendar.timeInMillis - currentTime
        return Clock(timeLeft)
    }

    suspend fun startTimers() {
        while (true){
            hourly.value = getHourlyClock()
            daily.value = getDailyClock()
            weekly.value = getWeeklyClock()
            monthly.value = getMonthlyClock()
            delay(1000)
        }
    }

}
package com.dissiapps.crypto.ui.closings

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.delay
import java.util.*


class ClosingTimesHelper {

    private val timeZone = TimeZone.getTimeZone("utc")
    var hourly = mutableStateOf(Clock(0L))
    val daily = mutableStateOf(Clock(0L))
    val weekly = mutableStateOf(Clock(0L))
    val monthly = mutableStateOf(Clock(0L))

    var nextHour = 0L
    var nextDay = 0L
    var nextWeek = 0L
    var nextMonth = 0L

    init {
        updateNextHour()
        updateNextDay()
        updateNextWeek()
        updateNextMonth()
    }

    private fun updateNextHour(){
        val calendar = Calendar.getInstance(timeZone)
        val targetHour = calendar.get(Calendar.HOUR).plus(1)
        calendar.apply {
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.HOUR, targetHour)
        }
        nextHour = calendar.timeInMillis
    }

    private fun updateNextDay(){
        val calendar = Calendar.getInstance(timeZone)
        val targetDay = calendar.get(Calendar.DAY_OF_YEAR).plus(1)
        calendar.apply {
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.DAY_OF_YEAR, targetDay)
        }
        nextDay = calendar.timeInMillis
    }

    private fun updateNextWeek(){
        val calendar = Calendar.getInstance(timeZone)
        val targetDay = calendar.get(Calendar.DAY_OF_YEAR).plus(7)
        calendar.apply {
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.DAY_OF_YEAR, targetDay)
        }
        nextWeek = calendar.timeInMillis
    }

    private fun updateNextMonth(){
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("utc"))
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
        nextMonth = calendar.timeInMillis
    }

    private fun getHourlyClock(currentTime: Long): Clock {
        val timeLeft = nextHour - currentTime
        if(timeLeft <= 0L) updateNextHour()
        return Clock(timeLeft)
    }

    private fun getDailyClock(currentTime: Long): Clock {
        val timeLeft = nextDay - currentTime
        if(timeLeft <= 0L) updateNextDay()
        return Clock(timeLeft)
    }

    private fun getWeeklyClock(currentTime: Long): Clock {
        val timeLeft = nextWeek - currentTime
        if(timeLeft <= 0L) updateNextWeek()
        return Clock(timeLeft)
    }

    private fun getMonthlyClock(currentTime: Long): Clock {
        val timeLeft = nextMonth - currentTime
        if(timeLeft <= 0L) updateNextMonth()
        return Clock(timeLeft)
    }

    suspend fun startTimers() {
        while (true){
            val currentTime = Calendar.getInstance(timeZone).timeInMillis // TODO: use object that updates time
            Log.e("TAG", "startTimers: $currentTime")
            hourly.value = getHourlyClock(currentTime)
            daily.value = getDailyClock(currentTime)
            weekly.value = getWeeklyClock(currentTime)
            monthly.value = getMonthlyClock(currentTime)
            delay(1000)
        }
    }

}
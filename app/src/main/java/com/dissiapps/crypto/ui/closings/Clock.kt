package com.dissiapps.crypto.ui.closings


data class Clock(val durationMillis: Long){

    private val millisInSecond = 1000
    private val millisInMinute = millisInSecond * 60
    private val millisInHour = millisInMinute * 60
    private val millisInDay = millisInHour * 24

    val days = getDaysLeft()
    val hours = getHoursLeft()
    val minutes = getMinutesLeft()
    val seconds = getSecondsLeft()


    private fun getSecondsLeft(): String {
        return String.format("%02d", (durationMillis / millisInSecond) % 60)
    }

    private fun getMinutesLeft(): String {
        return String.format("%02d", (durationMillis / millisInMinute) % 60)
    }

    private fun getHoursLeft(): String {
        return String.format("%02d", (durationMillis / millisInHour) % 24)
    }

    private fun getDaysLeft(): String {
        return String.format("%02d", durationMillis / millisInDay)
    }

    override fun toString(): String {
        return "$days:$hours:$minutes:$seconds"
    }

    override fun hashCode(): Int {
        return durationMillis.toInt()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Clock

        if (durationMillis != other.durationMillis) return false
        if (millisInSecond != other.millisInSecond) return false
        if (millisInMinute != other.millisInMinute) return false
        if (millisInHour != other.millisInHour) return false
        if (millisInDay != other.millisInDay) return false
        if (days != other.days) return false
        if (hours != other.hours) return false
        if (minutes != other.minutes) return false
        if (seconds != other.seconds) return false

        return true
    }
}
package com.dissiapps.crypto.ui.closings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dissiapps.crypto.R
import com.dissiapps.crypto.ui.common.MainTitleText
import java.text.SimpleDateFormat
import java.util.*

@Preview(showBackground = true)
@Composable
fun LabeledClockRowPreview() {
    LabeledClockRow(
        label = "Hourly Close",
        dayValue = "00",
        hourValue = "00",
        minValue = "00",
        secValue = "00",
        closingTime = 0L,
        shouldShowDate = true
    )
}

@Composable
fun ClosingTimesScreen() {
    var selectedIdx by remember { mutableStateOf(0) }
    val closingTimesHelper = remember { ClosingTimesHelper() }
    val timeLeftHourly = remember { closingTimesHelper.hourly }
    val timeLeftDaily = remember { closingTimesHelper.daily }
    val timeLeftWeekly = remember { closingTimesHelper.weekly }
    val timeLeftMonthly = remember { closingTimesHelper.monthly }
    val hourlyClosingTime = remember { mutableStateOf(closingTimesHelper.nextHour) }
    val dailyClosingTime = remember { mutableStateOf(closingTimesHelper.nextDay) }
    val weeklyClosingTime = remember { mutableStateOf(closingTimesHelper.nextWeek) }
    val monthlyClosingTime = remember { mutableStateOf(closingTimesHelper.nextMonth) }

    LaunchedEffect(key1 = 0){
        closingTimesHelper.startTimers()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 64.dp)//Bottom nav
    ) {
        MainTitleText(mainText = stringResource(R.string.Closings_Title))

        SimpleTabLayout(){ idx -> selectedIdx = idx }

        when(selectedIdx){
            0 -> LabeledClockRow(
                label = stringResource(R.string.hourly_close),
                dayValue = timeLeftHourly.value.days,
                hourValue = timeLeftHourly.value.hours,
                minValue = timeLeftHourly.value.minutes,
                secValue = timeLeftHourly.value.seconds,
                closingTime = hourlyClosingTime.value,
                shouldShowDate = false
            )
            1 -> LabeledClockRow(
                label = stringResource(R.string.daily_close),
                dayValue = timeLeftDaily.value.days,
                hourValue = timeLeftDaily.value.hours,
                minValue = timeLeftDaily.value.minutes,
                secValue = timeLeftDaily.value.seconds,
                closingTime = dailyClosingTime.value,
                shouldShowDate = false
            )
            2 -> LabeledClockRow(
                label = stringResource(R.string.weekly_close),
                dayValue = timeLeftWeekly.value.days,
                hourValue = timeLeftWeekly.value.hours,
                minValue = timeLeftWeekly.value.minutes,
                secValue = timeLeftWeekly.value.seconds,
                closingTime = weeklyClosingTime.value,
                shouldShowDate = true
            )
            else -> LabeledClockRow(
                label = stringResource(R.string.monthly_close),
                dayValue = timeLeftMonthly.value.days,
                hourValue = timeLeftMonthly.value.hours,
                minValue = timeLeftMonthly.value.minutes,
                secValue = timeLeftMonthly.value.seconds,
                closingTime = monthlyClosingTime.value,
                shouldShowDate = true
            )
        }
    }
}

@Composable
fun TwoValuesTextField(
    modifier: Modifier = Modifier,
    labelText: String,
    valueText: String
    ) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$labelText:",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = valueText,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

@Composable
fun LabeledClockRow(
    modifier: Modifier = Modifier,
    label: String,
    dayValue: String,
    hourValue: String,
    minValue: String,
    secValue: String,
    closingTime: Long,
    shouldShowDate: Boolean
) {
    val closingTimeString = remember { formatTimeToString(closingTime, shouldShowDate) }
    val currentTimeString = remember {
        formatTimeToString(Calendar.getInstance(TimeZone.getDefault()).timeInMillis, shouldShowDate)
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 56.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TwoValuesTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp, start = 18.dp),
            labelText = stringResource(R.string.current_time),
            valueText = currentTimeString
        )
        TwoValuesTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp, start = 18.dp),
            labelText = stringResource(R.string.closing_time),
            valueText = closingTimeString
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 18.dp),
            text = stringResource(R.string.time_left),
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
        ClockRow(
            dayValue = dayValue,
            hourValue = hourValue,
            minValue = minValue,
            secValue = secValue
        )
    }
}

fun formatTimeToString(time: Long, showDate: Boolean): String {
    val format = if(showDate){
        "yyyy/MM/dd"
    }else{
        "hh:mm a"
    }
    val sdf = SimpleDateFormat(format, Locale.ENGLISH)
    return sdf.format(time)
}

@Composable
fun ClockRow(
    dayValue: String,
    hourValue: String,
    minValue: String,
    secValue: String
) {
    Row(
        modifier = Modifier
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        NumberBox(text = dayValue, title = stringResource(R.string.days))
        SeparationColon()
        NumberBox(text = hourValue, title = stringResource(R.string.hours))
        SeparationColon()
        NumberBox(text = minValue, title = stringResource(R.string.minutes))
        SeparationColon()
        NumberBox(text = secValue, title = stringResource(R.string.seconds))
    }
}

@Composable
fun SeparationColon() {
    Text(modifier = Modifier.padding(1.dp), text = ":")
}

@Composable
fun NumberBox(text: String, title: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            modifier = Modifier.padding(6.dp),
            text = text,
            fontWeight = FontWeight.SemiBold,
            fontSize = 56.sp
        )
        Text(text = title)
    }
}


@Composable
fun SimpleTabLayout(onIndexChanged: (Int) -> Unit){
    val tabTitles = mutableListOf(
        stringResource(R.string.hourly),
        stringResource(R.string.daily),
        stringResource(R.string.weekly),
        stringResource(R.string.monthly)
    )
    var tabIndex by remember { mutableStateOf(0) }

    LazyRow(modifier = Modifier.padding(start = 12.dp)) {
        items(tabTitles.size) {
            Box(
                modifier = Modifier
                    .padding(start = 4.dp, end = 4.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (tabIndex == it) Color.Black else Color(0x99BBBBBB))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    modifier = Modifier.clickable {
                        tabIndex = it
                        onIndexChanged(it)
                    },
                    text = tabTitles[it],
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if(tabIndex == it) Color.White else Color.Black)
            }
        }
    }

}

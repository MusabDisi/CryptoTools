package com.dissiapps.crypto.ui.closings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun LabeledClockRowPreview() {
    LabeledClockRow(
        label = "Hourly Close",
        dayValue = "00",
        hourValue = "00",
        minValue = "00",
        secValue = "00"
    )
}

@Composable
fun ClosingTimesScreen() {
    val closingTimesHelper = remember { ClosingTimesHelper() }
    val hourly by remember { mutableStateOf(closingTimesHelper.hourly) }
    val daily by remember { mutableStateOf(closingTimesHelper.daily) }
    val weekly by remember { mutableStateOf(closingTimesHelper.weekly) }
    val monthly by remember { mutableStateOf(closingTimesHelper.monthly) }

    LaunchedEffect(key1 = 0){
        closingTimesHelper.startTimers()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 50.dp)
            .padding(bottom = 50.dp)
            .padding(bottom = 56.dp), //Bottom nav
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        LabeledClockRow(
            label = "Hourly Close",
            dayValue = hourly.value.days,
            hourValue = hourly.value.hours,
            minValue = hourly.value.minutes,
            secValue = hourly.value.seconds
        )
        LabeledClockRow(
            label = "Daily Close",
            dayValue = daily.value.days,
            hourValue = daily.value.hours,
            minValue = daily.value.minutes,
            secValue = daily.value.seconds
        )
        LabeledClockRow(
            label = "Weekly Close",
            dayValue = weekly.value.days,
            hourValue = weekly.value.hours,
            minValue = weekly.value.minutes,
            secValue = weekly.value.seconds
        )
        LabeledClockRow(
            label = "Monthly Close",
            dayValue = monthly.value.days,
            hourValue = monthly.value.hours,
            minValue = monthly.value.minutes,
            secValue = monthly.value.seconds
        )
    }
}

@Composable
fun LabeledClockRow(
    label: String,
    dayValue: String,
    hourValue: String,
    minValue: String,
    secValue: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
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
        NumberBox(text = dayValue, title = "days")
        SeparationColon()
        NumberBox(text = hourValue, title = "hours")
        SeparationColon()
        NumberBox(text = minValue, title = "minutes")
        SeparationColon()
        NumberBox(text = secValue, title = "seconds")
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
            modifier = Modifier
                .background(
                    color = Color.LightGray,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(6.dp),
            text = text,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
//        Text(text = title)
    }
}
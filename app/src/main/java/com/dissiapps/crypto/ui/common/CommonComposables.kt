package com.dissiapps.crypto.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainTitleText(mainText: String, descText: String){
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 36.dp, bottom = 20.dp)) {
        Text(
            text = mainText,
            fontSize = 30.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold
        )
        if(descText.isNotEmpty())
            Text(text = descText, color = Color.Gray)
    }
}
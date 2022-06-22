package com.dissiapps.crypto.ui.news

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dissiapps.crypto.ui.theme.ShimmerColorShades
import java.lang.Exception


@Composable
@Preview(showBackground = true)
fun CoinPriceBoxPreview(){
    CoinPriceBox(name = "BTC/USD", price = "20900", isLoading = true)
}

@Composable
fun CoinPriceBox(
    modifier: Modifier = Modifier,
    name: String,
    price: String,
    isLoading: Boolean
){
    Column(
        modifier = modifier
            .width(100.dp)
            .border(width = 1.dp, color = Color.Gray, RoundedCornerShape(10.dp))
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = name,
            textAlign = TextAlign.Start,
            fontSize = 18.sp,
            fontWeight = FontWeight.W500
        )
        if (isLoading){
            ShimmerItem()
//            Text(
//                modifier = Modifier.fillMaxWidth().padding(end = 8.dp),
//                text = "--",
//                textAlign = TextAlign.End,
//                fontSize = 18.sp,
//                fontWeight = FontWeight.W400
//            )
        }else{
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "${price.toNumberWithComma()}$",
                textAlign = TextAlign.End,
                fontSize = 18.sp,
                fontWeight = FontWeight.W400
            )
        }
    }
}

@Preview
@Composable
fun ShimmerItem() {
    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1200, easing = LinearEasing),
            RepeatMode.Restart
        )
    )
    val brush = Brush.linearGradient(
        colors = ShimmerColorShades,
        start = Offset(10f, 10f),
        end = Offset(translateAnim, translateAnim)
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Box(
            modifier = Modifier
                .width(80.dp)
                .height(23.dp)
                .padding(top = 8.dp, end = 4.dp, bottom = 4.dp)
                .background(brush = brush, RoundedCornerShape(5.dp))
        )
    }
}

private fun String.toNumberWithComma(): String {
    return try {
        val floatValue = this.toFloat()
        if (floatValue > 1000){
            String.format("%,d", floatValue.toInt())
        }else{
            String.format("%,f", floatValue)
        }
    }catch (ex: Exception){
        Log.e("TAG", "toNumberWithComma: $ex")
        "0"
    }
}
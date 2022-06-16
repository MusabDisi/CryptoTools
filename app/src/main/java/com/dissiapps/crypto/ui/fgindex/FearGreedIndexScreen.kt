package com.dissiapps.crypto.ui.fgindex

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.dissiapps.crypto.ui.theme.Orange


@Preview(showBackground = true)
@Composable
fun FearGreedIndexScreen(viewModel: FGIndexViewModel = hiltViewModel()) {
    val state by remember { viewModel.state }
    val index by remember { viewModel.index }
    val lastUpdated by remember { viewModel.lastUpdated }
    val classification by remember { viewModel.classification }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (state != FGIndexViewModel.UiState.SUCCESS) {
            CircularProgressIndicator()
        } else {
            IndexAndLabelColumn(index = index, classification = classification)
            GradiantScaleHorizontal(index = index.toInt())
            Row(
                Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(text = lastUpdated)
                Text(text = "alternative.me")
            }
        }
    }
}


@Composable
fun IndexAndLabelColumn(
    modifier: Modifier = Modifier,
    index: String, classification: String
) {
    val color = getColorFromIndex(index)
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .drawBehind {
                    drawCircle(
                        color = color,
                        center = center,
                    )
                }
                .padding(40.dp),
            text = index,
            fontSize = 100.sp,
            color = Color.White
        )
        Text(
            modifier = Modifier.padding(40.dp),
            text = classification,
            fontSize = 40.sp,
            color = color,
            fontWeight = FontWeight.Bold
        )
    }
}

fun getColorFromIndex(index: String): Color {
    return try {
        val value = index.toInt()
        when {
            value <= 25 -> Color.Red
            value <= 45 -> Orange
            value <= 65 -> Color.Yellow
            value <= 100 -> Color.Green
            else -> Color.Red
        }
    } catch (ex: Exception) {
        Color.Red
    }
}

@Preview
@Composable
fun PreviewGradiantScale() {
    GradiantScaleHorizontal(index = 80)
}

@Composable
fun GradiantScaleHorizontal(index: Int) {
    var leftPadding by remember { mutableStateOf(0.dp) }
    var width by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 70.dp, vertical = 20.dp)
            .padding(bottom = 56.dp)
    ) {
        Box(
            modifier = Modifier
                .offset(x = leftPadding.minus(10.dp))
                .clip(ReversedTriangle())
                .width(20.dp)
                .height(20.dp)
                .background(Color.Black)
                .padding(bottom = 4.dp)
        )
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color.Red,
                            Color.Yellow,
                            Color.Green
                        )
                    )
                )
                .fillMaxWidth()
                .height(20.dp)
                .border(1.dp, Color.LightGray)
                .onGloballyPositioned { width = it.size.width }
        ) {
            leftPadding = with(LocalDensity.current) { (width * (index / 100.0)).toInt().toDp() }
        }
    }
}

class ReversedTriangle : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val trianglePath = Path().apply {
            lineTo(x = size.width, y = 0F)
            lineTo(x = size.width / 2, size.height)
            lineTo(x = 0F, y = 0F)
        }
        return Outline.Generic(path = trianglePath)
    }
}
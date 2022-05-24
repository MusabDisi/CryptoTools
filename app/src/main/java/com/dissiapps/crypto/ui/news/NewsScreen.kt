package com.dissiapps.crypto.ui.news

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.dissiapps.crypto.R
import com.dissiapps.crypto.data.local.news.NewsModel
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import com.dissiapps.crypto.data.models.news.Currency
import com.dissiapps.crypto.ui.theme.OffWhite
import com.dissiapps.crypto.ui.theme.Yellow

@ExperimentalPagingApi
@Composable
fun NewsScreen(viewModel: NewsScreenViewModel = hiltViewModel()) {

    val lazyPagingItems = viewModel.news.collectAsLazyPagingItems()

    if (lazyPagingItems.loadState.refresh == LoadState.Loading) {
        CircularProgressIndicator()
    }

    LazyColumn(modifier = Modifier.padding(bottom = 56.dp)) {

        items(lazyPagingItems) { item ->
            item ?: return@items
            NewsItem(newsResult = item)
            Divider(thickness = 1.dp)
        }

        if (lazyPagingItems.loadState.append == LoadState.Loading) {
            item {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsItemPreview() {
    val newsResult = NewsModel(
        currencies = listOf(
            Currency("BTC", "BTC", "BTC", "something.com"),
            Currency("ETH", "ETH", "ETH", "something.com"),
            Currency("DOT", "DOT", "DOT", "something.com"),
            Currency("MATIC", "MATIC", "MATIC", "something.com")
        ),
        id = 10,
        sourceDomain = "domain.com",
        title = "title alksdjn adnas lkdnaskld and yrryt  ry ryy yr ryyrtryryty   t yrryryyr y y ytytrtytrrtrty rytrtyrty ryrytrtyry ryrtyyt",
        url = "google.com",
        created_at = ""
    )
    NewsItem(newsResult = newsResult)
}

@Composable
fun NewsItem(newsResult: NewsModel) {
    var context:Context? = null
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, end = 8.dp, bottom = 8.dp)
            .clickable {
                openUrlInBrowser(context, newsResult.url)
            }
    ) {
        context = LocalContext.current
        Text(
            modifier = Modifier.width(60.dp),
            text = fixTime(newsResult.created_at),
            color = Color.DarkGray,
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
        )
        Column {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = newsResult.title,
                maxLines = 2,
                style = MaterialTheme.typography.subtitle1,
                overflow = TextOverflow.Ellipsis,
//                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.padding(end = 8.dp),
                    painter = painterResource(id = R.drawable.ic_link_24),
                    tint = Color.Gray,
                    contentDescription = null
                )
                Text(
                    text = newsResult.sourceDomain,
                    color = Color.Gray
                )
                CoinsRow(
                    modifier = Modifier.fillMaxWidth(), currencies = newsResult.currencies
                )
            }
        }
    }
}

@Composable
fun CoinsRow(modifier: Modifier = Modifier, currencies: List<Currency>?, maxSize: Int = 2) {
    if (currencies == null) return
    val list = if (currencies.size > maxSize) currencies.subList(0, maxSize) else currencies
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.End
    ) {
        list.forEach { currency ->
            CoinHolder(modifier = Modifier.padding(end = 4.dp), name = currency.code)
        }
        if (currencies.size > maxSize) {
            Text(
                modifier = Modifier.padding(end = 4.dp),
                text = "...",
                color = Yellow,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun CoinHolder(modifier: Modifier = Modifier, name: String) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Yellow)
            .padding(horizontal = 6.dp)
    ) {
        Text(text = name, fontSize = 12.sp, color = OffWhite)
    }
}

fun fixTime(createdAt: String): String {
    return try {
        val utc = TimeZone.getTimeZone("UTC")
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
        format.timeZone = utc
        val createdTimeMillis = format.parse(createdAt)?.time ?: 0L
        val calendar = Calendar.getInstance(utc)
        val timeDifference = calendar.timeInMillis - createdTimeMillis
        formatTimeDifference(timeDifference)
    } catch (ex: Exception) {
        Log.e("TAG", "fixTime: ", ex)
        ""
    }
}

fun formatTimeDifference(timeDifference: Long): String {
    val seconds = TimeUnit.MILLISECONDS.toSeconds(timeDifference)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(timeDifference)
    val hours = TimeUnit.MILLISECONDS.toHours(timeDifference)
    val days = TimeUnit.MILLISECONDS.toDays(timeDifference)
    return when {
        days > 0 -> "${days}d"
        hours > 0 -> "${hours}h"
        minutes > 0 -> "${minutes}min"
        seconds > 0 -> "${seconds}sec"
        else -> ""
    }
}

private fun openUrlInBrowser(context: Context?, url: String) {
    val fixedUrlString = if (url.startsWith("http")) url else "https://$url"
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(fixedUrlString))
        context?.startActivity(intent)
    }catch (ex: java.lang.Exception){
        Log.e("TAG", "openUrlInBrowser: ", ex)
    }
}
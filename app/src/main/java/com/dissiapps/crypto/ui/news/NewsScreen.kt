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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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
import com.dissiapps.crypto.ui.common.MainTitleText
import com.dissiapps.crypto.ui.navigation.NavigationPage
import com.dissiapps.crypto.ui.news.NewsScreenViewModel.UiState
import com.dissiapps.crypto.ui.theme.OffWhite
import com.dissiapps.crypto.ui.theme.VeryLightGray
import com.dissiapps.crypto.ui.theme.Yellow

@ExperimentalPagingApi
@Composable
fun NewsScreen(
    navController: NavController,
    currencyCode: List<String>,
    viewModel: NewsScreenViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = currencyCode){
        viewModel.setCurrenciesList(currencyCode)
    }

    val bitcoinTicker by remember { viewModel.btcTicker }
    val etherTicker by remember { viewModel.ethTicker }
    var loaded = false
    val lazyPagingItems = viewModel.news.collectAsLazyPagingItems()

    LazyColumn(modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.bottom_nav_bar_height))) {

        item {
            MainTitleText(
                mainText = stringResource(id = R.string.NewsTitle),
                descText = stringResource(id = R.string.NewsDesc)
            )
        }

        item {
            Row(modifier = Modifier.fillMaxWidth()) {
                if(bitcoinTicker != UiState.Error){
                    val isSuccess = bitcoinTicker is UiState.Success
                    CoinPriceBox(
                        modifier = Modifier
                            .padding(start = 12.dp, end = 4.dp, bottom = 8.dp)
                            .weight(1f),
                        name = "BTC/USD",
                        price = if (isSuccess) {
                            (bitcoinTicker as UiState.Success).ticker.price
                        } else {
                            "0"
                        },
                        isLoading = !isSuccess
                    )
                }
                if(etherTicker != UiState.Error){
                    val isSuccess = etherTicker is UiState.Success
                    CoinPriceBox(
                        modifier = Modifier
                            .padding(start = 4.dp, end = 12.dp, bottom = 8.dp)
                            .weight(1f),
                        name = "ETH/USD",
                        price = if (isSuccess) {
                            (etherTicker as UiState.Success).ticker.price
                        } else {
                            "0"
                        },
                        isLoading = !isSuccess
                    )
                }
            }
        }

        item {
            CustomSearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, start = 12.dp, end = 12.dp),
                text = if (currencyCode.isNotEmpty()) currencyCode[0] else null,
                onClick = {
                    navController.navigate(NavigationPage.SearchNewsPage.route) {
                        launchSingleTop = true
                    }
                },
                onClear = {
                    navController.popBackStack()
                }
            )
        }

        if (lazyPagingItems.loadState.refresh == LoadState.Loading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.Black)
                }
            }
            loaded = true
        } else {
            if (lazyPagingItems.itemCount == 0 && loaded) {
                item {
                    NoSearchResults()
                }
            } else {
                items(lazyPagingItems) { item ->
                    item ?: return@items
                    NewsItem(newsResult = item)
                    Divider(thickness = 1.dp)
                }
            }
        }

        if (lazyPagingItems.loadState.append == LoadState.Loading) {
            item {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally),
                    color = Color.Black
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NewsItemPreview() {
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

@Preview(showBackground = true)
@Composable
private fun NewsItemPreview2() {
    val newsResult = NewsModel(
        currencies = listOf(
            Currency("BTC", "BTC", "BTC", "something.com"),
            Currency("ETH", "ETH", "ETH", "something.com"),
            Currency("DOT", "DOT", "DOT", "something.com"),
            Currency("MATIC", "MATIC", "MATIC", "something.com")
        ),
        id = 10,
        sourceDomain = "domain.com",
        title = "title rtyyt",
        url = "google.com",
        created_at = ""
    )
    NewsItem(newsResult = newsResult)
}

@Composable
private fun NewsItem(newsResult: NewsModel) {
    var context: Context? = null
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 8.dp, end = 4.dp, bottom = 8.dp)
            .clickable {
                openUrlInBrowser(context, newsResult.url)
            }
    ) {
        context = LocalContext.current

        Column {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = newsResult.title,
                maxLines = 3,
                style = MaterialTheme.typography.subtitle1,
                overflow = TextOverflow.Ellipsis,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight(450)
            )
            Row(
                modifier = Modifier.padding(vertical = 4.dp),
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    modifier = Modifier.padding(end = 4.dp),
                    painter = painterResource(id = R.drawable.ic_link_24),
                    tint = Color.Gray,
                    contentDescription = null
                )
                Text(
                    text = newsResult.sourceDomain,
                    color = Color.Gray
                )
            }
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                CoinsRow(
                    modifier = Modifier.wrapContentSize(),
                    currencies = newsResult.currencies ?: emptyList(),
                    maxSize = 4,
                )
                Text(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = 2.dp, end = 8.dp),
                    text = fixTime(newsResult.created_at),
                    color = Color.DarkGray,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                )
            }
        }
    }
}

@Composable
fun CoinsRow(modifier: Modifier = Modifier, currencies: List<Currency>, maxSize: Int = 2) {
    val list = if (currencies.size > maxSize) currencies.subList(0, maxSize) else currencies
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        if (list.isEmpty()){
            CoinHolder(modifier = Modifier.padding(end = 4.dp), name = stringResource(R.string.news))
        }
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

@Preview
@Composable
fun CustomSearchBarPreview(){
    CustomSearchBar(text = "txt", onClick = {}){}
}

@Composable
fun CustomSearchBar(
    modifier: Modifier = Modifier,
    text: String?,
    onClick: () -> Unit,
    onClear: () -> Unit
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .clip(RoundedCornerShape(15.dp))
            .background(VeryLightGray)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (text == null)
            Icon(
                modifier = Modifier.padding(end = 8.dp),
                painter = painterResource(
                    id = R.drawable.ic_search_24
                ),
                tint = Color.Gray,
                contentDescription = null
            )
        Text(
            modifier = Modifier.weight(1f),
            text = text?.uppercase(Locale.ENGLISH) ?: stringResource(R.string.search_currency_code),
            fontSize = 20.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight(300),
            color = if (text == null) Color.Gray else Color.Black
        )
        if (text != null)
            Icon(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clickable {
                        onClear()
                    },
                painter = painterResource(
                    id = R.drawable.ic_baseline_close_24
                ),
                tint = Color.Black,
                contentDescription = null
            )
    }
}

@Composable
fun NoSearchResults(modifier: Modifier = Modifier){
    Column(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_search_off_24),
            tint = Color.Black,
            contentDescription = null
        )
        Text(
            text = stringResource(id = R.string.no_search_results_found),
            fontFamily = FontFamily.SansSerif,
            fontSize = 20.sp
        )
        Text(
            text = stringResource(id = R.string.plz_enter_a_valid_code),
            fontFamily = FontFamily.SansSerif,
            fontSize = 16.sp,
            color = Color.Gray
        )
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
    return when { // TODO: extract string resources
        days > 0 -> "$days d"
        hours > 0 -> "$hours h"
        minutes > 0 -> "$minutes min"
        seconds > 0 -> "$seconds sec"
        else -> ""
    }
}

private fun openUrlInBrowser(context: Context?, url: String) {
    val fixedUrlString = if (url.startsWith("http")) url else "https://$url"
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(fixedUrlString))
        context?.startActivity(intent)
    } catch (ex: java.lang.Exception) {
        Log.e("TAG", "openUrlInBrowser: ", ex)
    }
}
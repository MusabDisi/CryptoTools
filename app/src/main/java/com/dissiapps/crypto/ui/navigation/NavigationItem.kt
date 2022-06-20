package com.dissiapps.crypto.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NavArgument
import androidx.navigation.NavHostController
import com.dissiapps.crypto.R

sealed class NavigationItem(
    @DrawableRes val iconResourceId: Int,
    @StringRes val titleResourceId: Int,
    val destination: String,
    val path: String = "",
    ) {

    val route get() = "$destination$path"

    object ClosingTimesScreenNav :
        NavigationItem(R.drawable.ic_time_24, R.string.nav_title_closings, destination = "closings")

    object FearGreedIndexScreenNav :
        NavigationItem(R.drawable.ic_show_chart_24, R.string.nav_title_index, destination = "index")

    object NewsScreenNav : NavigationItem(
        R.drawable.ic_news_24,
        R.string.nav_title_news,
        destination = "news",
        path = "?currencyCode={currencyCode}",
    ){
        fun createNavigationRoute(currencyCode: String): String {
            return destination + path.replace("{currencyCode}", currencyCode.trim())
        }
    }

}

sealed class NavigationPage(
    val route: String
) {
    object SearchNewsPage: NavigationPage("search_news")
}



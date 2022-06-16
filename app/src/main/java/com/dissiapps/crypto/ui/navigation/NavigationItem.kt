package com.dissiapps.crypto.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.dissiapps.crypto.R

sealed class NavigationItem(
    val route: String,
    @DrawableRes val iconResourceId: Int,
    @StringRes val titleResourceId: Int
) {
    object ClosingTimesScreenNav: NavigationItem("closings", R.drawable.ic_time_24, R.string.nav_title_closings)
    object FearGreedIndexScreenNav: NavigationItem("index", R.drawable.ic_show_chart_24, R.string.nav_title_index)
    object NewsScreenNav: NavigationItem("news", R.drawable.ic_news_24, R.string.nav_title_news)
}

sealed class NavigationPage(
    val route: String
) {
    object SearchNewsPage: NavigationPage("search_news")
}


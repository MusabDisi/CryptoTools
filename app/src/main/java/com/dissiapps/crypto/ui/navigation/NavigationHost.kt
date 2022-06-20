package com.dissiapps.crypto.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.paging.ExperimentalPagingApi
import com.dissiapps.crypto.ui.closings.ClosingTimesScreen
import com.dissiapps.crypto.ui.news.NewsScreen
import com.dissiapps.crypto.ui.fgindex.FearGreedIndexScreen
import com.dissiapps.crypto.ui.news.search.SearchNewsScreen

@OptIn(ExperimentalPagingApi::class)
@Composable
fun AppNavigationHost(
    navigationController: NavHostController
){
    NavHost(navController = navigationController, startDestination =  NavigationItem.NewsScreenNav.route) {
        composable(
            route = NavigationItem.NewsScreenNav.route,
            arguments = listOf(navArgument("currencyCode") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
            )) {
            val string = it.arguments?.getString("currencyCode")
            val argsList = if (string != null) listOf(string) else emptyList()
            NewsScreen(navigationController, argsList)
        }
        composable(NavigationItem.ClosingTimesScreenNav.route){
            ClosingTimesScreen()
        }
        composable(NavigationItem.FearGreedIndexScreenNav.route){
            FearGreedIndexScreen()
        }
        composable(NavigationPage.SearchNewsPage.route) {
            SearchNewsScreen(navigationController)
        }
    }
}
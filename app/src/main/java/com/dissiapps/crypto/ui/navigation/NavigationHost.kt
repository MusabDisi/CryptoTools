package com.dissiapps.crypto.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.ExperimentalPagingApi
import com.dissiapps.crypto.ui.closings.ClosingTimesScreen
import com.dissiapps.crypto.ui.fgindex.FGIndexViewModel
import com.dissiapps.crypto.ui.news.NewsScreen
import com.dissiapps.crypto.ui.fgindex.FearGreedIndexScreen
import com.dissiapps.crypto.ui.news.SearchNewsScreen

@OptIn(ExperimentalPagingApi::class)
@Composable
fun AppNavigationHost(
    navigationController: NavHostController
){
    NavHost(navController = navigationController, startDestination =  NavigationItem.ClosingTimesScreenNav.route) {
        composable(NavigationItem.NewsScreenNav.route){
            NewsScreen(navigationController)
        }
        composable(NavigationItem.ClosingTimesScreenNav.route){
            ClosingTimesScreen()
        }
        composable(NavigationItem.FearGreedIndexScreenNav.route){
            val viewModel = hiltViewModel<FGIndexViewModel>()
            FearGreedIndexScreen(viewModel = viewModel)
        }
        composable(NavigationPage.SearchNewsPage.route){
            SearchNewsScreen(navigationController)
        }
    }
}
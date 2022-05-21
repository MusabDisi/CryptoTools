package com.dissiapps.crypto.ui.navigation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavigationHost(
    navigationController: NavHostController
){
    NavHost(navController = navigationController, startDestination =  NavigationItem.NewsScreenNav.route) {
        composable(NavigationItem.NewsScreenNav.route){
            Text(text = "News")
        }
        composable(NavigationItem.ClosingTimesScreenNav.route){
            Text(text = "Closings")
        }
        composable(NavigationItem.FearGreedIndexScreenNav.route){
            Text(text = "Fear greed index")
        }
        composable(NavigationItem.CalculatorScreenNav.route){
            Text(text = "calculator")
        }
    }
}
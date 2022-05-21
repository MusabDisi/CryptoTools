package com.dissiapps.crypto.ui.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navigationController: NavHostController) {
    val navigationItems = listOf(
        NavigationItem.CalculatorScreenNav,
        NavigationItem.NewsScreenNav,
        NavigationItem.ClosingTimesScreenNav,
        NavigationItem.FearGreedIndexScreenNav
    )
    val navBackStackEntry by navigationController.currentBackStackEntryAsState()
    val selectedRoute = navBackStackEntry?.destination?.route

    BottomNavigation(
        contentColor = Color.White
    ) {
        navigationItems.forEach {
            BottomNavigationItem(
                selected = selectedRoute == it.route,
                icon = {
                    Icon(
                        painter = painterResource(id = it.iconResourceId),
                        contentDescription = stringResource(id = it.titleResourceId)
                    ) },
                label = { Text(text = stringResource(id = it.titleResourceId)) },
                selectedContentColor = Color.White,
                alwaysShowLabel = selectedRoute == it.route,
                onClick = {
                    navigationController.navigate(it.route){
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
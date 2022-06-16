package com.dissiapps.crypto.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navigationController: NavHostController) {
    val navigationItems = listOf(
//        NavigationItem.CalculatorScreenNav,
        NavigationItem.NewsScreenNav,
        NavigationItem.ClosingTimesScreenNav,
        NavigationItem.FearGreedIndexScreenNav
    )
    val navBackStackEntry by navigationController.currentBackStackEntryAsState()
    val selectedRoute = navBackStackEntry?.destination?.route
    if(navigationItems.map { it.route }.contains(selectedRoute))
    Column {
        Divider(thickness = 1.dp, color = Color.Gray)
        BottomNavigation(
            modifier = Modifier.height(64.dp),
            contentColor = Color.Gray,
            backgroundColor = Color.White
        ) {
            navigationItems.forEach {
                BottomNavigationItem(
                    selected = selectedRoute == it.route,
                    icon = {
                        Icon(
                            painter = painterResource(id = it.iconResourceId),
                            contentDescription = stringResource(id = it.titleResourceId)
                        )
                    },
                    label = { Text(text = stringResource(id = it.titleResourceId)) },
                    selectedContentColor = Color.Black,
                    alwaysShowLabel = selectedRoute == it.route,
                    onClick = {
                        navigationController.navigate(it.route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}
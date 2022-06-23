package com.dissiapps.crypto

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.dissiapps.crypto.ui.navigation.AppNavigationHost
import com.dissiapps.crypto.ui.navigation.BottomNavigationBar
import com.dissiapps.crypto.ui.theme.CryptoToolsTheme
import com.dissiapps.crypto.ui.theme.LightRed
import com.dissiapps.crypto.utils.connectivity.ConnectionState
import com.dissiapps.crypto.utils.connectivity.connectivityState
import com.dissiapps.crypto.utils.connectivity.currentConnectivityState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptoToolsTheme {
                CryptoToolsApp()
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Composable
    private fun CryptoToolsApp() {
        val navController = rememberNavController()
        // This will cause re-composition on every network state change
        val connection by connectivityState(this.lifecycle)
        val isConnected = connection == ConnectionState.Available
        
        Scaffold(
            bottomBar = { BottomNavigationBar(navigationController = navController) }
        ) {
            Box(Modifier.fillMaxSize()) {
                AnimatedVisibility(
                    visible = !isConnected,
                    enter = slideInVertically(),
                    exit = slideOutVertically()
                ) {
                    NoInternet()
                }
                AppNavigationHost(navigationController = navController) 
            }
        }
    }

    @Preview
    @Composable
    private fun NoInternet() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(LightRed)
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_wifi_off_24),
                tint = Color.White,
                contentDescription = null
            )
            Text(
                modifier = Modifier.padding(start = 6.dp),
                text = stringResource(R.string.no_internet_warning),
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}


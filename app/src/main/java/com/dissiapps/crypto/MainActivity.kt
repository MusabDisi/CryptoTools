package com.dissiapps.crypto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.dissiapps.crypto.ui.navigation.AppNavigationHost
import com.dissiapps.crypto.ui.navigation.BottomNavigationBar
import com.dissiapps.crypto.ui.theme.CryptoToolsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptoToolsTheme {
                CryptoToolsApp()
            }
        }
    }

    @Composable
    private fun CryptoToolsApp() {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = { BottomNavigationBar(navigationController = navController) }
        ) {
            AppNavigationHost(navigationController = navController)
        }
    }
}


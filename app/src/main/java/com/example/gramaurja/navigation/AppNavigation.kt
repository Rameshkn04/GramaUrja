package com.example.gramaurja.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.*
import com.example.gramaurja.screens.RegisterScreen
import com.example.gramaurja.screens.SplashScreen

import com.example.gramaurja.screens.HomeScreen
import com.example.gramaurja.screens.LoginScreen
import com.example.gramaurja.screens.ProfileScreen
import com.example.gramaurja.screens.ReportScreen
import com.example.gramaurja.screens.AdminScreen
import com.example.gramaurja.screens.LiveMapScreen

data class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
)

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val items = listOf(

        BottomNavItem(
            route = "home",
            title = "Home",
            icon = Icons.Default.Home
        ),

        BottomNavItem(
            route = "report",
            title = "Report",
            icon = Icons.Default.Warning
        ),

        BottomNavItem(
            route = "profile",
            title = "Profile",
            icon = Icons.Default.Person
        )
    )

    val navBackStackEntry by
    navController.currentBackStackEntryAsState()

    val currentRoute =
        navBackStackEntry?.destination?.route

    val showBottomBar =
        currentRoute == "home" ||
                currentRoute == "report" ||
                currentRoute == "profile"

    Scaffold(

        bottomBar = {

            if (showBottomBar) {

                NavigationBar {

                    items.forEach { item ->

                        NavigationBarItem(

                            selected =
                                currentRoute == item.route,

                            onClick = {
                                navController.navigate(item.route)
                            },

                            icon = {

                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.title
                                )
                            },

                            label = {
                                Text(item.title)
                            }
                        )
                    }
                }
            }
        }

    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = "splash",
            modifier = Modifier.padding(innerPadding)
        ) {

            composable("splash") {
                SplashScreen(navController)
            }

            composable("login") {
                LoginScreen(navController)
            }

            composable("register") {
                RegisterScreen(navController)
            }

            composable("home") {
                HomeScreen(navController)
            }

            composable("report") {
                ReportScreen(navController)
            }

            composable("profile") {
                ProfileScreen(navController)
            }

            composable("admin") {
                AdminScreen(navController)
            }
            composable("map") {
                LiveMapScreen(navController)
            }
        }
    }
}
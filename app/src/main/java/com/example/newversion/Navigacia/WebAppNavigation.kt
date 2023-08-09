package com.example.newversion.Navigacia

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newversion.Screender.ScreenThatCloack
import com.example.newversion.Screender.ScreenThatMain
import com.example.newversion.Screender.ScreenThatSplash
import com.example.newversion.Screender.ScreenThatWin

@Composable
fun WebAppNavigation() {
    val navigationController= rememberNavController()
    NavHost(navController = navigationController, startDestination = ScreensOfNav.MainScreenWithWeb.name ){
        composable(ScreensOfNav.ScreenThatSplash.name){
            ScreenThatSplash(navigationController)
        }
        composable(ScreensOfNav.CloackScreen.name){
            ScreenThatCloack()
        }
        composable(ScreensOfNav.WinningScreen.name){
            ScreenThatWin()
        }
        composable(ScreensOfNav.MainScreenWithWeb.name){
            ScreenThatMain(navigationController)
        }
    }
}
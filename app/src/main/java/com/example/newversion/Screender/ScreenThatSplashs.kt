package com.example.newversion.Screender

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.newversion.Navigacia.ScreensOfNav
import com.example.newversion.R
import com.example.newversion.ui.theme.splashLoadingColor
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import android.graphics.drawable.Animatable as Animatable1

@Composable
fun ScreenThatSplash(navController: NavController) {
    Surface(modifier = Modifier.fillMaxSize()){
        Image(painter = painterResource(id = R.drawable.splash), contentDescription ="Splash Screen Background", contentScale = ContentScale.Crop )
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()
            ) {
            Spacer(modifier = Modifier.weight(0.9f))
            CustomLoadingIndicator(navController=navController)
            Spacer(modifier = Modifier.weight(0.1f))
        }
    }
}

@SuppressLint("RememberReturnType")
@Composable
fun CustomLoadingIndicator(
    navController: NavController, // Добавлен NavController для навигации
    baseModifier: Modifier = Modifier,
    orbSize: Dp = 25.dp,
    orbHue: Color = splashLoadingColor,
    separation: Dp = 10.dp,
    shiftDistance: Dp = 20.dp
) {
    val orbs = listOf(
        remember { Animatable(initialValue = 0f) },
        remember { Animatable(initialValue = 0f) },
        remember { Animatable(initialValue = 0f) }
    )

    orbs.forEachIndexed { position, animatedOrb ->
        LaunchedEffect(key1 = animatedOrb) {
            async {
                delay(2000L)
                navController.navigate(ScreensOfNav.CloackScreen.name)
            }

            delay(position * 100L)
            animatedOrb.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 2000 // Изменено на 2 секунды
                        0.0f at 0 with LinearOutSlowInEasing
                        1.0f at 500 with LinearOutSlowInEasing
                        0.0f at 1000 with LinearOutSlowInEasing
                        0.0f at 2000 with LinearOutSlowInEasing
                    }
                )
            )
        }
    }

    val orbMovements = orbs.map { it.value }
    val pxShift = with(LocalDensity.current) { shiftDistance.toPx() }

    Row(
        modifier = baseModifier,
        horizontalArrangement = Arrangement.spacedBy(separation)
    ) {
        orbMovements.forEach { movementValue ->
            Box(
                modifier = Modifier
                    .size(orbSize)
                    .graphicsLayer {
                        this.translationY = -movementValue * pxShift
                    }
                    .background(
                        color = orbHue,
                        shape = CircleShape
                    )
            )
        }
    }
}

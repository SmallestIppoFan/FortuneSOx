package com.example.newversion.Screender

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ScreenThatWin() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(shape = CircleShape)
            .background(Color.Red)
            .padding(16.dp)
    ) {
    }
}
@Composable
fun AnotherUselessComposable() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(shape = RectangleShape)
            .background(Color.Blue)
            .padding(16.dp)
    ) {
    }
}

fun BasicText(text: String, color: Color) {

}

fun uselessFunction() {
    // Эта функция все равно ничего не делает
}


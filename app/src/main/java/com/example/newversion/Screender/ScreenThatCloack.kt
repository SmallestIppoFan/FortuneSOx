package com.example.newversion.Screender

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.newversion.R
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

@Preview
@Composable
fun ScreenThatCloack(navController: NavController= rememberNavController()) {
    val isSpinned = remember { mutableStateOf(false) }
    val spintimes = remember {
        mutableStateOf(1)
    }
    val isEnabled = remember{
        mutableStateOf(true)
    }
    val fuckingMoney = remember{ mutableStateOf(1000) }
    Surface(modifier = Modifier.fillMaxSize()) {
        Image(painter = painterResource(id = R.drawable.play_bgbg),
            contentDescription ="BGBGGBGB",
            contentScale = ContentScale.Crop
            )
    }
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()){
        Spacer(modifier = Modifier.weight(0.85f))
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(20.dp)) {
            Slot(isSpinned,spintimes,fuckingMoney,isEnabled)
            Slot(isSpinned,spintimes,fuckingMoney,isEnabled)
            Slot(isSpinned,spintimes,fuckingMoney,isEnabled)
        }
        Spacer(modifier = Modifier.weight(0.05f))
        Row(horizontalArrangement = Arrangement.Center) {
            PlusMinus(spintimes)
            Spacer(modifier = Modifier.width(80.dp))
            Image(painter = painterResource(id = R.drawable.spinthatass), contentDescription ="", modifier = Modifier.size(100.dp)
                .clickable {
                    if (isEnabled.value) {
                        fuckingMoney.value -=50*spintimes.value
                        isEnabled.value=false
                        isSpinned.value = true
                    }
            } )
        }
        BalanceBg(fuckingMoney)
        Spacer(modifier = Modifier.weight(0.1f))

    }
}

@Composable
fun Slot(spinState:MutableState<Boolean>, spinTimes: MutableState<Int>, reward: MutableState<Int>,
         isEnabled:MutableState<Boolean>
         ) {
    val slotImages = listOf(R.drawable.first_slot, R.drawable.second_slot, R.drawable.third_slot, R.drawable.fourth_slot, R.drawable.fifth_slot)
    val currentSurrentImages = remember { List(3) { mutableStateOf(slotImages.random()) } }

    LaunchedEffect(spinState.value) {
        if (spinState.value) {
            for (i in 1..spinTimes.value) {
                val endTime = System.currentTimeMillis() + 3000 // 3 секунды вращения
                while (System.currentTimeMillis() < endTime) {
                    currentSurrentImages.forEach {
                        it.value = slotImages.random()
                    }
                    delay(100)
                }
                when {
                    currentSurrentImages.all { it.value == currentSurrentImages[0].value } -> {
                        reward.value += 100
                    }
                    currentSurrentImages[0].value == currentSurrentImages[1].value -> {
                        reward.value += 50
                    }
                    currentSurrentImages[1].value == currentSurrentImages[2].value -> {
                        reward.value += 30
                    }
                }
                if (i < spinTimes.value) {
                    delay(1000)
                }
            }
            spinState.value = false
            isEnabled.value=true
        }
    }

    Surface(modifier = Modifier.height(300.dp).width(100.dp)) {
        Image(painter = painterResource(id = R.drawable.slot_bgbg), contentDescription = "", contentScale = ContentScale.Crop)
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            currentSurrentImages.forEach { it ->
                Image(painter = painterResource(id = it.value),
                    contentDescription = "sd",
                    modifier = Modifier.size(100.dp)
                )
            }
        }
    }
}



@Composable
fun BalanceBg(allMoney:MutableState<Int> = mutableStateOf(100)) {
    Surface(modifier = Modifier
        .height(150.dp).width(150.dp), color = Color.Transparent
        ) {
        Image(painter = painterResource(id = R.drawable.balance_bgg), contentDescription ="")
        Column (verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 4.dp, bottom = 4.dp)
            ){
            Text(text = allMoney.value.toString(),
                color= Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
                )
        }
    }
}

@Composable
fun PlusMinus(spinTimes:MutableState<Int>) {
    Surface(color= Color.Transparent, modifier = Modifier.size(100.dp)) {
        Image(painter = painterResource(id = R.drawable.rc_59), contentDescription = "ZAAZ")
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(18.dp), modifier = Modifier.padding(start = 12.dp, bottom = 5.dp)) {
            Text(text = "-",
                color = Color.Green,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    if (spinTimes.value>=1) {
                        spinTimes.value -= 1
                    }
                }
            )
            Text(text = "${spinTimes.value}",
                color = Color.White,
                fontSize = 25.sp
            )
            Text(text = "+",
                color = Color.Green,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier.clickable {
                    if (spinTimes.value>=1) {
                        spinTimes.value += 1
                    }
                }
            )
        }
    }
}
package com.example.jaldrishtifinalll.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jaldrishtifinalll.R
private val LightBlue = Color(0xFFEAF7FA)
private val Blue = Color(0xFF59BDD3)
private val DarkText = Color(0xFF171717)

@Composable
fun WelcomePage(navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF65C6DA),
                        Color(0xFFEAF8FC)
                    )
                )
            )
            .padding(horizontal = 20.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .clip(RoundedCornerShape(32.dp))
                .background(LightBlue)
                .padding(bottom = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Top Image
            Image(
                painter = painterResource(id = R.drawable.img_1),
                contentDescription = "Hydrogen Fuel Station",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(420.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 32.dp,
                            topEnd = 32.dp
                        )
                    ),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(25.dp))

            // Welcome text
            Text(
                text = "Welcome To",
                fontSize = 38.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Text(
                text = "Jal Drishti",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = Blue
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Description
            Text(
                text = "Save time at the pump with secure quick\nand reliable mobile payments.",
                fontSize = 16.sp,
                lineHeight = 24.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(42.dp))

            // Get Started
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(58.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                Color(0xFF5BC0D7),
                                Color(0xFFBDECF4),
                                //Color(0xFFEAF8FC)
                            )
                        )
                    )
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable {
                        navController.navigate("login")
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Get Started",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
package com.example.jaldrishtifinalll.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val LightBlue = Color(0xFFEAF8FC)
private val PrimaryBlue = Color(0xFF5BC0D7)
private val DarkText = Color(0xFF171717)

private val selectedItem = 0
private val selected= 0

@Composable
fun JalDrishtiBottomBar(

) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(LightBlue)
            .padding(
                horizontal = 10.dp,
                vertical = 8.dp
            ),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {

        BottomBarItem(
            icon = Icons.Default.Home,
            label = "Home",
            selected = selectedItem == 0,
            onClick = {

            }
        )

        BottomBarItem(
            icon = Icons.Default.Home,
            label = "Harvest",
            selected = selectedItem == 1,
            onClick = {

            }
        )

        BottomBarItem(
            icon = Icons.Default.LocationOn,
            label = "Map",
            selected == 2,
            onClick = {
            }
        )

        BottomBarItem(
            icon = Icons.Default.Person,
            label = "Profile",
            selected  == 3,
            onClick = {

            }
        )
    }
}

@Composable
fun BottomBarItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                onClick()
            }
            .padding(
                horizontal = 14.dp,
                vertical = 6.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (selected) {
                PrimaryBlue
            } else {
                Color.Gray
            }
        )

        Spacer(
            modifier = Modifier.height(3.dp)
        )

        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = if (selected) {
                FontWeight.Bold
            } else {
                FontWeight.Normal
            },
            color = if (selected) {
                DarkText
            } else {
                Color.Gray
            }
        )
    }
}
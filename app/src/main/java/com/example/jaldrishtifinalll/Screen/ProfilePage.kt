package com.example.jaldrishtifinalll.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.jaldrishtifinalll.ViewModel.ProfileViewModel

@Composable
fun ProfilePage(
    navController: NavController,
    profileViewModel: ProfileViewModel = viewModel()
) {

    val user by profileViewModel.user.collectAsState()
    val isLoading by profileViewModel.isLoading.collectAsState()
    val message by profileViewModel.message.collectAsState()

    var city by remember {
        mutableStateOf("")
    }

    var editingCity by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        profileViewModel.loadUser()
    }

    Column(
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
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {

        // ---------------- TOP BAR ----------------

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            Text(
                text = "Profile",
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        // ---------------- PROFILE HEADER ----------------

        if (isLoading && user == null) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color.White
                )
            }

        } else {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Simple avatar - PHOTO NOT USED

                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "User",
                        tint = Color(0xFF52B8CE),
                        modifier = Modifier.size(58.dp)
                    )
                }

                Spacer(
                    modifier = Modifier.height(14.dp)
                )

                Text(
                    text = user?.name
                        ?.takeIf { it.isNotBlank() }
                        ?: "User",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(5.dp)
                )

                Text(
                    text = user?.email
                        ?.takeIf { it.isNotBlank() }
                        ?: "Email not available",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 14.sp
                )
            }

            Spacer(
                modifier = Modifier.height(30.dp)
            )

            // ---------------- PERSONAL INFO ----------------

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF5FCFD)
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 5.dp
                )
            ) {

                Column(
                    modifier = Modifier.padding(24.dp)
                ) {

                    Text(
                        text = "Personal Information",
                        color = Color(0xFF173B43),
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(22.dp)
                    )

                    // NAME

                    ProfileDetail(
                        icon = Icons.Default.Person,
                        title = "Full Name",
                        value = user?.name
                            ?.takeIf { it.isNotBlank() }
                            ?: "Not available"
                    )

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )

                    // EMAIL

                    ProfileDetail(
                        icon = Icons.Default.Email,
                        title = "Email",
                        value = user?.email
                            ?.takeIf { it.isNotBlank() }
                            ?: "Not available"
                    )

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )

                    // CITY

                    if (editingCity) {

                        OutlinedTextField(
                            value = city,
                            onValueChange = {
                                city = it
                            },
                            modifier = Modifier.fillMaxWidth(),
                            label = {
                                Text("City")
                            },
                            placeholder = {
                                Text("Enter your city")
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(14.dp)
                        )

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement =
                                Arrangement.spacedBy(10.dp)
                        ) {

                            Button(
                                onClick = {
                                    editingCity = false
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.LightGray
                                )
                            ) {
                                Text(
                                    text = "Cancel",
                                    color = Color.Black
                                )
                            }

                            Button(
                                onClick = {

                                    profileViewModel
                                        .updateCity(city)

                                    editingCity = false

                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor =
                                        Color(0xFF5BC0D7)
                                )
                            ) {
                                Text(
                                    text = "Save",
                                    color = Color.Black
                                )
                            }
                        }

                    } else {

                        ProfileDetail(
                            icon = Icons.Default.LocationOn,
                            title = "City",
                            value =
                                if (user?.city.isNullOrBlank()) {
                                    "Add your city"
                                } else {
                                    user?.city ?: ""
                                },
                            clickable = true,
                            onClick = {
                                city = user?.city ?: ""
                                editingCity = true
                            }
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            // ---------------- JAL DRISHTI CARD ----------------

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(
                        alpha = 0.75f
                    )
                )
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "Jal Drishti",
                        color = Color(0xFF173B43),
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )

                    Text(
                        text = "Your profile helps us personalize " +
                                "rainwater harvesting recommendations.",
                        color = Color.Gray,
                        fontSize = 13.sp,
                        lineHeight = 19.sp
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Button(
                onClick = {

                    profileViewModel.logout()

                    navController.navigate("login") {
                        popUpTo(0) {
                            inclusive = true
                        }
                    }

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFE5E5)
                )
            ) {

                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Logout",
                    tint = Color(0xFFD32F2F)
                )

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                Text(
                    text = "Logout",
                    color = Color(0xFFD32F2F),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(
                modifier = Modifier.height(30.dp)
            )

            if (message.isNotEmpty()) {

                Text(
                    text = message,
                    color = Color.White,
                    fontSize = 13.sp
                )
            }
        }
    }
}


@Composable
fun ProfileDetail(
    icon: ImageVector,
    title: String,
    value: String,
    clickable: Boolean = false,
    onClick: () -> Unit = {}
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (clickable) {
                    Modifier.clickable {
                        onClick()
                    }
                } else {
                    Modifier
                }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(RoundedCornerShape(13.dp))
                .background(Color(0xFFDDF4F8)),
            contentAlignment = Alignment.Center
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF3FA8BD),
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(
            modifier = Modifier.width(14.dp)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = title,
                color = Color.Gray,
                fontSize = 12.sp
            )

            Spacer(
                modifier = Modifier.height(3.dp)
            )

            Text(
                text = value,
                color = Color(0xFF173B43),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
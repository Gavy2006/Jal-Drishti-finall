package com.example.jaldrishtifinalll.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.jaldrishtifinalll.ViewModel.AuthViewModel

@Composable
fun LoginPage(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var passwordVisible by remember {
        mutableStateOf(false)
    }

    val message by authViewModel.message.collectAsState()
    val isLoading by authViewModel.isLoading.collectAsState()

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
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // HEADER

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Sign up",
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.clickable {
                    navController.navigate("signup")
                }
            )
        }

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        // HEADING

        Text(
            text = "Sign in to",
            color = Color.White,
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Continue",
            color = Color.White,
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(35.dp)
        )

        // FORM CARD

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFEAF7FA)
            )
        ) {

            Column(
                modifier = Modifier.padding(25.dp)
            ) {

                Text(
                    text = "Sign in",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(25.dp)
                )

                // EMAIL

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text("Enter your email")
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(
                    modifier = Modifier.height(15.dp)
                )

                // PASSWORD

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text("Enter your password")
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    visualTransformation =
                        if (passwordVisible)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation(),

                    trailingIcon = {

                        IconButton(
                            onClick = {
                                passwordVisible =
                                    !passwordVisible
                            }
                        ) {

                            Icon(
                                imageVector =
                                    Icons.Default.CheckCircle,
                                contentDescription =
                                    "Password visibility"
                            )
                        }
                    }
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                // ERROR / SUCCESS MESSAGE

                if (message.isNotEmpty()) {

                    Text(
                        text = message,
                        color = if (
                            message.contains(
                                "successful",
                                ignoreCase = true
                            )
                        ) {
                            Color(0xFF168A4A)
                        } else {
                            Color.Red
                        },
                        fontSize = 14.sp,
                        modifier = Modifier.padding(
                            vertical = 5.dp
                        )
                    )
                }

                Spacer(
                    modifier = Modifier.height(15.dp)
                )

                // LOGIN BUTTON

                Button(
                    onClick = {

                        authViewModel.login(
                            email = email,
                            password = password
                        ) {

                            navController.navigate("home") {

                                popUpTo("login") {
                                    inclusive = true
                                }
                            }
                        }

                    },
                    enabled = !isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF5BC0D7)
                    )
                ) {

                    Text(
                        text = if (isLoading)
                            "Signing in..."
                        else
                            "Sign in",
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                }

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                // OR

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    HorizontalDivider(
                        modifier = Modifier.weight(1f)
                    )

                    Text(
                        text = "  or continue with  ",
                        color = Color.Gray
                    )

                    HorizontalDivider(
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                // GOOGLE / APPLE

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.spacedBy(10.dp)
                ) {

                    // GOOGLE

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .clip(
                                RoundedCornerShape(12.dp)
                            )
                            .background(
                                Brush.horizontalGradient(
                                    listOf(
                                        Color(0xFF5BC0D7),
                                        Color(0xFFBDECF4)
                                    )
                                )
                            )
                            .border(
                                width = 1.dp,
                                color = Color.Black,
                                shape =
                                    RoundedCornerShape(12.dp)
                            )
                            .clickable {
                                // Google login later
                            },
                        horizontalAlignment =
                            Alignment.CenterHorizontally,
                        verticalArrangement =
                            Arrangement.Center
                    ) {

                        Text(
                            text = "Google",
                            color = Color.Black,
                            fontSize = 14.sp,
                            fontWeight =
                                FontWeight.Medium
                        )
                    }

                    // APPLE

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .clip(
                                RoundedCornerShape(12.dp)
                            )
                            .background(
                                Brush.horizontalGradient(
                                    listOf(
                                        Color(0xFF5BC0D7),
                                        Color(0xFFBDECF4)
                                    )
                                )
                            )
                            .border(
                                width = 1.dp,
                                color = Color.Black,
                                shape =
                                    RoundedCornerShape(12.dp)
                            )
                            .clickable {
                                // Apple login later
                            },
                        horizontalAlignment =
                            Alignment.CenterHorizontally,
                        verticalArrangement =
                            Arrangement.Center
                    ) {

                        Text(
                            text = "Apple",
                            color = Color.Black,
                            fontSize = 14.sp,
                            fontWeight =
                                FontWeight.Medium
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(25.dp)
                )

                // SIGN UP

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.Center
                ) {

                    Text(
                        text = "Don't have an account? "
                    )

                    Text(
                        text = "Sign up",
                        color = Color.Gray,
                        modifier = Modifier.clickable {
                            navController.navigate("signup")
                        }
                    )
                }
            }
        }
    }
}
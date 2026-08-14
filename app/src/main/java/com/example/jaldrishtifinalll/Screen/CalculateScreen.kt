package com.example.jaldrishtifinalll.Screen

import android.os.Looper
import com.google.android.gms.location.Priority
import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.jaldrishtifinalll.ViewModel.RainfallViewModel
import com.example.jaldrishtifinalll.model.RainfallRequest
import com.google.android.gms.location.LocationServices

@Composable
fun CalculateScreen(
    navController: NavController,
    rainfallViewModel: RainfallViewModel = viewModel()
) {

    val context = LocalContext.current

    val result by rainfallViewModel.result.collectAsState()
    val isLoading by rainfallViewModel.isLoading.collectAsState()
    val error by rainfallViewModel.error.collectAsState()

    var roofArea by remember {
        mutableStateOf("")
    }

    var roofType by remember {
        mutableStateOf("Concrete")
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    var latitude by remember {
        mutableStateOf<Double?>(null)
    }

    var longitude by remember {
        mutableStateOf<Double?>(null)
    }

    var locationText by remember {
        mutableStateOf("Location not detected")
    }

    val roofTypes = listOf(
        "Concrete",
        "Metal",
        "Tiled",
        "Asbestos",
        "Flat RCC",
        "Sloped GI Sheet",
        "Thatched",
        "Green Roof"
    )

    val fusedLocationClient =
        remember {
            LocationServices
                .getFusedLocationProviderClient(context)
        }

    val locationPermissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->

            val granted =
                permissions[
                    Manifest.permission.ACCESS_FINE_LOCATION
                ] == true ||
                        permissions[
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ] == true

            if (granted) {

                locationText =
                    "Permission granted. Tap location again."

            } else {

                locationText =
                    "Location permission required"
            }
        }

    fun getLocation() {

        val fineGranted =
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

        val coarseGranted =
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

        if (!fineGranted && !coarseGranted) {

            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )

            return
        }

        locationText = "Detecting current location..."

        val locationRequest =
            com.google.android.gms.location.LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY,
                1000L
            )
                .setWaitForAccurateLocation(true)
                .setMaxUpdates(1)
                .build()

        val locationCallback =
            object : com.google.android.gms.location.LocationCallback() {

                override fun onLocationResult(
                    result: com.google.android.gms.location.LocationResult
                ) {

                    val location = result.lastLocation

                    if (location != null) {

                        latitude = location.latitude
                        longitude = location.longitude

                        locationText =
                            "Location detected successfully"

                    } else {

                        locationText =
                            "Unable to detect location"
                    }
                }
            }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
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
            .verticalScroll(
                rememberScrollState()
            )
            .padding(20.dp)
    ) {


        Text(
            text = "Rainwater",
            color = Color.White,
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Harvest Calculator",
            color = Color.White,
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Estimate how much rainwater your roof can harvest.",
            color = Color.White.copy(alpha = 0.9f),
            fontSize = 14.sp
        )

        Spacer(
            modifier = Modifier.height(25.dp)
        )



        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFEAF7FA)
            )
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Row(
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .size(45.dp)
                            .clip(
                                RoundedCornerShape(13.dp)
                            )
                            .background(
                                Color(0xFFD5F2F6)
                            ),
                        contentAlignment =
                            Alignment.Center
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.LocationOn,
                            contentDescription =
                                "Location",
                            tint =
                                Color(0xFF3FA8BD)
                        )
                    }

                    Spacer(
                        modifier = Modifier.width(13.dp)
                    )

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = "Your Location",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(3.dp)
                        )

                        Text(
                            text = locationText,
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                    }

                    Icon(
                        imageVector =
                            if (latitude != null)
                                Icons.Default.CheckCircle
                            else
                                Icons.Default.Refresh,
                        contentDescription = null,
                        tint =
                            if (latitude != null)
                                Color(0xFF29A36A)
                            else
                                Color(0xFF3FA8BD),
                        modifier = Modifier
                            .size(25.dp)
                            .clickable {
                                getLocation()
                            }
                    )
                }

                Spacer(
                    modifier = Modifier.height(15.dp)
                )

                Button(
                    onClick = {
                        getLocation()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor =
                            Color(0xFF5BC0D7)
                    )
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = Color.Black
                    )

                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )

                    Text(
                        text =
                            if (latitude != null)
                                "Location Detected"
                            else
                                "Use My Current Location",
                        color = Color.Black
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(18.dp)
        )



        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFEAF7FA)
            )
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Row(
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = Color(0xFF3FA8BD)
                    )

                    Spacer(
                        modifier = Modifier.width(10.dp)
                    )

                    Text(
                        text = "Roof Details",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(
                    modifier = Modifier.height(22.dp)
                )


                // ROOF AREA

                Text(
                    text = "Roof Area",
                    fontSize = 13.sp,
                    color = Color.Gray
                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                OutlinedTextField(
                    value = roofArea,
                    onValueChange = {
                        roofArea = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text("Enter roof area")
                    },
                    suffix = {
                        Text("m²")
                    },
                    singleLine = true,
                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType =
                                KeyboardType.Decimal
                        ),
                    shape = RoundedCornerShape(13.dp)
                )

                Spacer(
                    modifier = Modifier.height(18.dp)
                )


                // ROOF TYPE

                Text(
                    text = "Roof Type",
                    fontSize = 13.sp,
                    color = Color.Gray
                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    OutlinedTextField(
                        value = roofType,
                        onValueChange = {},
                        readOnly = true,
                        enabled = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                expanded = !expanded
                            },
                        singleLine = true,
                        shape = RoundedCornerShape(13.dp),
                        trailingIcon = {
                            Icon(
                                imageVector =
                                    if (expanded)
                                        Icons.Default.KeyboardArrowUp
                                    else
                                        Icons.Default.KeyboardArrowDown,
                                contentDescription = "Select Roof Type",
                                modifier = Modifier.clickable {
                                    expanded = !expanded
                                }
                            )
                        }
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        }
                    ) {
                        roofTypes.forEach { type ->
                            DropdownMenuItem(
                                text = {
                                    Text(type)
                                },
                                onClick = {
                                    roofType = type
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
            Spacer(
                modifier = Modifier.height(20.dp)
            )


            Button(
                onClick = {

                    val area = roofArea.toDoubleOrNull()

                    if (area == null || area <= 0) {
                        return@Button
                    }

                    if (latitude == null || longitude == null) {
                        getLocation()
                        return@Button
                    }

                    val request = RainfallRequest(
                        place = null,
                        lat = latitude,
                        lon = longitude,
                        roof_type = when (roofType) {
                            "Concrete" -> "concrete"
                            "Metal" -> "metal"
                            "Tiled" -> "tiled"
                            "Asbestos" -> "asbestos"
                            "Flat RCC" -> "flat_rcc"
                            "Sloped GI Sheet" -> "sloped_gi_sheet"
                            "Thatched" -> "thatched"
                            "Green Roof" -> "green_roof"
                            else -> "concrete"
                        },
                        roof_area_m2 = area
                    )

                    rainfallViewModel.asessRainwater(request)
                },
                enabled =
                    !isLoading &&
                            roofArea.toDoubleOrNull() != null &&
                            roofArea.toDoubleOrNull()!! > 0,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF173B43)
                )
            ) {

                if (isLoading) {

                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )

                    Spacer(
                        modifier = Modifier.width(10.dp)
                    )

                    Text(
                        text = "Calculating...",
                        color = Color.White
                    )

                } else {

                    Icon(
                        imageVector =
                            Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = Color.White
                    )

                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )

                    Text(
                        text = "Calculate Harvest Potential",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            if (error.isNotEmpty()) {

                Spacer(
                    modifier = Modifier.height(15.dp)
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(15.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFE4E4)
                    )
                ) {

                    Text(
                        text = error,
                        color = Color(0xFFC62828),
                        modifier = Modifier.padding(15.dp),
                        fontSize = 13.sp
                    )
                }
            }


            // ---------------- RESULT ----------------

            result?.let { data ->

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF173B43)
                    ),
                    elevation =
                        CardDefaults.cardElevation(
                            defaultElevation = 6.dp
                        )
                ) {

                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment =
                            Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "Estimated Annual Harvest",
                            color = Color.White.copy(
                                alpha = 0.8f
                            ),
                            fontSize = 14.sp
                        )

                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )

                        Row(
                            verticalAlignment =
                                Alignment.CenterVertically
                        ) {

                            Icon(
                                imageVector =
                                    Icons.Default.LocationOn,
                                contentDescription = null,
                                tint =
                                    Color(0xFF65C6DA),
                                modifier = Modifier.size(35.dp)
                            )

                            Spacer(
                                modifier = Modifier.width(8.dp)
                            )

                            Text(
                                text = String.format(
                                    "%,.0f",
                                    data.harvestable_litres
                                ),
                                color = Color.White,
                                fontSize = 38.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(
                                modifier = Modifier.width(6.dp)
                            )

                            Text(
                                text = "L",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Text(
                            text = "per year",
                            color = Color.White.copy(
                                alpha = 0.7f
                            ),
                            fontSize = 13.sp
                        )

                        Spacer(
                            modifier = Modifier.height(25.dp)
                        )

                        ResultRow(
                            title = "Annual Rainfall",
                            value =
                                "${data.annual_rainfall_mm} mm"
                        )

                        ResultRow(
                            title = "Roof Area",
                            value =
                                "${data.roof_area_m2} m²"
                        )

                        ResultRow(
                            title = "Roof Type",
                            value =
                                data.roof_type.replaceFirstChar {
                                    it.uppercase()
                                }
                        )

                        ResultRow(
                            title = "Runoff Coefficient",
                            value =
                                data.runoff_coefficient_used
                                    .toString()
                        )
                    }
                }
            }
        }
                Spacer(
                    modifier = Modifier.height(30.dp)
                )
            }
        }


@Composable
private fun ResultRow(
    title: String,
    value: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp),
        horizontalArrangement =
            Arrangement.SpaceBetween
    ) {

        Text(
            text = title,
            color = Color.White.copy(
                alpha = 0.7f
            ),
            fontSize = 13.sp
        )

        Text(
            text = value,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
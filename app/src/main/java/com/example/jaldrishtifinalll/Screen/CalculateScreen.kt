package com.example.jaldrishtifinalll.Screen

import android.Manifest
import android.content.pm.PackageManager
import android.os.Looper

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
import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

import com.example.jaldrishtifinalll.ViewModel.RainfallViewModel
import com.example.jaldrishtifinalll.model.RainfallRequest

import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun CalculateScreen(
    navController: NavController,
    rainfallViewModel: RainfallViewModel = viewModel()
) {

    val context = LocalContext.current

    // ---------------------------------------------------------
    // VIEWMODEL STATES
    // ---------------------------------------------------------

    val result by
    rainfallViewModel.result.collectAsState()

    val isLoading by
    rainfallViewModel.isLoading.collectAsState()

    val error by
    rainfallViewModel.error.collectAsState()

    val detailedReport by
    rainfallViewModel.detailedReport.collectAsState()

    val reportLoading by
    rainfallViewModel.reportLoading.collectAsState()

    val reportError by
    rainfallViewModel.reportError.collectAsState()


    // ---------------------------------------------------------
    // LOCAL UI STATE
    // ---------------------------------------------------------

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


    // Fallback location so map/backend can work even before GPS.
    val fallbackLatitude = 29.9695
    val fallbackLongitude = 76.8783

    val mapLatitude =
        latitude ?: fallbackLatitude

    val mapLongitude =
        longitude ?: fallbackLongitude


    // ---------------------------------------------------------
    // ROOF TYPES
    // ---------------------------------------------------------

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


    // ---------------------------------------------------------
    // LOCATION CLIENT
    // ---------------------------------------------------------

    val fusedLocationClient =
        remember {
            LocationServices
                .getFusedLocationProviderClient(context)
        }


    // ---------------------------------------------------------
    // START LOCATION UPDATE
    // ---------------------------------------------------------

    fun startLocationUpdates() {

        locationText =
            "Detecting current location..."


        val locationRequest =
            LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY,
                1000L
            )
                .setWaitForAccurateLocation(true)
                .setMaxUpdates(1)
                .build()


        val locationCallback =
            object : LocationCallback() {

                override fun onLocationResult(
                    result: LocationResult
                ) {

                    val location =
                        result.lastLocation

                    if (location != null) {

                        latitude =
                            location.latitude

                        longitude =
                            location.longitude

                        locationText =
                            "Location detected successfully"

                    } else {

                        locationText =
                            "Using default location"
                    }
                }
            }


        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }


    // ---------------------------------------------------------
    // PERMISSION LAUNCHER
    // ---------------------------------------------------------

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

                startLocationUpdates()

            } else {

                locationText =
                    "Permission denied - using default location"
            }
        }


    // ---------------------------------------------------------
    // REQUEST LOCATION
    // ---------------------------------------------------------

    fun requestLocation() {

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


        startLocationUpdates()
    }


    // ---------------------------------------------------------
    // CAMERA
    // ---------------------------------------------------------

    val currentLocation =
        LatLng(
            mapLatitude,
            mapLongitude
        )


    val cameraPositionState =
        rememberCameraPositionState {
            position =
                CameraPosition.fromLatLngZoom(
                    currentLocation,
                    16f
                )
        }


    // Move camera whenever GPS location changes.
    LaunchedEffect(
        mapLatitude,
        mapLongitude
    ) {

        cameraPositionState.position =
            CameraPosition.fromLatLngZoom(
                LatLng(
                    mapLatitude,
                    mapLongitude
                ),
                16f
            )
    }


    // ---------------------------------------------------------
    // GOOGLE MAP CONFIG
    // ---------------------------------------------------------

    val mapProperties =
        remember {
            MapProperties(
                mapType =
                    MapType.SATELLITE
            )
        }


    val mapUiSettings =
        remember {
            MapUiSettings(
                zoomControlsEnabled = true,
                myLocationButtonEnabled = false,
                compassEnabled = true
            )
        }


    // ---------------------------------------------------------
    // MAIN SCREEN
    // ---------------------------------------------------------

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


        // -----------------------------------------------------
        // HEADER
        // -----------------------------------------------------

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
            modifier =
                Modifier.height(8.dp)
        )

        Text(
            text =
                "Estimate how much rainwater your roof can harvest.",
            color =
                Color.White.copy(
                    alpha = 0.9f
                ),
            fontSize = 14.sp
        )


        Spacer(
            modifier =
                Modifier.height(25.dp)
        )


        // -----------------------------------------------------
        // LOCATION CARD
        // -----------------------------------------------------

        Card(
            modifier =
                Modifier.fillMaxWidth(),
            shape =
                RoundedCornerShape(24.dp),
            colors =
                CardDefaults.cardColors(
                    containerColor =
                        Color(0xFFEAF7FA)
                )
        ) {

            Column(
                modifier =
                    Modifier.padding(20.dp)
            ) {

                Row(
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Box(
                        modifier =
                            Modifier
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
                        modifier =
                            Modifier.width(13.dp)
                    )


                    Column(
                        modifier =
                            Modifier.weight(1f)
                    ) {

                        Text(
                            text =
                                "Your Location",
                            fontSize = 17.sp,
                            fontWeight =
                                FontWeight.Bold
                        )

                        Spacer(
                            modifier =
                                Modifier.height(3.dp)
                        )

                        Text(
                            text =
                                locationText,
                            color =
                                Color.Gray,
                            fontSize = 12.sp
                        )
                    }


                    Icon(
                        imageVector =
                            if (latitude != null)
                                Icons.Default.CheckCircle
                            else
                                Icons.Default.Refresh,
                        contentDescription =
                            null,
                        tint =
                            if (latitude != null)
                                Color(0xFF29A36A)
                            else
                                Color(0xFF3FA8BD),
                        modifier =
                            Modifier
                                .size(25.dp)
                                .clickable {
                                    requestLocation()
                                }
                    )
                }


                Spacer(
                    modifier =
                        Modifier.height(15.dp)
                )


                Button(
                    onClick = {
                        requestLocation()
                    },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                    shape =
                        RoundedCornerShape(12.dp),
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor =
                                Color(0xFF5BC0D7)
                        )
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.LocationOn,
                        contentDescription =
                            null,
                        tint =
                            Color.Black
                    )

                    Spacer(
                        modifier =
                            Modifier.width(8.dp)
                    )

                    Text(
                        text =
                            if (latitude != null)
                                "Location Detected"
                            else
                                "Use My Current Location",
                        color =
                            Color.Black
                    )
                }
            }
        }


        Spacer(
            modifier =
                Modifier.height(18.dp)
        )


        // -----------------------------------------------------
        // GOOGLE MAP
        // -----------------------------------------------------

        Card(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(320.dp),
            shape =
                RoundedCornerShape(24.dp)
        ) {

            GoogleMap(
                modifier =
                    Modifier.fillMaxSize(),
                cameraPositionState =
                    cameraPositionState,
                properties =
                    mapProperties,
                uiSettings =
                    mapUiSettings
            ) {

                Marker(
                    state =
                        MarkerState(
                            position =
                                currentLocation
                        ),
                    title =
                        if (latitude != null)
                            "Your Location"
                        else
                            "Default Location"
                )
            }
        }


        Spacer(
            modifier =
                Modifier.height(18.dp)
        )


        // -----------------------------------------------------
        // ROOF DETAILS CARD
        // -----------------------------------------------------

        Card(
            modifier =
                Modifier.fillMaxWidth(),
            shape =
                RoundedCornerShape(24.dp),
            colors =
                CardDefaults.cardColors(
                    containerColor =
                        Color(0xFFEAF7FA)
                )
        ) {

            Column(
                modifier =
                    Modifier.padding(20.dp)
            ) {

                Row(
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.LocationOn,
                        contentDescription =
                            null,
                        tint =
                            Color(0xFF3FA8BD)
                    )

                    Spacer(
                        modifier =
                            Modifier.width(10.dp)
                    )

                    Text(
                        text =
                            "Roof Details",
                        fontSize = 20.sp,
                        fontWeight =
                            FontWeight.Bold
                    )
                }


                Spacer(
                    modifier =
                        Modifier.height(22.dp)
                )


                // ROOF AREA

                Text(
                    text =
                        "Roof Area",
                    fontSize = 13.sp,
                    color =
                        Color.Gray
                )


                Spacer(
                    modifier =
                        Modifier.height(6.dp)
                )


                OutlinedTextField(
                    value =
                        roofArea,
                    onValueChange = {
                        roofArea = it
                    },
                    modifier =
                        Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            "Enter roof area"
                        )
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
                    shape =
                        RoundedCornerShape(13.dp)
                )


                Spacer(
                    modifier =
                        Modifier.height(18.dp)
                )


                // ROOF TYPE

                Text(
                    text =
                        "Roof Type",
                    fontSize = 13.sp,
                    color =
                        Color.Gray
                )


                Spacer(
                    modifier =
                        Modifier.height(6.dp)
                )


                Box(
                    modifier =
                        Modifier.fillMaxWidth()
                ) {

                    OutlinedTextField(
                        value =
                            roofType,
                        onValueChange = {},
                        readOnly = true,
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .clickable {
                                    expanded =
                                        !expanded
                                },
                        singleLine = true,
                        shape =
                            RoundedCornerShape(13.dp),
                        trailingIcon = {

                            Icon(
                                imageVector =
                                    if (expanded)
                                        Icons.Default.KeyboardArrowUp
                                    else
                                        Icons.Default.KeyboardArrowDown,
                                contentDescription =
                                    "Select Roof Type",
                                modifier =
                                    Modifier.clickable {
                                        expanded =
                                            !expanded
                                    }
                            )
                        }
                    )


                    DropdownMenu(
                        expanded =
                            expanded,
                        onDismissRequest = {
                            expanded =
                                false
                        }
                    ) {

                        roofTypes.forEach { type ->

                            DropdownMenuItem(
                                text = {
                                    Text(type)
                                },
                                onClick = {

                                    roofType =
                                        type

                                    expanded =
                                        false
                                }
                            )
                        }
                    }
                }


                Spacer(
                    modifier =
                        Modifier.height(20.dp)
                )


                // ------------------------------------------------
                // CALCULATE
                // ------------------------------------------------

                Button(
                    onClick = {

                        val area =
                            roofArea.toDoubleOrNull()


                        if (
                            area == null ||
                            area <= 0
                        ) {
                            return@Button
                        }


                        /*
                         * Use actual GPS coordinates when
                         * available.
                         *
                         * Otherwise use fallback coordinates
                         * so backend can still be tested.
                         */

                        val requestLatitude =
                            latitude
                                ?: fallbackLatitude

                        val requestLongitude =
                            longitude
                                ?: fallbackLongitude


                        val request =
                            RainfallRequest(

                                place = null,

                                lat =
                                    requestLatitude,

                                lon =
                                    requestLongitude,

                                roof_type =
                                    when (roofType) {

                                        "Concrete" ->
                                            "concrete"

                                        "Metal" ->
                                            "metal"

                                        "Tiled" ->
                                            "tiled"

                                        "Asbestos" ->
                                            "asbestos"

                                        "Flat RCC" ->
                                            "flat_rcc"

                                        "Sloped GI Sheet" ->
                                            "sloped_gi_sheet"

                                        "Thatched" ->
                                            "thatched"

                                        "Green Roof" ->
                                            "green_roof"

                                        else ->
                                            "concrete"
                                    },

                                roof_area_m2 =
                                    area
                            )


                        rainfallViewModel
                            .asessRainwater(
                                request
                            )
                    },


                    enabled =
                        !isLoading &&
                                roofArea
                                    .toDoubleOrNull()
                                    ?.let {
                                        it > 0
                                    } == true,


                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(58.dp),


                    shape =
                        RoundedCornerShape(15.dp),


                    colors =
                        ButtonDefaults
                            .buttonColors(
                                containerColor =
                                    Color(0xFF173B43)
                            )
                ) {

                    if (isLoading) {

                        CircularProgressIndicator(
                            modifier =
                                Modifier.size(22.dp),
                            color =
                                Color.White,
                            strokeWidth =
                                2.dp
                        )

                        Spacer(
                            modifier =
                                Modifier.width(10.dp)
                        )

                        Text(
                            text =
                                "Calculating...",
                            color =
                                Color.White
                        )

                    } else {

                        Icon(
                            imageVector =
                                Icons.Default.LocationOn,
                            contentDescription =
                                null,
                            tint =
                                Color.White
                        )

                        Spacer(
                            modifier =
                                Modifier.width(8.dp)
                        )

                        Text(
                            text =
                                "Calculate Harvest Potential",
                            color =
                                Color.White,
                            fontSize =
                                15.sp,
                            fontWeight =
                                FontWeight.SemiBold
                        )
                    }
                }


                // ------------------------------------------------
                // CALCULATION ERROR
                // ------------------------------------------------

                if (error.isNotEmpty()) {

                    Spacer(
                        modifier =
                            Modifier.height(15.dp)
                    )


                    Card(
                        modifier =
                            Modifier.fillMaxWidth(),
                        shape =
                            RoundedCornerShape(15.dp),
                        colors =
                            CardDefaults.cardColors(
                                containerColor =
                                    Color(0xFFFFE4E4)
                            )
                    ) {

                        Text(
                            text =
                                error,
                            color =
                                Color(0xFFC62828),
                            modifier =
                                Modifier.padding(15.dp),
                            fontSize =
                                13.sp
                        )
                    }
                }
            }
        }


        // ---------------------------------------------------------
        // RESULT CARD
        // ---------------------------------------------------------

        result?.let { data ->

            Spacer(
                modifier =
                    Modifier.height(20.dp)
            )


            Card(
                modifier =
                    Modifier.fillMaxWidth(),
                shape =
                    RoundedCornerShape(28.dp),
                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            Color(0xFF173B43)
                    ),
                elevation =
                    CardDefaults.cardElevation(
                        defaultElevation =
                            6.dp
                    )
            ) {

                Column(
                    modifier =
                        Modifier.padding(24.dp),
                    horizontalAlignment =
                        Alignment.CenterHorizontally
                ) {

                    Text(
                        text =
                            "Estimated Annual Harvest",
                        color =
                            Color.White.copy(
                                alpha = 0.8f
                            ),
                        fontSize =
                            14.sp
                    )


                    Spacer(
                        modifier =
                            Modifier.height(10.dp)
                    )


                    Row(
                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.LocationOn,
                            contentDescription =
                                null,
                            tint =
                                Color(0xFF65C6DA),
                            modifier =
                                Modifier.size(35.dp)
                        )


                        Spacer(
                            modifier =
                                Modifier.width(8.dp)
                        )


                        Text(
                            text =
                                String.format(
                                    "%,.0f",
                                    data.harvestable_litres
                                ),
                            color =
                                Color.White,
                            fontSize =
                                38.sp,
                            fontWeight =
                                FontWeight.Bold
                        )


                        Spacer(
                            modifier =
                                Modifier.width(6.dp)
                        )


                        Text(
                            text =
                                "L",
                            color =
                                Color.White,
                            fontSize =
                                20.sp,
                            fontWeight =
                                FontWeight.Bold
                        )
                    }


                    Text(
                        text =
                            "per year",
                        color =
                            Color.White.copy(
                                alpha = 0.7f
                            ),
                        fontSize =
                            13.sp
                    )


                    Spacer(
                        modifier =
                            Modifier.height(25.dp)
                    )


                    ResultRow(
                        title =
                            "Annual Rainfall",
                        value =
                            "${data.annual_rainfall_mm} mm"
                    )


                    ResultRow(
                        title =
                            "Roof Area",
                        value =
                            "${data.roof_area_m2} m²"
                    )


                    ResultRow(
                        title =
                            "Roof Type",
                        value =
                            data.roof_type
                                .replaceFirstChar {
                                    it.uppercase()
                                }
                    )


                    ResultRow(
                        title =
                            "Runoff Coefficient",
                        value =
                            data.runoff_coefficient_used
                                .toString()
                    )


                    Spacer(
                        modifier =
                            Modifier.height(20.dp)
                    )


                    // -------------------------------------------------
                    // DETAILED REPORT BUTTON
                    // -------------------------------------------------

                    Button(
                        onClick = {

                            navController.navigate(
                                "detailedReport"
                            )
                        },


                        enabled =
                            detailedReport != null &&
                                    !reportLoading,


                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(54.dp),


                        shape =
                            RoundedCornerShape(15.dp),


                        colors =
                            ButtonDefaults.buttonColors(
                                containerColor =
                                    Color(0xFF5BC0D7)
                            )
                    ) {

                        if (reportLoading) {

                            CircularProgressIndicator(
                                modifier =
                                    Modifier.size(20.dp),
                                color =
                                    Color.Black,
                                strokeWidth =
                                    2.dp
                            )


                            Spacer(
                                modifier =
                                    Modifier.width(10.dp)
                            )


                            Text(
                                text =
                                    "Preparing Detailed Report...",
                                color =
                                    Color.Black,
                                fontSize =
                                    14.sp
                            )

                        } else {

                            Icon(
                                imageVector =
                                    Icons.Default.ArrowForward,
                                contentDescription =
                                    null,
                                tint =
                                    Color.Black
                            )


                            Spacer(
                                modifier =
                                    Modifier.width(8.dp)
                            )


                            Text(
                                text =
                                    "View Detailed Report",
                                color =
                                    Color.Black,
                                fontSize =
                                    15.sp,
                                fontWeight =
                                    FontWeight.Bold
                            )
                        }
                    }
                }
            }


            // -----------------------------------------------------
            // RAG ERROR
            // -----------------------------------------------------

            if (reportError.isNotEmpty()) {

                Spacer(
                    modifier =
                        Modifier.height(10.dp)
                )


                Text(
                    text =
                        reportError,
                    color =
                        Color(0xFFC62828),
                    fontSize =
                        12.sp,
                    modifier =
                        Modifier.padding(
                            horizontal = 4.dp
                        )
                )
            }
        }


        Spacer(
            modifier =
                Modifier.height(30.dp)
        )
    }
}


// -------------------------------------------------------------
// RESULT ROW
// -------------------------------------------------------------

@Composable
private fun ResultRow(
    title: String,
    value: String
) {

    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 7.dp
                ),
        horizontalArrangement =
            Arrangement.SpaceBetween
    ) {

        Text(
            text =
                title,
            color =
                Color.White.copy(
                    alpha = 0.7f
                ),
            fontSize =
                13.sp
        )


        Text(
            text =
                value,
            color =
                Color.White,
            fontSize =
                14.sp,
            fontWeight =
                FontWeight.SemiBold
        )
    }
}
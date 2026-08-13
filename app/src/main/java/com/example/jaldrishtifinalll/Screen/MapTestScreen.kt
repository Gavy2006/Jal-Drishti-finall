package com.example.jaldrishtifinalll.Screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun MapTestScreen() {

    val kurukshetra = LatLng(
        29.9695,
        76.8783
    )

    val cameraPositionState =
        rememberCameraPositionState {

            position =
                CameraPosition.fromLatLngZoom(
                    kurukshetra,
                    15f
                )
        }

    GoogleMap(
        modifier = Modifier,
        cameraPositionState = cameraPositionState,
        properties = MapProperties(
            mapType = MapType.SATELLITE
        )
    )

}
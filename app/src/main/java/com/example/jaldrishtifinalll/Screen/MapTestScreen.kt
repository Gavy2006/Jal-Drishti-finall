package com.example.jaldrishtifinalll.Screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.arcgismaps.mapping.ArcGISMap
import com.arcgismaps.mapping.BasemapStyle
import com.arcgismaps.mapping.Viewpoint
import com.arcgismaps.toolkit.geoviewcompose.MapView

@Composable
fun MapTestScreen() {

    val map = remember {
        ArcGISMap(
            BasemapStyle.ArcGISImagery
        ).apply {

            initialViewpoint = Viewpoint(
                latitude = 29.969,
                longitude = 76.852,
                scale = 15000.0
            )
        }
    }

    MapView(
        modifier = Modifier.fillMaxSize(),
        arcGISMap = map
    )
}
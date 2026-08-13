package com.example.jaldrishtifinalll.Screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.jaldrishtifinalll.BuildConfig
import com.maptiler.maptilersdk.MTConfig
import com.maptiler.maptilersdk.map.MTMapOptions
import com.maptiler.maptilersdk.map.MTMapView
import com.maptiler.maptilersdk.map.MTMapViewController
import com.maptiler.maptilersdk.map.style.MTMapReferenceStyle
import com.maptiler.maptilersdk.map.LngLat
@Composable
fun MapTestScreen() {

    val context = LocalContext.current

    MTConfig.apiKey = BuildConfig.MAPTILER_API_KEY

    val controller = remember {
        MTMapViewController(context)
    }

    MTMapView(
        referenceStyle = MTMapReferenceStyle.SATELLITE,
        options = MTMapOptions(
            center = LngLat(
                lng = 76.7570,
                lat = 29.6899
            ),
            zoom = 18.0
        ),
        controller = controller,
        modifier = Modifier.fillMaxSize()
    )
}
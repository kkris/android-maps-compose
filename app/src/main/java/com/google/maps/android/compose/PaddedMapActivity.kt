package com.google.maps.android.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

class PaddedMapActivity : ComponentActivity() {

    companion object {
        private val OVERLAY_HEIGHT = 250.dp
        private val center = LatLng(1.35, 103.87)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isMapLoaded by remember { mutableStateOf(false) }
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(center, 11f)
            }
            val singaporeState = rememberMarkerState(position = center)

            Box(Modifier.fillMaxSize()) {
                GoogleMap(
                    cameraPositionState = cameraPositionState,
                    onMapLoaded = {
                        isMapLoaded = true
                    },
                    contentPadding = PaddingValues(
                        bottom = OVERLAY_HEIGHT
                    ),
                ) {
                    Marker(
                        state = singaporeState,
                        title = "Center"
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White.copy(alpha = 0.75f))
                        .height(OVERLAY_HEIGHT)
                        .align(Alignment.BottomCenter)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier.padding(10.dp).fillMaxWidth(),
                            text = "This overlay covers the bottom part of the map",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Button(
                            onClick = {
                                cameraPositionState.position = CameraPosition.fromLatLngZoom(center, 11f)
                            }
                        ) {
                            Text("Set camera position to initial value")

                        }
                    }
                }
                if (!isMapLoaded) {
                    AnimatedVisibility(
                        modifier = Modifier
                            .matchParentSize(),
                        visible = !isMapLoaded,
                        enter = EnterTransition.None,
                        exit = fadeOut()
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .background(MaterialTheme.colors.background)
                                .wrapContentSize()
                        )
                    }
                }
            }
        }
    }
}

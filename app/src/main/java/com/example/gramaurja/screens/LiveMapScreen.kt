package com.example.gramaurja.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.gramaurja.ReportData
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore
import com.google.maps.android.compose.*

@Composable
fun LiveMapScreen(navController: NavController) {

    val db = FirebaseFirestore.getInstance()

    var reports by remember {

        mutableStateOf(listOf<ReportData>())
    }

    LaunchedEffect(Unit) {

        db.collection("reports")

            .addSnapshotListener { value, error ->

                if (error != null) {

                    return@addSnapshotListener
                }

                reports =
                    value?.documents?.mapNotNull {

                        try {

                            it.toObject(
                                ReportData::class.java
                            )

                        } catch (e: Exception) {

                            null
                        }
                    } ?: emptyList()
            }
    }

    val defaultLocation =
        LatLng(12.9716, 77.5946)

    val cameraPositionState =
        rememberCameraPositionState {

            position = CameraPosition.fromLatLngZoom(
                defaultLocation,
                10f
            )
        }

    GoogleMap(

        modifier = Modifier.fillMaxSize(),

        cameraPositionState =
            cameraPositionState
    ) {

        reports.forEach { report ->

            Marker(

                state = MarkerState(

                    position = LatLng(
                        report.latitude,
                        report.longitude
                    )
                ),

                title = report.issueType,

                snippet =
                    "${report.villageName} - ${report.status}"
            )
        }
    }
}
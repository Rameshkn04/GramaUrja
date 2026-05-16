package com.example.gramaurja.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.gramaurja.ReportData
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(navController: NavController) {

    val context = LocalContext.current

    val db = FirebaseFirestore.getInstance()

    val fusedLocationClient =
        LocationServices.getFusedLocationProviderClient(
            context
        )

    var latitude by remember {
        mutableStateOf(12.9716)
    }

    var longitude by remember {
        mutableStateOf(77.5946)
    }

    var userName by remember {
        mutableStateOf("")
    }

    var contactNumber by remember {
        mutableStateOf("")
    }

    var villageName by remember {
        mutableStateOf("")
    }

    var issueType by remember {
        mutableStateOf("")
    }

    var otherIssue by remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val issueOptions = listOf(
        "Power Failure",
        "Voltage Fluctuation",
        "Transformer Issue",
        "Wire Damage",
        "Street Light Failure",
        "Other"
    )

    val imagePickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->

            selectedImageUri = uri
        }

    @SuppressLint("MissingPermission")
    fun fetchLocation() {

        fusedLocationClient.lastLocation

            .addOnSuccessListener { location ->

                if (location != null) {

                    latitude = location.latitude

                    longitude = location.longitude
                }
            }
    }

    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract =
                ActivityResultContracts.RequestPermission()
        ) { isGranted ->

            if (isGranted) {

                fetchLocation()

            } else {

                Toast.makeText(
                    context,
                    "Location Permission Denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    LaunchedEffect(Unit) {

        when {

            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {

                fetchLocation()
            }

            else -> {

                permissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0B3D2E),
                        Color(0xFF1E8449)
                    )
                )
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Report Power Issue",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(30.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(24.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    OutlinedTextField(

                        value = userName,

                        onValueChange = {
                            userName = it
                        },

                        label = {
                            Text("User Name")
                        },

                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(16.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(

                        value = contactNumber,

                        onValueChange = {
                            contactNumber = it
                        },

                        label = {
                            Text("Contact Number")
                        },

                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(16.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(

                        value = villageName,

                        onValueChange = {
                            villageName = it
                        },

                        label = {
                            Text("Village Name")
                        },

                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(16.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    ExposedDropdownMenuBox(

                        expanded = expanded,

                        onExpandedChange = {
                            expanded = !expanded
                        }
                    ) {

                        OutlinedTextField(

                            value = issueType,

                            onValueChange = {},

                            readOnly = true,

                            label = {
                                Text("Select Issue Type")
                            },

                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expanded
                                )
                            },

                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),

                            shape = RoundedCornerShape(16.dp)
                        )

                        ExposedDropdownMenu(

                            expanded = expanded,

                            onDismissRequest = {
                                expanded = false
                            }
                        ) {

                            issueOptions.forEach { issue ->

                                DropdownMenuItem(

                                    text = {
                                        Text(issue)
                                    },

                                    onClick = {

                                        issueType = issue

                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    if (issueType == "Other") {

                        Spacer(modifier = Modifier.height(20.dp))

                        OutlinedTextField(

                            value = otherIssue,

                            onValueChange = {
                                otherIssue = it
                            },

                            label = {
                                Text("Enter Other Issue")
                            },

                            modifier = Modifier.fillMaxWidth(),

                            shape = RoundedCornerShape(16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(

                        value = description,

                        onValueChange = {
                            description = it
                        },

                        label = {
                            Text("Description")
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),

                        shape = RoundedCornerShape(16.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(

                        onClick = {

                            imagePickerLauncher.launch("image/*")
                        },

                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(16.dp)
                    ) {

                        Text("Upload Optional Photo")
                    }

                    if (selectedImageUri != null) {

                        Spacer(modifier = Modifier.height(20.dp))

                        Image(
                            painter = rememberAsyncImagePainter(selectedImageUri),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Current GPS Location",
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Latitude: $latitude"
                    )

                    Text(
                        text = "Longitude: $longitude"
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    Button(

                        onClick = {

                            if (
                                userName.isEmpty() ||
                                contactNumber.isEmpty() ||
                                villageName.isEmpty() ||
                                issueType.isEmpty() ||
                                description.isEmpty()
                            ) {

                                Toast.makeText(
                                    context,
                                    "Fill all required fields",
                                    Toast.LENGTH_SHORT
                                ).show()

                                return@Button
                            }

                            val timestamp = SimpleDateFormat(
                                "dd MMM yyyy, hh:mm a",
                                Locale.getDefault()
                            ).format(Date())

                            val reportData = ReportData(

                                userName = userName,

                                contactNumber = contactNumber,

                                villageName = villageName,

                                issueType = issueType,

                                otherIssue = otherIssue,

                                description = description,

                                imageUrl = selectedImageUri?.toString() ?: "",

                                latitude = latitude,

                                longitude = longitude,

                                timestamp = timestamp,

                                status = "Pending"
                            )

                            db.collection("reports")

                                .add(reportData)

                                .addOnSuccessListener {

                                    Toast.makeText(
                                        context,
                                        "Report Submitted Successfully",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    userName = ""
                                    contactNumber = ""
                                    villageName = ""
                                    issueType = ""
                                    otherIssue = ""
                                    description = ""
                                    selectedImageUri = null
                                }

                                .addOnFailureListener {

                                    Toast.makeText(
                                        context,
                                        "Failed to Submit Report",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),

                        shape = RoundedCornerShape(16.dp)
                    ) {

                        Text(
                            text = "Submit Report",
                            fontSize = 18.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}
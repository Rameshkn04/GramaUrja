package com.example.gramaurja.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Power
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gramaurja.DashboardData
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun HomeScreen(navController: NavController) {

    val db = FirebaseFirestore.getInstance()

    var dashboardData by remember {

        mutableStateOf(DashboardData())
    }

    LaunchedEffect(Unit) {

        db.collection("dashboard")

            .get()

            .addOnSuccessListener { result ->

                if (!result.isEmpty) {

                    dashboardData =
                        result.documents[0]
                            .toObject(DashboardData::class.java)
                            ?: DashboardData()
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

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {

            item {

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Welcome 👋",
                    color = Color.White,
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Grama Urja Dashboard",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(30.dp))
            }

            item {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    DashboardCard(
                        title = "Power Status",
                        value = dashboardData.powerStatus,
                        iconColor = Color(0xFF4CAF50),
                        backgroundColor = Color.White,
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Power,
                                contentDescription = null,
                                tint = Color(0xFF4CAF50),
                                modifier = Modifier.size(40.dp)
                            )
                        },
                        modifier = Modifier.weight(1f)
                    )

                    DashboardCard(
                        title = "Fault Alerts",
                        value = dashboardData.faultAlerts,
                        iconColor = Color.Red,
                        backgroundColor = Color.White,
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = null,
                                tint = Color.Red,
                                modifier = Modifier.size(40.dp)
                            )
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    DashboardCard(
                        title = "Energy Usage",
                        value = dashboardData.energyUsage,
                        iconColor = Color(0xFFFFC107),
                        backgroundColor = Color.White,
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Bolt,
                                contentDescription = null,
                                tint = Color(0xFFFFC107),
                                modifier = Modifier.size(40.dp)
                            )
                        },
                        modifier = Modifier.weight(1f)
                    )

                    DashboardCard(
                        title = "System Health",
                        value = dashboardData.systemHealth,
                        iconColor = Color(0xFF2196F3),
                        backgroundColor = Color.White,
                        icon = {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = Color(0xFF2196F3),
                                modifier = Modifier.size(40.dp)
                            )
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {

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

                        Text(
                            text = "Village Electricity Overview",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0B3D2E)
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        OverviewRow(
                            title = "Total Houses Connected",
                            value = dashboardData.totalHouses
                        )

                        OverviewRow(
                            title = "Current Power Consumption",
                            value = dashboardData.energyUsage
                        )

                        OverviewRow(
                            title = "System Status",
                            value = dashboardData.powerStatus
                        )

                        OverviewRow(
                            title = "Power Failures Today",
                            value = dashboardData.faultAlerts
                        )
                    }
                }
            }

            item {

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

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                imageVector = Icons.Default.ElectricBolt,
                                contentDescription = null,
                                tint = Color(0xFFFFC107),
                                modifier = Modifier.size(36.dp)
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Text(
                                text = "Smart Insights",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0B3D2E)
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "• Live monitoring connected to Firebase.",
                            fontSize = 16.sp,
                            color = Color.DarkGray
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "• Realtime outage reporting enabled.",
                            fontSize = 16.sp,
                            color = Color.DarkGray
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "• Smart village electricity system active.",
                            fontSize = 16.sp,
                            color = Color.DarkGray
                        )
                    }
                }
            }

            item {

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

                        Text(
                            text = "Power Usage Analytics",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0B3D2E)
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                        ) {

                            PowerUsageChart()
                        }
                    }
                }
            }

            item {

                Spacer(modifier = Modifier.height(20.dp))

                Button(

                    onClick = {

                        navController.navigate("map")
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),

                    shape = RoundedCornerShape(16.dp)
                ) {

                    Text(
                        text = "Open Live Village Map"
                    )
                }

                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@Composable
fun DashboardCard(
    title: String,
    value: String,
    iconColor: Color,
    backgroundColor: Color,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        )
    ) {

        Column(
            modifier = Modifier
                .padding(18.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            icon()

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = title,
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = iconColor
            )
        }
    }
}

@Composable
fun OverviewRow(
    title: String,
    value: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),

        horizontalArrangement =
            Arrangement.SpaceBetween
    ) {

        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.DarkGray
        )

        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0B3D2E)
        )
    }
}
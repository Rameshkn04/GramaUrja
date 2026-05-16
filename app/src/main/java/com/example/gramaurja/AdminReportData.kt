package com.example.gramaurja

data class AdminReportData(

    val userName: String = "",

    val contactNumber: String = "",

    val villageName: String = "",

    val issueType: String = "",

    val otherIssue: String = "",

    val description: String = "",

    val imageUrl: String = "",

    val latitude: Double = 0.0,

    val longitude: Double = 0.0,

    val timestamp: String = "",

    val status: String = "Pending"
)
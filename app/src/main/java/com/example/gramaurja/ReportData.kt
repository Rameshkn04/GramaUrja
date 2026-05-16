package com.example.gramaurja

data class ReportData(

    val userName: String = "",

    val contactNumber: String = "",

    val villageName: String = "",

    val issueType: String = "",

    val otherIssue: String = "",

    val description: String = "",

    val imageUrl: String = "",

    val latitude: Double = 12.9716,

    val longitude: Double = 77.5946,

    val timestamp: String = "",

    val status: String = "Pending"
)
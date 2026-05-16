package com.example.gramaurja.screens

import android.graphics.Color
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

@Composable
fun PowerUsageChart() {

    AndroidView(

        modifier = Modifier,

        factory = { context ->

            val lineChart = LineChart(context)

            lineChart.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            val entries = arrayListOf(

                Entry(1f, 50f),

                Entry(2f, 80f),

                Entry(3f, 65f),

                Entry(4f, 95f),

                Entry(5f, 70f),

                Entry(6f, 120f),

                Entry(7f, 100f)
            )

            val dataSet = LineDataSet(
                entries,
                "Weekly Power Usage"
            )

            dataSet.color = Color.rgb(30, 132, 73)

            dataSet.valueTextColor = Color.BLACK

            dataSet.lineWidth = 3f

            dataSet.circleRadius = 5f

            dataSet.setCircleColor(
                Color.rgb(30, 132, 73)
            )

            dataSet.setDrawFilled(true)

            dataSet.fillColor =
                Color.rgb(144, 238, 144)

            val lineData = LineData(dataSet)

            lineChart.data = lineData

            lineChart.description.isEnabled = false

            lineChart.setTouchEnabled(false)

            lineChart.setPinchZoom(false)

            lineChart.legend.isEnabled = true

            val xAxis = lineChart.xAxis

            xAxis.position = XAxis.XAxisPosition.BOTTOM

            xAxis.textColor = Color.BLACK

            xAxis.setDrawGridLines(false)

            val leftAxis: YAxis =
                lineChart.axisLeft

            leftAxis.textColor = Color.BLACK

            leftAxis.setDrawGridLines(true)

            lineChart.axisRight.isEnabled = false

            lineChart.animateX(1500)

            lineChart.invalidate()

            lineChart
        }
    )
}
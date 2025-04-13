package com.example.fitness91.features.history.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.chart.line.lineSpec
import com.patrykandpatrick.vico.compose.chart.scroll.rememberChartScrollSpec
import com.patrykandpatrick.vico.compose.component.shape.shader.verticalGradient
import com.patrykandpatrick.vico.compose.style.ChartStyle
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import kotlin.math.roundToInt

@Composable
fun ChartUI(
    icon: ImageVector,
    title: String,
    iconBackgroundColor: Color,
    iconColor: Color,
    chartModel: ChartEntryModelProducer,
    chartColor: Color,
    yAxisFormatter: (Float) -> String = { value -> value.roundToInt().toString() }
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = iconColor,
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(iconBackgroundColor)
                    .padding(6.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(192.dp)
        ) {
            val chartColors = ChartStyle(
                axis = ChartStyle.Axis(
                    axisLabelColor = MaterialTheme.colorScheme.tertiary,
                    axisGuidelineColor = Color.Transparent,
                    axisLineColor = MaterialTheme.colorScheme.tertiary
                ),
                columnChart = ChartStyle.ColumnChart(columns = listOf(LineComponent(color = Color.Transparent.toArgb()))),
                lineChart = ChartStyle.LineChart(
                    lines = listOf(
                        lineSpec(
                            lineColor = chartColor,
                            lineBackgroundShader = verticalGradient(
                                colors = arrayOf(
                                    chartColor.copy(alpha = 0.5f),
                                    Color.Transparent
                                )
                            )
                        )
                    )
                ),
                marker = ChartStyle.Marker(),
                elevationOverlayColor = Color.Transparent,
            )

            ProvideChartStyle(chartStyle = chartColors) {
                val bottomAxis = rememberBottomAxis(
                    valueFormatter = { value, _ ->
                        value.toInt().toString()
                    },
                    tickLength = 0.dp
                )

                val startAxis = rememberStartAxis(
                    valueFormatter = { value, _ ->
                        yAxisFormatter(value)
                    },
                    tickLength = 0.dp
                )

                Chart(
                    chart = lineChart(
                        lines = listOf(
                            lineSpec(
                                lineColor = chartColor,
                                lineBackgroundShader = verticalGradient(
                                    colors = arrayOf(
                                        chartColor.copy(alpha = 0.5f),
                                        Color.Transparent
                                    )
                                )
                            )
                        )
                    ),
                    chartModelProducer = chartModel,
                    startAxis = startAxis,
                    bottomAxis = bottomAxis,
                    chartScrollSpec = rememberChartScrollSpec(isScrollEnabled = true)
                )
            }
        }
    }
}
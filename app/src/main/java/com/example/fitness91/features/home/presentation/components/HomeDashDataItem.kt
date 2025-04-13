package com.example.fitness91.features.home.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitness91.R
import com.example.fitness91.theme.completed
import com.example.fitness91.theme.notCompleted
import com.example.fitness91.theme.notStarted
import com.example.fitness91.theme.started

@Composable
fun HomeDashDataItem(
    completedText: Int,
    target: Int,
    percentage: Int,
    title: Int
) {
    Column {
        Text(
            text = stringResource(title),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.tertiary
        )

        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = stringResource(
                    R.string.workout_count,
                    completedText,
                    target
                ),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = when {
                    percentage < 25 -> notStarted
                    percentage < 50 -> started
                    percentage < 80 -> notCompleted
                    else -> completed
                }
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = stringResource(
                    R.string.workout_percentage,
                    percentage
                ),
                fontSize = 14.sp,
                color = when {
                    percentage < 25 -> notStarted
                    percentage < 50 -> started
                    percentage < 80 -> notCompleted
                    else -> completed
                }
            )
        }
    }
}
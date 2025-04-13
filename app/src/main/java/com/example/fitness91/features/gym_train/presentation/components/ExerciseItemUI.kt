package com.example.fitness91.features.gym_train.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitness91.R
import com.example.fitness91.features.gym_train.presentation.model.ExerciseUI
import com.example.fitness91.theme.blue
import com.example.fitness91.theme.completed
import com.example.fitness91.theme.grey

@Composable
fun ExerciseItemUI(exercise: ExerciseUI, onExerciseClick: () -> Unit) {
    val isCompleted = exercise.sets == exercise.completedSet
    val inProgress = exercise.completedSet > 0 && !isCompleted

    val borderColor = when {
        isCompleted -> completed
        inProgress -> blue
        else -> grey
    }

    Column(
        modifier = Modifier
            .background(borderColor.copy(0.03f), RoundedCornerShape(8.dp))
            .padding(6.dp)
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = exercise.subExercise,
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = stringResource(
                        R.string.sets,
                        exercise.sets,
                        exercise.times,
                        exercise.unit
                    ),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.tertiary,
                )
            }

            val buttonText =
                if (isCompleted) stringResource(R.string.completed)
                else if (inProgress) stringResource(R.string.continue_)
                else stringResource(R.string.start)
            val buttonIcon =
                if (isCompleted) Icons.Default.CheckCircle else if (inProgress) Icons.Rounded.Schedule else Icons.Rounded.Add

            Button(
                onClick = { onExerciseClick() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = borderColor.copy(0.15f),
                    contentColor = borderColor,
                    disabledContentColor = borderColor,
                    disabledContainerColor = borderColor.copy(0.15f)
                ),
                shape = CircleShape,
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                modifier = Modifier.height(32.dp),
            ) {
                Icon(
                    imageVector = buttonIcon,
                    contentDescription = buttonText,
                    modifier = Modifier.size(16.dp),
                    tint = borderColor
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = buttonText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = borderColor
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
            Text(
                text = stringResource(
                    R.string.progress_sets,
                    exercise.completedSet,
                    exercise.completedSet
                ),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.tertiary
            )

            Box(
                modifier = Modifier
                    .padding(top = 6.dp)
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(borderColor.copy(0.2f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(exercise.completedSet.toFloat() / exercise.sets)
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(borderColor)
                )
            }
        }
    }
}
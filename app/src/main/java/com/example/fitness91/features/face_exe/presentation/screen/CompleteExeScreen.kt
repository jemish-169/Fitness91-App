package com.example.fitness91.features.face_exe.presentation.screen

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.RotateLeft
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fitness91.R
import com.example.fitness91.common.domain.state.AppState
import com.example.fitness91.common.presentation.HeaderBackIcon
import com.example.fitness91.features.face_exe.presentation.viewmodel.FaceExeViewmodel
import com.example.fitness91.theme.green
import com.example.fitness91.theme.lightGreen
import kotlin.math.roundToInt

@Composable
fun CompleteExeScreen(
    faceExeViewmodel: FaceExeViewmodel,
    onBackClick: () -> Unit
) {
    val exercise by faceExeViewmodel.toBeCompleteExe.collectAsStateWithLifecycle()
    val progress = (exercise.completedSet.toFloat() / exercise.sets.toFloat() * 100)
    val updateExerciseState by faceExeViewmodel.updateExerciseState.collectAsStateWithLifecycle()
    val animatedProgress by animateFloatAsState(targetValue = progress / 100f, label = "")
    var isUndoClicked by remember { mutableStateOf(false) }
    val context = LocalContext.current


    LaunchedEffect(updateExerciseState) {
        if (updateExerciseState is AppState.Error) {
            Toast.makeText(
                context,
                (updateExerciseState as AppState.Error<String>).error, Toast.LENGTH_SHORT
            ).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(12.dp)
    ) {

        HeaderBackIcon(stringResource(R.string.complete_exercise), onBackClick)
        Column(
            modifier = Modifier
                .padding(top = 24.dp)
                .padding(horizontal = 12.dp)
        ) {
            Text(
                text = exercise.exerciseName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = stringResource(
                    R.string.sets_and_units,
                    exercise.sets,
                    exercise.unit
                ),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.tertiary
            )


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(
                        R.string.progress_times,
                        exercise.completedSet,
                        exercise.sets
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = stringResource(R.string.overall_percentage, progress.roundToInt()),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .clip(RoundedCornerShape(5.dp)),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.primary.copy(0.1f),
            )


            Row(
                modifier = Modifier.padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (i in 0 until exercise.sets) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(
                                if (i < exercise.completedSet) green.copy(0.8f)
                                else MaterialTheme.colorScheme.tertiary.copy(0.2f)
                            )
                            .border(
                                width = 1.dp,
                                color = if (i < exercise.completedSet) green.copy(0.8f)
                                else MaterialTheme.colorScheme.tertiary.copy(0.2f),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (i < exercise.completedSet) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = stringResource(R.string.completed),
                                tint = lightGreen,
                                modifier = Modifier.size(16.dp)
                            )
                        } else {
                            Text(
                                text = stringResource(R.string.number, i + 1),
                                color = MaterialTheme.colorScheme.onSurface.copy(0.8f),
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = {
                        isUndoClicked = true
                        faceExeViewmodel.onUndoSet(exercise.id)
                    },
                    enabled = exercise.completedSet > 0,
                    modifier = Modifier.height(36.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.RotateLeft,
                            contentDescription = stringResource(R.string.undo),
                            modifier = Modifier.size(14.dp),
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                        Text(
                            text = stringResource(R.string.undo),
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        AnimatedVisibility(
                            visible = updateExerciseState is AppState.Loading && isUndoClicked,
                            enter = expandHorizontally(),
                            exit = shrinkHorizontally()
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }

                Button(
                    onClick = {
                        isUndoClicked = false
                        faceExeViewmodel.onCompleteSet(exercise.id)
                    },
                    enabled = exercise.completedSet < exercise.sets,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (exercise.completedSet >= exercise.sets) green else MaterialTheme.colorScheme.primary,
                        disabledContainerColor = green
                    ),
                    modifier = Modifier.height(36.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp)
                ) {
                    if (exercise.completedSet >= exercise.sets) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = stringResource(R.string.completed),
                            modifier = Modifier.size(14.dp),
                            tint = MaterialTheme.colorScheme.surface
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = stringResource(R.string.completed),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.surface
                        )
                    } else {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = stringResource(
                                    R.string.complete_,
                                    exercise.completedSet + 1
                                ),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.surface
                            )
                            AnimatedVisibility(
                                visible = updateExerciseState is AppState.Loading && !isUndoClicked,
                                enter = expandHorizontally(),
                                exit = shrinkHorizontally()
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(16.dp),
                                    strokeWidth = 3.dp,
                                    color = MaterialTheme.colorScheme.surface
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
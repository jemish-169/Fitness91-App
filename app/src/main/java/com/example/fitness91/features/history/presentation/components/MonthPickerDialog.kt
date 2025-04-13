package com.example.fitness91.features.history.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.fitness91.R
import java.time.LocalDate

@Composable
fun MonthPickerDialog(
    selectedDate: LocalDate,
    onDateSelected: (Int, Int) -> Unit,
    onDismiss: () -> Unit,
    years: List<Int>,
    months: List<String>
) {

    val currentYear = remember { mutableIntStateOf(selectedDate.year) }

    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
                .padding(4.dp)
                .padding(vertical = 4.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.select_month),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(start = 12.dp, top = 4.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(items = years, key = { it }) { year ->
                    Button(
                        onClick = { currentYear.intValue = year },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (year == currentYear.intValue)
                                MaterialTheme.colorScheme.primary.copy(0.7f)
                            else MaterialTheme.colorScheme.onTertiary
                        ),
                        shape = CircleShape,
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = year.toString(),
                            color = if (year == currentYear.intValue) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.onSurface,
                            fontSize = 14.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(columns = GridCells.Fixed(3)) {
                itemsIndexed(months) { index, month ->
                    Button(
                        onClick = { onDateSelected(currentYear.intValue, index + 1) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (index + 1 == selectedDate.monthValue &&
                                currentYear.intValue == selectedDate.year
                            ) MaterialTheme.colorScheme.primary.copy(0.7f)
                            else MaterialTheme.colorScheme.onTertiary
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = month,
                            color = if (index + 1 == selectedDate.monthValue &&
                                currentYear.intValue == selectedDate.year
                            ) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            TextButton(
                onClick = onDismiss,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    stringResource(R.string.close),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
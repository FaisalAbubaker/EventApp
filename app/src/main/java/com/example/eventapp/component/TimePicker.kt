package com.example.eventapp.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.eventapp.R
import com.example.eventapp.ui.theme.Navy
import com.example.eventapp.ui.theme.PrimaryColor
import java.util.Locale


@Composable
fun TimePickerDialog(
    onBackPress: () -> Unit,
    onTimeSelected: (hour: String, minute: String) -> Unit
) {

    Dialog(onDismissRequest = {
        onBackPress.invoke()
    }) {
        val selectedTime = rememberSaveable {
            mutableStateOf<Pair<String, String>?>(null)
        }
        Box(
            Modifier
                .padding(horizontal = 34.dp)
                .background(Color.Transparent)
        ) {
            Column(
                modifier = Modifier
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(id = R.string.edit_time),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(22.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Navy,
                    textAlign = TextAlign.Center
                )

                VerticalTimePicker(
                    initialHour = "10",
                    initialMinute = "30",
                    onTimeSelected = { hour, min ->
                        onTimeSelected.invoke(hour, min)
                        selectedTime.value = Pair(hour, min)
                    }
                )

                Spacer(modifier = Modifier.width(16.dp))
                Divider()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween

                ) {

                    Text(
                        stringResource(id = R.string.reminder_mode),
                        fontSize = 16.sp,
                        color = Navy
                    )
                    Text(
                        stringResource(id = R.string.ring),
                        //   modifier = Modifier.padding(top = 8.dp),
                        fontSize = 14.sp,
                        color = PrimaryColor
                    )
                }
                Divider()

                // buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            onBackPress.invoke()
                        }, shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(text = stringResource(id = R.string.cancel))

                    }

                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            onTimeSelected(
                                selectedTime.value?.first.orEmpty(),
                                selectedTime.value?.second.orEmpty()
                            )
                            onBackPress.invoke()
                        }, shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(text = stringResource(id = R.string.save), color = Color.White)
                    }

                }
            }
        }
    }
}

@Composable
fun VerticalTimePicker(
    initialHour: String = "0",
    initialMinute: String = "0",
    onTimeSelected: (hour: String, minute: String) -> Unit
) {
    var hour by remember { mutableStateOf(initialHour) }
    var minute by remember { mutableStateOf(initialMinute) }
    onTimeSelected.invoke(hour, minute)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Hour picker
        RotaryPicker(
            onValueChange = { hour = it },
            minValue = 0,
            maxValue = 23,
            label = "H"
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Minute picker
        RotaryPicker(
            onValueChange = { minute = it },
            minValue = 0,
            maxValue = 59,
            label = "M"
        )
    }
}

@Composable
fun RotaryPicker(
    onValueChange: (String) -> Unit,
    minValue: Int,
    maxValue: Int,
    label: String
) {
    val lazyColumnState = rememberLazyListState()
    val items = (minValue..maxValue).toList()

    val topBottomFade = Brush.verticalGradient(
        0f to Color.Transparent,
        0.3f to Color.Red,
        0.7f to Color.Red,
        1f to Color.Transparent
    )

    Column(
        modifier = Modifier
            .fadingEdge(topBottomFade)
            .height(120.dp)
    ) {
        LazyColumn(
            state = lazyColumnState,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(count = Int.MAX_VALUE) { index ->
                val middleItem =
                    remember { derivedStateOf { lazyColumnState.firstVisibleItemIndex } }.value + 2

                val isSelected = index == middleItem
                val backgroundColor = if (isSelected) PrimaryColor else PrimaryColor.copy(0.3f)
                val item = items[index % items.size]
                val formattedItem = String.format(Locale.ENGLISH, "%02d", item)

                Text(
                    text = if (isSelected) (formattedItem.plus(
                        "   $label"
                    )) else (formattedItem),
                    color = backgroundColor,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        onValueChange.invoke(formattedItem)
                    },
                    fontSize = if (isSelected) 20.sp else 16.sp
                )
                if (isSelected) {
                    onValueChange.invoke(formattedItem)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

    }
}

fun Modifier.fadingEdge(brush: Brush) = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()
        drawRect(brush = brush, blendMode = BlendMode.DstIn)
    }
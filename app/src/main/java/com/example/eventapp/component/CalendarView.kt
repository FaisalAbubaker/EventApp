package com.example.eventapp.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.eventapp.ui.theme.PrimaryColor
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.OutDateStyle
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale


@Composable
fun MonthlyHorizontalCalendarView(navController: NavHostController, onBackPress: () -> Boolean) {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(100) } // Adjust as needed
    val endMonth = remember { currentMonth.plusMonths(100) } // Adjust as needed
    val firstDayOfWeek = remember { DayOfWeek.MONDAY } // Available from the library
    var selectedDate by remember { mutableStateOf<LocalDate?>(LocalDate.now()) }
    val daysOfWeek = daysOfWeek(firstDayOfWeek = DayOfWeek.MONDAY)

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek,
        outDateStyle = OutDateStyle.EndOfGrid
    )
    Box(
        Modifier
            .padding(16.dp)
            .background(Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(16.dp))
                .padding(vertical = 16.dp, horizontal = 24.dp),
        ) {
            HorizontalCalendar(
                state = state,
                monthHeader = { month ->
                    MonthHeader(daysOfWeek = month.yearMonth, state)
                    DaysOfWeekTitle(daysOfWeek)
                },
                dayContent = { calendarDay ->

                    DayContent(calendarDay, isSelected = selectedDate == calendarDay.date) {
                        selectedDate = if (selectedDate == it) null else it
                    }
                }
            )
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
                    Text(text = "Cancel")

                }

                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        navController.previousBackStackEntry?.savedStateHandle?.set<String>(
                            "selectedDate",
                            selectedDate.toString()
                        )
                        onBackPress.invoke()
                    }, shape = RoundedCornerShape(16.dp)
                ) {
                    Text(text = "Save", color = Color.White)
                }

            }
        }
    }
}


@Composable
fun MonthHeader(daysOfWeek: YearMonth, state: CalendarState) {
    val scope = rememberCoroutineScope()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp, horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val monthName =
            daysOfWeek.month.getDisplayName(
                TextStyle.FULL,
                Locale.ENGLISH
            )
        Text(
            monthName.orEmpty().plus(daysOfWeek.year.toString()),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(Icons.Default.KeyboardArrowLeft, "", Modifier.clickable {
                scope.launch {
                    state.scrollToMonth(daysOfWeek.previousMonth)
                }
            })
            Icon(Icons.Default.KeyboardArrowRight, "", Modifier.clickable {
                scope.launch { state.scrollToMonth(daysOfWeek.nextMonth) }
            })
        }
    }
}


@Composable
fun DayContent(day: CalendarDay, isSelected: Boolean, onDateSelected: (LocalDate) -> Unit) {
    Column(
        modifier = Modifier
            .aspectRatio(0.9f)
            .background(if (isSelected) PrimaryColor else Color.White, RoundedCornerShape(50))
            .clickable {
                onDateSelected.invoke(day.date)
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (day.position == DayPosition.MonthDate) {
            Text(
                text = day.date.dayOfMonth.toString(),
                color = if (isSelected) Color.White else Color.Black,
                fontSize = 12.sp,
            )
        }
    }
}

@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH).dropLast(1),
                color = Color.Black,
                fontSize = 14.sp
            )
        }
    }
}
package com.example.eventapp.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eventapp.ui.theme.Navy
import com.example.eventapp.ui.theme.PrimaryColor
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.core.yearMonth
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CalendarWeeklyView(onDateSelected: (LocalDate) -> Unit) {
    val currentDate = remember { LocalDate.now() }
    val currentMonth = remember { YearMonth.now() }
    val startDate = remember { currentMonth.minusMonths(100).atStartOfMonth() } // Adjust as needed
    val endDate = remember { currentMonth.plusMonths(100).atEndOfMonth() } // Adjust as needed
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() } // Available from the library
    var selectedDate by remember { mutableStateOf<LocalDate?>(LocalDate.now()) }
    val state = rememberWeekCalendarState(
        startDate = startDate,
        endDate = endDate,
        firstVisibleWeekDate = currentDate,
        firstDayOfWeek = firstDayOfWeek
    )

    WeekCalendar(
        modifier = Modifier
            .fillMaxWidth(),
        state = state,
        weekHeader = {
            WeekHeaderView(
                it.days.first().date.yearMonth.month.getDisplayName(
                    TextStyle.FULL,
                    Locale.getDefault()
                ).plus(state.lastVisibleWeek.days.first().date.year)
            )
        },
        dayContent = { day ->
            Day(day.date, isSelected = selectedDate == day.date) {
                selectedDate = if (selectedDate == it) null else it
                onDateSelected.invoke(it)
            }

        }
    )
}


@Composable
fun WeekHeaderView(monthName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            "Task",
            modifier = Modifier,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            color = Navy,
            textAlign = TextAlign.Center
        )

        Row {
            Icon(Icons.Outlined.DateRange, contentDescription = "")
            Text(
                monthName,
                modifier = Modifier,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = Color.LightGray,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun Day(day: LocalDate, isSelected: Boolean, onDateSelected: (LocalDate) -> Unit) {
    Column(
        modifier = Modifier
            .aspectRatio(0.7f)
            .background(if (isSelected) PrimaryColor else Color.White, RoundedCornerShape(16.dp))
            .clickable {
                onDateSelected.invoke(day)
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = day.dayOfWeek.getDisplayName(
                TextStyle.SHORT,
                Locale.getDefault()
            ),
            color = if (isSelected) Color.White else Color.Black,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Text(
            text = day.dayOfMonth.toString(),
            color = if (isSelected) Color.White else Color.Black,
            fontSize = 12.sp,
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
}
package com.example.eventapp.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.eventapp.data.entity.Tags
import com.example.eventapp.navigation.Screens
import com.example.eventapp.ui.theme.PrimaryColor

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddTagsListView(list: List<Tags>, navController: NavController ,onTagClick: (MutableSet<Tags>) -> Unit) {
    val selectedItems = remember { mutableSetOf<Tags>() }

    Column(modifier = Modifier.wrapContentSize()) {
        Text(
            text = "Tags",
            modifier = Modifier
                .padding(5.dp)
                .clickable { },
            color = Color.White
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            list.forEach {
                val isSelected = selectedItems.contains(it)
                Box(
                    Modifier
                        .background(
                            Color(it.color.toIntOrNull()?:PrimaryColor.toArgb()).copy(0.2F),
                            shape = RoundedCornerShape(40)
                        )
                        .padding(vertical = 2.dp, horizontal = 8.dp)
                        .clickable {
                            if (isSelected) {
                                selectedItems.remove(it)
                            } else {
                                selectedItems.add(it)
                            }
                            onTagClick.invoke(selectedItems)
                        }
                ) {
                    Text(
                        text = it.name,
                        modifier = Modifier.padding(8.dp),
                        color = Color(it.color.toIntOrNull()?: PrimaryColor.toArgb())
                    )
                }
            }

        }

        Text(
            text = "+ Add new tag",
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .clickable {
                    navController.navigate(Screens.MainApp.AddTagDialog.route)
                },
            color = PrimaryColor,
            textAlign = TextAlign.Center
        )

    }
}
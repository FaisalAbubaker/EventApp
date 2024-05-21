package com.example.eventapp.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    label: String,
    textColor: Color,
    value: MutableState<String>,
    isReadOnly: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Column(modifier = modifier.padding(vertical = 12.dp)) {
        Text(
            label,
            Modifier.padding(end = 20.dp, start = 20.dp),
            style = TextStyle(color = textColor, fontSize = 16.sp)
        )
        TextField(modifier = Modifier
            .padding(horizontal = 6.dp)
            .fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                disabledContainerColor = containerColor,
                cursorColor = textColor,
                focusedIndicatorColor = textColor,
                focusedLabelColor = textColor,
            ),
            readOnly = isReadOnly,
            trailingIcon = { trailingIcon?.invoke() },
            value = value.value,
            onValueChange = {
                value.value = it
            })

    }
}

//@Preview
//@Composable
//fun CustomTextFieldPreview() {
//    val state = remember {
//        mutableStateOf("")
//    }
//    CustomTextField(modifier = Modifier, "Username", Color.Gray, state)
//
//}
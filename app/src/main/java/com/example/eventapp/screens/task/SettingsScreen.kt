package com.example.eventapp.screens.task

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.os.LocaleListCompat
import androidx.navigation.NavHostController
import com.example.eventapp.R
import com.example.eventapp.component.TasksHeaderView


@Composable
fun SettingsScreen(navController: NavHostController) {

    val context = LocalContext.current

    val onClickRefreshArActivity = {
        context.findActivity()?.runOnUiThread {
            val arAppLocale =
                LocaleListCompat.forLanguageTags("ar")
            AppCompatDelegate.setApplicationLocales(arAppLocale)
        }
    }

    val onClickRefreshEngActivity = {
        context.findActivity()?.runOnUiThread {
            val enAppLocale =
                LocaleListCompat.forLanguageTags("en")
            AppCompatDelegate.setApplicationLocales(enAppLocale)
        }
    }

    var lng = stringResource(id = R.string.language)
    var lang by rememberSaveable {
        mutableStateOf(lng)
    }
    val showLangDialog = remember {
        mutableStateOf(false)
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
    ) {
            TasksHeaderView(title = stringResource(id = R.string.settings)) {
                navController.popBackStack()
            }
            var general = stringResource(id = R.string.general)
            Text(
                text = general, fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.padding(15.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(text = stringResource(id = R.string.language), fontSize = 16.sp)
                Spacer(modifier = Modifier.weight(1f))

                Row(Modifier.clickable {
                    showLangDialog.value = true
                }) {
                    Text(text = lang, fontSize = 16.sp)
                    Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "")
                }
                if (showLangDialog.value) {
                    LanguagePickerDialog(defaultLang = lang,
                        onBackPress = { showLangDialog.value = false },
                        onLangSelected = {
                            lang = it
                            if (lang == "English") {
                                onClickRefreshEngActivity()
                            } else if (lang == "عربي") {
                                onClickRefreshArActivity()
                            }
                        })
                }
            }
            Text(
                text = stringResource(id = R.string.delete_account), fontSize = 16.sp,
                modifier = Modifier.padding(10.dp)
            )

            Spacer(modifier = Modifier.padding(20.dp))
            var checked1 by rememberSaveable { mutableStateOf(false) }
            var checked2 by rememberSaveable { mutableStateOf(false) }

            Text(
                text = stringResource(id = R.string.notifications), fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.padding(15.dp))
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(id = R.string.allow_notifications), fontSize = 16.sp,
                    modifier = Modifier.padding(10.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = checked1,
                    onCheckedChange = {
                        checked1 = it
                    }
                )
            }
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(id = R.string.allow_notif_rings), fontSize = 16.sp,
                    modifier = Modifier.padding(10.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = checked2,
                    onCheckedChange = {
                        checked2 = it
                    }
                )
            }


    }
}
    @Composable
    fun LanguagePickerDialog(
        defaultLang: String,
        onBackPress: () -> Unit,
        onLangSelected: (language: String) -> Unit
    ) {
        var selectedOption by rememberSaveable {
            mutableStateOf(defaultLang)
        }
        Dialog(onDismissRequest = {
            onBackPress.invoke()
            onLangSelected.invoke(selectedOption)
        }) {
            Column(
                Modifier
                    .background(Color.White)
                    .padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.language),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 20.dp)
                )
                Row(Modifier.fillMaxWidth()) {
                    Text(text = "English")
                    Spacer(modifier = Modifier.weight(1f))
                    RadioButton(
                        selected = selectedOption == "English",
                        onClick = { selectedOption = "English" })
                }
                Row(Modifier.fillMaxWidth()) {
                    Text(text = "عربي")
                    Spacer(modifier = Modifier.weight(1f))
                    RadioButton(
                        selected = selectedOption == "عربي",
                        onClick = { selectedOption = "عربي" })
                }
            }

        }
    }
fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}


package com.avinash.mynotes.ui.screens.add_or_edit

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.avinash.mynotes.R
import com.avinash.mynotes.room.Note
import com.avinash.mynotes.ui.theme.*
import java.util.*


@ExperimentalUnitApi
@Composable
fun AddOrEditNotesScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: AddOrEditViewModel = hiltViewModel()
) {
    val colorSelected by viewModel.color.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {

        Column(
            modifier = modifier
                .weight(1f, true)
        ) {

            val title by viewModel.title.collectAsState()
            val content by viewModel.content.collectAsState()

            BackAndSaveButtons(
                onGoBack = {
                    navController.popBackStack()
                },
                onSave = {
                    val calender = Calendar.getInstance()
                    val createDate = " ${calender.time.date}  ${
                        calender.getDisplayName(
                            Calendar.MONTH,
                            Calendar.LONG,
                            Locale.ENGLISH
                        )
                    } ${calender.time.year - 100}"
                    viewModel.saveNote(
                        note = Note(
                            id = 0,
                            title = title,
                            content = content,
                            createDate = createDate,
                            color = colorSelected
                        )
                    )
                    navController.popBackStack()
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            TitleAndText(
                title = title,
                titleChange = viewModel::onTitleChange,
                content = content,
                contentChange = viewModel::onContentChange
            )

        }

        ColorSection(
            colorSelected = colorSelected,
            onColorChange = viewModel::onColorChange,
            colors = colorPickerList
        )
    }
}


@Composable
fun BackAndSaveButtons(onGoBack: () -> Unit, onSave: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    )
    {

        Icon(
            painter = painterResource(id = R.drawable.back_icon),
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(25))
                .clickable {
                    onGoBack()
                }
                .height(50.dp)
                .background(MaterialTheme.colors.ButtonBackground)
                .padding(16.dp)
        )

        Text(
            text = "Save",
            style = MaterialTheme.typography.body1.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold
            ),
            modifier = Modifier
                .clip(RoundedCornerShape(25))
                .clickable {
                    onSave()
                }
                .height(50.dp)
                .background(MaterialTheme.colors.ButtonBackground)
                .padding(16.dp)
        )
    }
}


@ExperimentalUnitApi
@Composable
fun TitleAndText(
    title: String,
    titleChange: (String) -> Unit,
    content: String,
    contentChange: (String) -> Unit
) {

    TextFieldWithHint(
        value = title,
        hint = "Title",
        singleLine = true,
        onValueChange = titleChange,
        fontSize = 40.sp,
        fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(32.dp))

    TextFieldWithHint(
        value = content,
        hint = "Start typing.....",
        onValueChange = contentChange,
        fontSize = 24.sp,
        fontWeight = FontWeight.Black
    )

}


@ExperimentalUnitApi
@Composable
fun TextFieldWithHint(
    modifier: Modifier = Modifier,
    value: String,
    hint: String = "",
    singleLine: Boolean = false,
    onValueChange: (String) -> Unit,
    fontSize: TextUnit = 30.sp,
    fontWeight: FontWeight? = null
) {
    Box(modifier = modifier.fillMaxWidth()) {

        val style = MaterialTheme.typography.h3
            .copy(
                color = MaterialTheme.colors.NotesTextColor,
                fontSize = fontSize, //this should be 40
                fontWeight = fontWeight,
                letterSpacing = TextUnit(1.5f, TextUnitType.Sp)
            )

        if (value.isEmpty())
            Text(
                text = hint, style = style
            )

        BasicTextField(
            value = value, textStyle = style,
            onValueChange = onValueChange,
            singleLine = singleLine
        )
    }
}


@Composable
fun ColorSection(colorSelected: Int, onColorChange: (Int) -> Unit, colors: List<Color>) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {

        colors.forEachIndexed { i: Int, color: Color ->
            ColorPicker(color = color, i == colorSelected, onClick = {
                onColorChange(i)
            })
        }
    }
}


@Preview
@Composable
fun ColorPicker(
    color: Color = Color.White,
    isSelected: Boolean = true,
    onClick: () -> Unit = {}
) {
    Spacer(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .clickable {
                onClick()
            }
            .background(if (isSelected) Color.White else Color.Transparent)
            .padding(4.dp)
            .clip(CircleShape)
            .background(color = color)
            .animateContentSize()


    )
}
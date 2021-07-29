package com.avinash.mynotes.ui.screens.add_or_edit_notes

import android.app.Service
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
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
import com.avinash.mynotes.room.Content
import com.avinash.mynotes.room.Note
import com.avinash.mynotes.ui.UIUtil
import com.avinash.mynotes.ui.theme.ButtonBackground
import com.avinash.mynotes.ui.theme.NotesTextColor
import com.avinash.mynotes.ui.theme.colorPickerList
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
            val contents = viewModel.contents

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
                                        createDate = createDate,
                                        color = colorSelected
                                )
                        )
                        navController.popBackStack()
                    }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Title(
                    value = title,
                    titleChange = viewModel::onTitleChange
            )

            Spacer(modifier = Modifier.height(24.dp))

            ContentSection(
                    modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(colorPickerList[colorSelected])
                            .padding(16.dp),
                    contents = contents,
                    onContentChange = viewModel::onContentChange,
                    onAddItem = {
                        viewModel.addContent(it)
                    }
            )

        }

        ColorSection(
                colorSelected = colorSelected,
                onColorChange = viewModel::onColorChange,
                colors = colorPickerList
        )
    }
}


@ExperimentalUnitApi
@Composable
fun ContentSection(
        modifier: Modifier,
        contents: List<Content>,
        onAddItem: (Content) -> Unit,
        onContentChange: (String, Int) -> Unit
) {
    Column(
            modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
                modifier = Modifier.weight(1f, true)
        ) {

            itemsIndexed(contents) { index: Int, content: Content ->
                Log.d("AVINASH", "AddOrEditNotesScreen: IN THE CONTENT")
                ContentData(modifier = modifier, content = content, onValueChange = { data ->
                    Log.d("AVINASH", "AddOrEditNotesScreen: $index $data")
                    onContentChange(data, index)
                })
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
        ) {
            TextButton(text = "Add Text" , onClick = {
                onAddItem(Content(0, UIUtil.CONTENT_TYPE_TEXT, "NEW To do", 0))
            })
            TextButton(text = "Add ToDo" , onClick = {
                onAddItem(Content(0, UIUtil.CONTENT_TYPE_TODO, "NEW To do${UIUtil.TO_DO_SPLITER}false", 0))
            })
        }
    }
}

@ExperimentalUnitApi
@Composable
fun ContentData(modifier: Modifier, content: Content, onValueChange: (String) -> Unit) {

    var data: String by remember(key1 = content.id) {
        mutableStateOf(content.data)
    }

    when (content.contentType) {
        UIUtil.CONTENT_TYPE_TODO -> {
            ContentToDo(data = data, modifier = modifier, onValueChange = {
                data = it
                onValueChange(it)
            })
        }
        UIUtil.CONTENT_TYPE_TEXT -> {
            ContentText(data = data, modifier = modifier, onValueChange = {
                data = it
                onValueChange(it)
            })
        }
    }
}

@ExperimentalUnitApi
@Composable
fun ContentToDo(data: String, modifier: Modifier, onValueChange: (String) -> Unit) {
    Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
    ) {

        val value = data.split(UIUtil.TO_DO_SPLITER)
        val style = MaterialTheme.typography.h3
                .copy(
                        color = MaterialTheme.colors.NotesTextColor,
                        fontSize = 24.sp, //this should be 40
                        fontWeight = FontWeight.Bold,
                        letterSpacing = TextUnit(1.5f, TextUnitType.Sp)
                )

        BasicTextField(
                value = value[0], textStyle = style,
                onValueChange = {
                    onValueChange("$it${UIUtil.TO_DO_SPLITER}${value[1]}")
                },
                singleLine = true
        )
        Icon(painter = painterResource(id = if (value[1] == "true") R.drawable.checked else R.drawable.unchecked),
                contentDescription = null,
                modifier = Modifier.clickable {
                    onValueChange("${value[0]}${UIUtil.TO_DO_SPLITER}${if (value[1] == "true") "false" else "true"}")
                })
    }
}


@ExperimentalUnitApi
@Composable
fun ContentText(modifier: Modifier, data: String, onValueChange: (String) -> Unit) {
    TextFieldWithHint(
            modifier = modifier,
            value = data,
            hint = "Start Typing",
            singleLine = false,
            onValueChange = onValueChange,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
    )

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

        TextButton(text = "Save", onClick = onSave)
    }
}


@Composable
fun TextButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Text(
            text = text,
            style = MaterialTheme.typography.body1.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold
            ),
            modifier = modifier
                    .clip(RoundedCornerShape(25))
                    .clickable {
                        onClick()
                    }
                    .height(50.dp)
                    .background(MaterialTheme.colors.ButtonBackground)
                    .padding(16.dp)
    )
}

@ExperimentalUnitApi
@Composable
fun Title(
        value: String,
        titleChange: (String) -> Unit
) {

    TextFieldWithHint(
            value = value,
            hint = "Title",
            singleLine = true,
            onValueChange = titleChange,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
    )
}


@ExperimentalUnitApi
@Composable
fun TextFieldWithHint(
        modifier: Modifier,
        value: String,
        hint: String = "",
        singleLine: Boolean = false,
        onValueChange: (String) -> Unit,
        fontSize: TextUnit = 30.sp,
        fontWeight: FontWeight? = null
) {
    Box(modifier = modifier) {

        val style = MaterialTheme.typography.h3
                .copy(
                        color = MaterialTheme.colors.NotesTextColor,
                        fontSize = fontSize, //this should be 40
                        fontWeight = fontWeight,
                        letterSpacing = TextUnit(1.5f, TextUnitType.Sp)
                )

        if (value.isEmpty())
            Text(text = hint, style = style)

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
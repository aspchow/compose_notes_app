package com.avinash.mynotes.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)



val Yellow = Color(0xffffcc80)
val Green = Color(0xffe5ee9b)
val SkyBlue = Color(0xff80dfea)
val Pink = Color(0xffcf94d9)
val Red = Color(0xfff38fb0)

val colorPickerList = listOf(Yellow, Pink, Green, SkyBlue, Red)




val Colors.FabBackGround
@Composable
get() = Color(0xFF141313)


val Colors.CardBackGroundYellow
    @Composable
    get() = Color(0xFFFFE082)

val Colors.NotesTextColor
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0x88929292) else Color.White

val Colors.NotesTextBlackColor
    @Composable
    get() = Color(0x960E0D0D)

val Colors.NotesTextGreyColor
    @Composable
    get() = Color(0xA15E5B5B)

val Colors.ButtonBackground
    @Composable
    get() = Color(0x753B3B3B)
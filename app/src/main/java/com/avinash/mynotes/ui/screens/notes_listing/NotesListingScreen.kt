package com.avinash.mynotes.ui.screens.notes_listing

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.avinash.mynotes.R
import com.avinash.mynotes.room.Note
import com.avinash.mynotes.ui.NavRouting
import com.avinash.mynotes.ui.setValue
import com.avinash.mynotes.ui.theme.*

@ExperimentalAnimationApi
@Composable
fun NotesListingScreen(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: NotesListingViewModel = hiltViewModel()
) {
    val scrollState = rememberLazyListState()
    Scaffold(
        content = {

            Column(modifier = modifier) {
                val notes by viewModel.notes.collectAsState(initial = emptyList())

                val searchText by viewModel.searchText.collectAsState()

                NotesHeaderAndSearch(
                    searchText = searchText,
                    onSearchChange = viewModel::changeSearchText
                )

                Spacer(modifier = Modifier.height(16.dp))


                LazyColumn(modifier = Modifier.fillMaxSize(), scrollState) {
                    items(notes) { note ->
                        Note(note = note, onClick = {
                            navController.navigate("notes_detail/$it")
                        })
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        },
        floatingActionButton = {
            val fabSize by animateDpAsState(targetValue = if (scrollState.isScrollInProgress) 0.dp else 64.dp)

            FloatingActionButton(
                modifier = Modifier
                    .padding(8.dp)
                    .size(fabSize),
                onClick = {
                    navController.navigate(NavRouting.EDIT_NOTES.route)
                },
                backgroundColor = MaterialTheme.colors.FabBackGround,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add_icon),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    )
}

@ExperimentalAnimationApi
@Composable
fun NotesHeaderAndSearch(searchText: String, onSearchChange: (String) -> Unit) {

    var showSearchText by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(
                text = "Notes",
                modifier = Modifier.weight(0.3f),
                style = MaterialTheme.typography.h1.copy(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.5.sp
                ),
            )

            Row(
                modifier = Modifier
                    .weight(0.7f, showSearchText)
                    .clip(RoundedCornerShape(25))
                    .height(60.dp)
                    .background(MaterialTheme.colors.ButtonBackground)
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                AnimatedVisibility(visible = showSearchText) {
                    BasicTextField(
                        value = searchText,
                        textStyle = MaterialTheme.typography.h6.copy(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.5.sp,
                            color = MaterialTheme.colors.NotesTextColor
                        ),
                        onValueChange = onSearchChange
                    )
                }

                Icon(
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(RoundedCornerShape(25))
                        .clickable {
                            showSearchText = !showSearchText
                        }
                        .height(60.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun Note(
    note: Note = Note(1, "Avinash Munnangi", "20-Feb", 0),
    onClick: (Int) -> Unit = {}
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15))
            .clickable {
                onClick(note.id)
            },
        elevation = 30.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorPickerList[note.color])
                .padding(16.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = note.title,
                color = MaterialTheme.colors.NotesTextBlackColor,
                style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.ExtraBold)
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = note.createDate,
                color = MaterialTheme.colors.NotesTextGreyColor,
                style = MaterialTheme.typography.body1.copy(
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

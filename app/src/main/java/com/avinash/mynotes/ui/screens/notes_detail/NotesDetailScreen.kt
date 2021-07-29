package com.avinash.mynotes.ui.screens.notes_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun NotesDetailScreen(
    modifier: Modifier,
    navController: NavController,
    viewModel: NotesDetailViewModel = hiltViewModel()
) {
    Column(
        modifier = modifier
    ) {

    }
}
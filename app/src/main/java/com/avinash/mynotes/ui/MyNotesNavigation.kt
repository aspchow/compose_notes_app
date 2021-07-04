package com.avinash.mynotes.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.avinash.mynotes.ui.screens.add_or_edit.AddOrEditNotesScreen
import com.avinash.mynotes.ui.screens.notes_listing.NotesListingScreen

@ExperimentalAnimationApi
@ExperimentalUnitApi
@Composable
fun MyNotesNavigation() {
    val navController = rememberNavController()

    val containerModifier = Modifier
        .padding(16.dp)

    NavHost(navController = navController, startDestination = NavRouting.NOTES_LISTING) {
        composable(NavRouting.NOTES_LISTING) {

            NotesListingScreen(containerModifier, navController)
        }

        composable(NavRouting.EDIT_NOTES) {
            AddOrEditNotesScreen(containerModifier, navController)
        }
    }
}


object NavRouting {
    const val NOTES_LISTING = "notes_listing"
    const val EDIT_NOTES = "edit_notes"
}



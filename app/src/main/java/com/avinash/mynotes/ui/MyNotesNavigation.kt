package com.avinash.mynotes.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.avinash.mynotes.ui.screens.add_or_edit_notes.AddOrEditNotesScreen
import com.avinash.mynotes.ui.screens.notes_detail.NotesDetailScreen
import com.avinash.mynotes.ui.screens.notes_listing.NotesListingScreen

@ExperimentalAnimationApi
@ExperimentalUnitApi
@Composable
fun MyNotesNavigation() {
    val navController = rememberNavController()

    val containerModifier = Modifier
        .padding(16.dp)

    NavHost(navController = navController, startDestination = NavRouting.NOTES_LISTING.route) {
        composable(NavRouting.NOTES_LISTING.route) {
            NotesListingScreen(containerModifier, navController)
        }

        composable(NavRouting.EDIT_NOTES.route) {
            AddOrEditNotesScreen(containerModifier, navController)
        }

        composable(
            NavRouting.NOTES_DETAIL.route,
            arguments = NavRouting.NOTES_DETAIL.params
        ) {
            val notesId = it.arguments?.getString("notesId")
            Log.d(
                "AVINASH",
                "MyNotesNavigation: notes id is $notesId ${NavRouting.NOTES_DETAIL.route}\", Toast.LENGTH_SHORT"
            )
            Toast.makeText(LocalContext.current, "notes id is $notesId ${NavRouting.NOTES_DETAIL.route}", Toast.LENGTH_SHORT).show()
            NotesDetailScreen(containerModifier, navController)
        }
    }
}


sealed class NavRouting(
    _route: String,
    val params: List<NamedNavArgument> = emptyList()
) {

    val route = _route + params.joinToString(separator = "") { "/{${it.component1()}}" }
    object NOTES_LISTING : NavRouting("notes_listing")
    object EDIT_NOTES : NavRouting("edit_notes")
    object NOTES_DETAIL : NavRouting(
        "notes_detail",
        params = listOf(navArgument(name = "notesId") { NavType.IntType })
    )

}


fun String.setValue(name: String, value: String) = replace("{$name}", value)

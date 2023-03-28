package com.ktnotes.android.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ktnotes.android.feature.auth.RegistrationScreen
import com.ktnotes.android.feature.note.NoteListScreen
import com.ktnotes.android.feature.note.details.NoteDetailScreen

const val NAV_HOST_ROUTE = "main-route"

@Composable
fun KtNotesNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Screen.Notes.route, route = NAV_HOST_ROUTE) {
        composable(route = Screen.Notes.route) {
            val onLoggedOut = {
                navController.navigateToRegisterScreenPopAll()
            }

            val onNoteClick: (String) -> Unit = { noteId ->
                navController.navigate(Screen.NoteDetails.route(noteId))
            }

            val onAddNoteClick = {
                navController.navigate(Screen.AddNote.route)
            }
            NoteListScreen(
                navController = navController,
                onLoggedOut = onLoggedOut,
                onNoteClick = onNoteClick,
                onAddNoteClick = onAddNoteClick
            )
        }

        composable(
            route = Screen.NoteDetails.route,
            arguments = listOf(
                navArgument(name = Screen.NoteDetails.ARG_NOTE_ID) {
                    type = NavType.StringType
                    defaultValue = "new"
                }

            )) {
            val noteId = it.arguments?.getString(Screen.NoteDetails.ARG_NOTE_ID) ?: "new"

            NoteDetailScreen(
                noteId = noteId,
                navController = navController,
            )
        }

        composable(route = Screen.Register.route) {
            val onLoggedIn = {
                navController.navigateToNotesScreenPopAll()
            }
            RegistrationScreen(onLoggedIn = onLoggedIn)
        }
    }
}

private fun NavHostController.navigateToNotesScreenPopAll() {
    navigate(Screen.Notes.route) {
        popUpTo(Screen.Register.route) {
            inclusive = true
        }
    }
}

private fun NavHostController.navigateToRegisterScreenPopAll() {
    navigate(Screen.Register.route) {
        popUpTo(Screen.Register.route) {
            inclusive = true
        }
    }
}


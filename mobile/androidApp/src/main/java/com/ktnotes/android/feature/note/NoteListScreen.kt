package com.ktnotes.android.feature.note

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ktnotes.feature.note.model.Note


@Composable
fun NoteListScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel(),
    onLoggedOut: () -> Unit,
    onNoteClick: (String) -> Unit,
    onAddNoteClick: () -> Unit,
) {
    val state by viewModel.notesState.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAddNoteClick()
                },
                backgroundColor = Color.Black
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add note",
                    tint = Color.White
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            IconButton(
                onClick = viewModel::logout, modifier = Modifier
                    .padding(16.dp)
                    .wrapContentHeight()
                    .align(Alignment.End)
            ) {
                Icon(Icons.Filled.ExitToApp, contentDescription = null)
            }
            if (state.isLoading) {
                CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
            }
            LazyColumn {
                items(items = state.notes) { note ->
                    NoteItem(
                        note = note,
                        onNoteClick = {
                            onNoteClick(note.id)
                        },
                        onDeleteClick = {
                            viewModel.deleteNote(note.id)
                        }
                    )
                }
            }
        }
    }

    LaunchedEffect(state.isUserLoggedIn) {
        if (state.isUserLoggedIn == false) {
            onLoggedOut()
        }
    }
}

@Composable
fun NoteItem(
    note: Note,
    onNoteClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Card(modifier = Modifier
        .padding(8.dp)
        .clickable { onNoteClick() }
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = note.title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Delete note",
                    modifier = Modifier
                        .clickable(MutableInteractionSource(), null) {
                            onDeleteClick()
                        }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = note.note, fontWeight = FontWeight.Light,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = note.formattedCreatedDate,
                color = Color.DarkGray,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}
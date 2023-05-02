package com.ktnotes.android.feature.note.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun NoteDetailScreen(
    noteId: String,
    viewModel: NoteDetailViewModel = hiltViewModel(),
    onFinishSaving:()->Unit
) {
    val state by viewModel.noteState.collectAsState()
    val fieldsState by viewModel.fieldState.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.saveNote()
                },
                backgroundColor = Color.Black
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save note",
                    tint = Color.White
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
            }
            OutlinedTextField(
                value = fieldsState.title,
                onValueChange = viewModel::onNoteTitleChanged,
                singleLine = true,
                textStyle = TextStyle(fontSize = 20.sp),
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Title") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = fieldsState.content,
                onValueChange = viewModel::onNoteContentChanged,
                singleLine = false,
                textStyle = TextStyle(fontSize = 20.sp),
                modifier = Modifier.fillMaxSize(),
                label = { Text("Content") }
            )
        }
    }

    LaunchedEffect(key1 = noteId) {
        viewModel.getNoteDetails(noteId)
    }

    LaunchedEffect(key1 = state.finishSaving){
        if (state.finishSaving) {
            onFinishSaving()
        }
    }
}
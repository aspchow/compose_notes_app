package com.avinash.mynotes.ui.screens.add_or_edit

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avinash.mynotes.room.Note
import com.avinash.mynotes.room.NotesDao
import com.avinash.mynotes.ui.theme.Yellow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddOrEditViewModel @Inject constructor(
    private val notesDao: NotesDao
) : ViewModel() {
    val title = MutableStateFlow("")

    val content = MutableStateFlow("")

    val color = MutableStateFlow(0)

    fun onTitleChange(title: String) {
        this.title.value = title
    }

    fun onContentChange(content: String) {
        this.content.value = content
    }

    fun onColorChange(color: Int) {
        this.color.value = color
    }

    fun saveNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            notesDao.saveOrEditNote(note)
        }
    }


}
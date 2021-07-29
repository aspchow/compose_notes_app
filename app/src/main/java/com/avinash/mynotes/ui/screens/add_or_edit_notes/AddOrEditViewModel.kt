package com.avinash.mynotes.ui.screens.add_or_edit_notes

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avinash.mynotes.room.Content
import com.avinash.mynotes.room.Note
import com.avinash.mynotes.room.NotesDao
import com.avinash.mynotes.ui.UIUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddOrEditViewModel @Inject constructor(
    private val notesDao: NotesDao
) : ViewModel() {
    val title = MutableStateFlow("")

    val contents = mutableStateListOf(Content(1, UIUtil.CONTENT_TYPE_TODO, "MY TEST ${UIUtil.TO_DO_SPLITER}true", 1))

    val color = MutableStateFlow(0)

    fun onTitleChange(title: String) {
        this.title.value = title
    }

    fun addContent(content: Content) {
        contents.add(content)
    }


    fun onContentChange(data: String, index: Int) {
        contents[index].data = data
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
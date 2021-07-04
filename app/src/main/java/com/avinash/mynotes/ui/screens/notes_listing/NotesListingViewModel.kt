package com.avinash.mynotes.ui.screens.notes_listing

import androidx.lifecycle.ViewModel
import com.avinash.mynotes.room.Note
import com.avinash.mynotes.room.NotesDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class NotesListingViewModel @Inject constructor(
    notesDao: NotesDao
) : ViewModel() {

    val searchText = MutableStateFlow("")

    @ExperimentalCoroutinesApi
    val notes: Flow<List<Note>> = notesDao.getGetNotes().flatMapLatest { notes ->
        searchText.map { searchQuery ->
            notes.filter { note ->
                note.title.contains(searchQuery)
            }
        }
    }


    fun changeSearchText(searchText: String) {
        this.searchText.value = searchText
    }


}
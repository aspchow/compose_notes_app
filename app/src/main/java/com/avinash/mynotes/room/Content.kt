package com.avinash.mynotes.room

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Note::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("noteId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Content(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val contentType: String,
    var data: String,
    val noteId: Int
)

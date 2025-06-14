package edu.jgsilveira.tasks.kmp.roomdb.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import edu.jgsilveira.tasks.kmp.roomdb.entity.NoteEntity

@Dao
abstract class NoteDao {

    @Query("SELECT * FROM notes ORDER BY is_done ASC, updated_at_millis DESC")
    abstract suspend fun getAllNotes(): List<NoteEntity>

    @Query("SELECT * FROM notes WHERE id = :id")
    abstract suspend fun getNoteById(id: Long): NoteEntity?

    @Upsert
    abstract suspend fun upsertNote(note: NoteEntity): Long

    @Query("DELETE FROM notes WHERE 1 = 1")
    abstract suspend fun clearChecklists()

    @Query("DELETE FROM notes WHERE id in (:id)")
    abstract suspend fun deleteNoteById(vararg id: Long)
}
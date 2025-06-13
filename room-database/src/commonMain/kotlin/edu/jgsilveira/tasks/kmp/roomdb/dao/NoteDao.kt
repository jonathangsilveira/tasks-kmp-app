package edu.jgsilveira.tasks.kmp.roomdb.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import edu.jgsilveira.tasks.kmp.roomdb.entity.NoteEntity

@Dao
abstract class NoteDao {

    @Query("SELECT * FROM note ORDER BY is_done ASC, updated_at_millis DESC")
    abstract suspend fun getAllNotes(): List<NoteEntity>

    @Upsert
    abstract suspend fun upsertNote(note: NoteEntity): Long

    @Query("DELETE FROM note WHERE 1 = 1")
    abstract suspend fun clearChecklists()

    @Query("DELETE FROM note WHERE id in (:id)")
    abstract suspend fun deleteNoteById(vararg id: Long)
}
package edu.jgsilveira.tasks.kmp.domain.factory

import edu.jgsilveira.tasks.kmp.domain.model.Note
import edu.jgsilveira.tasks.kmp.roomdb.entity.NoteEntity

internal object NoteFactory {

    fun fromEntity(entity: NoteEntity): Note {
        return with(entity) {
            Note(
                id = id,
                title = title,
                description = description,
                isDone = isDone,
                createdAt = createdAtMillis,
                updatedAt = updatedAtMillis
            )
        }
    }

    fun toEntity(note: Note): NoteEntity {
        return with(note) {
            NoteEntity(
                id = id,
                title = title,
                description = description,
                isDone = isDone,
                createdAtMillis = createdAt,
                updatedAtMillis = updatedAt
            )
        }
    }
}
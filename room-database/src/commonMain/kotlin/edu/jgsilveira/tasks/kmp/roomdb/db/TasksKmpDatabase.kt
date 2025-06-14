package edu.jgsilveira.tasks.kmp.roomdb.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import edu.jgsilveira.tasks.kmp.roomdb.converters.BooleanRoomTypeConverter
import edu.jgsilveira.tasks.kmp.roomdb.dao.NoteDao
import edu.jgsilveira.tasks.kmp.roomdb.entity.NoteEntity

const val DATABASE_NAME = "tasks_kmp_room.db"

@Database(
    version = 1,
    entities = [
        NoteEntity::class
    ]
)
@TypeConverters(BooleanRoomTypeConverter::class)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class TasksKmpDatabase : RoomDatabase() {
    abstract fun provideNoteDao(): NoteDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<TasksKmpDatabase> {
    override fun initialize(): TasksKmpDatabase
}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<TasksKmpDatabase>
): TasksKmpDatabase {
    return builder
        .fallbackToDestructiveMigration(
            dropAllTables = true
        )
        .setDriver(BundledSQLiteDriver())
        .addTypeConverter(BooleanRoomTypeConverter())
        .build()
}
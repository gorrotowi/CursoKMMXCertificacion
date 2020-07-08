package com.gorrotowi.localstorage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(
    entities = [Book::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bookDAO(): DaoBooks

    companion object {

        private val MIGRATION1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE book_table ADD COLUMN ISBN TEXT")
            }
        }

        fun instance(ctx: Context): AppDatabase {
            synchronized(this) {
                val db by lazy {
                    Room.databaseBuilder(ctx, AppDatabase::class.java, "MYAPPDB")
                        .addMigrations(MIGRATION1_2)
                        .build()
                }
                return db
            }
        }

    }

}
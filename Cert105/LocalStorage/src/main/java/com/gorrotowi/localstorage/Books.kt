package com.gorrotowi.localstorage

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_table")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    @ColumnInfo(name = "author_book")
    val author: String,
    val ISNB: String,
    @Embedded
    val editorial: Editorial
)

@Entity
data class Editorial(
    val name: String
)

package com.gorrotowi.localstorage

import androidx.room.*

@Dao
interface DaoBooks {

    @Query("SELECT * FROM book_table")
    fun getBooks(): List<Book>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBook(book: Book)

    @Delete
    fun deleteAll()

    @Delete
    fun deleteBook(book: Book)
}
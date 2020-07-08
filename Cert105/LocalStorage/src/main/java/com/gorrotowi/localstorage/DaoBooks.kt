package com.gorrotowi.localstorage

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoBooks {

    //Para utilizar un Flow no se debe marcar la función como suspend
    @Query("SELECT * FROM book_table")
    fun getBooksByFlow(): Flow<List<Book>>

    @Query("SELECT * FROM book_table")
    suspend fun getBooksSuspend(): List<Book>

    @Query("SELECT * FROM book_table WHERE title = :bookTitle and author_book = :bookAuthor LIMIT 1")
    suspend fun getBookByTitleAndAuthor(bookTitle: String, bookAuthor: String): Book?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book)

    @Query("DELETE FROM book_table")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteBook(book: Book)

    @Transaction
    suspend fun findAndDeleteBook(title: String, author: String): Boolean {
        val book = getBookByTitleAndAuthor(title, author)
        return if (book != null) {
            deleteBook(book)
            true
        } else {
            false
        }
    }

}
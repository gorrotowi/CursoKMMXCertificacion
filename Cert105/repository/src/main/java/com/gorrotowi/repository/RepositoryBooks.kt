package com.gorrotowi.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.gorrotowi.localstorage.AppDatabase
import com.gorrotowi.localstorage.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class RepositoryBooks(val application: Application) {

    private val booksDao = AppDatabase.instance(application).bookDAO()

    fun getBooksFlow(): Flow<ResultRepo.Success<List<BookView>>> = booksDao.getBooksByFlow()
        .map { listBookDB ->
            val data = listBookDB.map { BookView(it.title, it.author, it.editorial) }
            ResultRepo.Success(data)
        }

    suspend fun getBooks(): ResultRepo<List<BookView>> = withContext(Dispatchers.Default) {
        try {
            val listBooks = booksDao.getBooksSuspend()
            val listBookViews = listBooks.map { book ->
                BookView(book.title, book.author, book.editorial)
            }
            ResultRepo.Success(listBookViews)

        } catch (e: Exception) {
            ResultRepo.Error(e)
        }
    }

    suspend fun addBook(title: String, author: String, editorial: String) =
        withContext(Dispatchers.Default) {
            return@withContext try {
                val book = Book(
                    author = author,
                    title = title,
                    ISNB = "adfa",
                    editorial = editorial
                )
                booksDao.insertBook(book)
                ResultRepo.Success(true)
            } catch (e: Exception) {
                ResultRepo.Error(e)
            }
        }

    suspend fun deleteBook(title: String, author: String): Boolean =
        booksDao.findAndDeleteBook(title, author)

}

sealed class ResultRepo<out T> {
    data class Success<out T>(val data: T) : ResultRepo<T>()
    data class Error(val error: Throwable) : ResultRepo<Nothing>()
}

data class BookView(val title: String, val author: String, val editorial: String)
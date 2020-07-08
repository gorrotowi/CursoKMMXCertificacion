package com.gorrotowi.cert105

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gorrotowi.repository.BookView
import com.gorrotowi.repository.RepositoryBooks
import com.gorrotowi.repository.ResultRepo
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = RepositoryBooks(application)

    private val mutableName: MutableLiveData<String> = MutableLiveData()
    val name: LiveData<String> = mutableName

    private val mutableBooksList = MutableLiveData<List<BookView>?>()
    val booksList: LiveData<List<BookView>?> = mutableBooksList

    fun getBooksByFlow() {
        viewModelScope.launch {
            repo.getBooksFlow()
                .catch { error -> error.printStackTrace() }
                .collect { result ->
                    mutableBooksList.value = result.data
                }
        }
    }

    fun getBooks() {
        viewModelScope.launch {
            when (val result = repo.getBooks()) {
                is ResultRepo.Success -> {
                    mutableBooksList.value = result.data
                }
                is ResultRepo.Error -> {
                    Log.e("GetBooks", "Error -> ${result.error.printStackTrace()}")
                }
            }
        }
    }

    fun addBook(title: String) {
        viewModelScope.launch {
            when (val result = repo.addBook(title, "Sebastian", "KMMX")) {
                is ResultRepo.Success -> {
                    Log.e("BookAdded", "DONE")
                }
                is ResultRepo.Error -> {
                    Log.e("BookAdded", "Error -> ${result.error.printStackTrace()}")
                }
            }
        }
    }

    fun setName(newName: String) {
        mutableName.value = newName
    }
}
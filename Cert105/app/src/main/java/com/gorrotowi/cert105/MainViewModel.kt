package com.gorrotowi.cert105

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val mutableName: MutableLiveData<String> = MutableLiveData()
    val name: LiveData<String> = mutableName

    fun setName(newName: String) {
        mutableName.value = newName
    }
}
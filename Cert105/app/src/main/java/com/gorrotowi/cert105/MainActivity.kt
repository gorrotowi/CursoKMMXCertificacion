package com.gorrotowi.cert105

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpListeners()
        setUpObservables()

    }

    private fun setUpObservables() {
        viewModel.name.observe(this, Observer { name ->
            txtName?.text = name
        })

        viewModel.booksList.observe(this, Observer { listBooks ->
            Log.d("Books", "${listBooks?.map { it.toString() }}")
        })
    }

    private fun setUpListeners() {
        btnGetName?.setOnClickListener {
            val name = edtxtName?.text?.toString() ?: ""
//            viewModel.setName(name)
            viewModel.addBook(name)
        }

        btnGetBooks?.setOnClickListener {
            viewModel.getBooksByFlow()
//            viewModel.getBooks()
        }
    }
}
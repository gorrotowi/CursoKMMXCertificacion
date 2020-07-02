package com.gorrotowi.cert102recyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val adapter by lazy {
        RcListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpViews()
        setData()
        setUpListeners()
    }

    private fun setUpListeners() {
        btnAdd?.setOnClickListener {
            val newData = adapter.dataSource
            newData?.addAll(getLastData())
            adapter.dataSource = newData
        }
    }

    private fun setData() {
        adapter.dataSource = getLastData()
    }

    private fun getLastData(): MutableList<ItemModel> = (0..15).mapIndexed { index, data ->
        ItemModel("Title $index", "Descr pos Rango -> $data")
    }.toMutableList()

    private fun setUpViews() {
        rcList?.adapter = adapter
    }
}

data class ItemModel(val title: String, val desc: String)
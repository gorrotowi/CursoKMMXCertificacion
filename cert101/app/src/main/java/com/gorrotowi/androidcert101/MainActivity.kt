package com.gorrotowi.androidcert101

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gorrotowi.androidcert101.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        Log.e("", "asdasd")
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
//        binding.txtHello.setOnClickListener {
//            Log.i("Click", "onResume: --->>>")
//            val customToastBinding = MyCustomToastBinding.inflate(layoutInflater)
//            customToastBinding.txtCustomToastTitle.text = "Title Toast"
//            customToastBinding.txtCustomToastDescrip.text = "My description 🎤"
//            customToastBinding.cstmToast.txtCustomToast.text = "Mi include 🤪"
//            val container = findViewById<ViewGroup>(R.id.viewGroupCustomToast)
//            val layout = layoutInflater.inflate(R.layout.my_custom_toast, container)
//            val textToastTitle = layout.findViewById<TextView>(R.id.txtCustomToastTitle)
//            val textToastDescrp = layout.findViewById<TextView>(R.id.txtCustomToastDescrip)
//            val includeToast = layout.findViewById<ViewGroup>(R.id.cstmToast)
//            val txtCstmToast = includeToast.findViewById<TextView>(R.id.txtCustomToast)
//            txtCstmToast.text = "asdkjfasdjf"
//            textToastTitle.text = "Title Toast"
//            textToastDescrp.text = "My description 🦄"


//            Toast(this).apply {
//                duration = Toast.LENGTH_SHORT
//                view = customToastBinding.root
//                show()
//            }

//        }
    }

    //            val bindingToast = CustomToastBinding.inflate(layoutInflater)
//            bindingToast.txtCustomToast.text = "Another text and emoji 👀"
//            Toast(this).apply {
//                duration = Toast.LENGTH_SHORT
//                setGravity(Gravity.CENTER, 0, 0)
//                view = bindingToast.toastContainer
//                show()
//            }

//            showAnotherToast()

    private fun showAnotherToast() {
        val container = findViewById<ViewGroup>(R.id.toastContainer)
        val layout = layoutInflater.inflate(R.layout.custom_toast, container)
        val textView = layout.findViewById<TextView>(R.id.txtCustomToast)
        textView.text = "Load anooooother emoji 😁"
        with(Toast(this)) {
            duration = Toast.LENGTH_LONG
            view = layout
            show()
        }
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
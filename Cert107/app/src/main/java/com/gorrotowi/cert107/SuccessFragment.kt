package com.gorrotowi.cert107

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs

class SuccessFragment : Fragment(R.layout.fragment_success) {

    private val args by navArgs<SuccessFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences =
            requireActivity().getSharedPreferences(
                "com.gorrotowi.cert107.SharedPreferences",
                Context.MODE_PRIVATE
            )

        sharedPreferences.edit(true) {
            putString("name", args.name)
            putInt("phone", args.phone)
        }

//        val edit = sharedPreferences.edit()
//        edit.putString("name", args.name)
//        edit.putInt("phone", args.phone)
//        edit.apply()

//        val name = sharedPreferences.getString("name", "")

//        arguments?.let { bundle: Bundle ->
//            val name = bundle.getString("name", "")
//            val phone = bundle.getInt("phone", 0)
//            Log.e(
//                "DATA Bundle", """
//            $name
//            $phone
//        """.trimIndent()
//            )
//        }
//
//        Log.e(
//            "DATA Safe Bundle", """
//            ${args.name}
//            ${args.phone}
//        """.trimIndent()
//        )


    }

}
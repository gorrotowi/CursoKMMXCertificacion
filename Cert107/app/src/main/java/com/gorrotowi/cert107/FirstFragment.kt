package com.gorrotowi.cert107

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_first.*


class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSendContact?.setOnClickListener {
//            findNavController().navigate(R.id.action_firstFragment_to_successFragment)
            val action = FirstFragmentDirections
                .actionFirstFragmentToSuccessFragment(
                    txtInpEdtxtName?.text?.toString() ?: "",
                    edtxtPhone?.text?.toString()?.toIntOrNull() ?: 0
                )
            findNavController().navigate(action)

        }
    }

}
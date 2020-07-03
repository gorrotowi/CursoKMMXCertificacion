package com.gorrotowi.cert103

import android.os.Bundle
import android.transition.TransitionManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val constraintSet1 = ConstraintSet()
    val constraintSet2 = ConstraintSet()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        constraintSet1.load(this, R.layout.activity_main)
        constraintSet2.load(this, R.layout.activity_main_two)

        val snackBar = Snackbar.make(
            mainContainer,
            getString(R.string.snackbar_show_bio),
            Snackbar.LENGTH_LONG
        ).apply {
            setActionTextColor(
                ContextCompat.getColor(
                    this@MainActivity,
                    R.color.colorAction
                )
            )
            setAction(getString(R.string.snackbar_action_back)) {
                TransitionManager.beginDelayedTransition(mainContainer)
                constraintSet1.applyTo(mainContainer)
            }
        }

        btnProfileMore?.setOnClickListener {
            TransitionManager.beginDelayedTransition(mainContainer)
            constraintSet2.applyTo(mainContainer)
            snackBar.show()
        }

    }
}
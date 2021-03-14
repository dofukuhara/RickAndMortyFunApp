package com.fukuhara.designsystem

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.Group

class ErrorFeedback(private val view: View) {

    fun showErrorFeedbackScreen() {
        handErrorFeedbackVisibility(true)
    }

    fun hideErrorFeedbackScreen() {
        handErrorFeedbackVisibility(false)
    }

    private fun handErrorFeedbackVisibility(shouldShow: Boolean) {
        view.findViewById<Group>(R.id.ds_error_group).visibility = when(shouldShow) {
            true -> View.VISIBLE
            false -> View.GONE
        }
    }

    fun setErrorMessage(message: String) {
        view.findViewById<TextView>(R.id.ds_error_title).text = message
    }

    fun setOnRetryButtonListener(onClickListener: View.OnClickListener) {
        view.findViewById<Button>(R.id.ds_error_btn).setOnClickListener(onClickListener)
    }
}
package com.example.assignment

import android.app.Dialog
import android.content.Context
import android.graphics.Color

import androidx.appcompat.app.AppCompatActivity

import androidx.core.graphics.drawable.toDrawable


open class BaseActivity : AppCompatActivity() {


    private var doubleBackToExitPressedOnce = false
    private lateinit var loadingDialog : Dialog

    fun showLoadingDialog(){

        loadingDialog = Dialog(this)

        loadingDialog.let {
            it.show()
            it.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
            it.setContentView(R.layout.dialog_progress)
            it.setCancelable(false)
            it.setCanceledOnTouchOutside(false)
        }
    }

    fun hideLoading(){
        loadingDialog.let { if (it.isShowing)it.cancel() }
    }


}


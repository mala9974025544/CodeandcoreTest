package com.codeandcore.test
import android.view.View
import androidx.appcompat.app.AppCompatActivity


open class BaseActivity : AppCompatActivity() {



    fun fullScreen(){
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }


}
package com.codeandcore.test.utils
import android.app.Activity
import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.Uri
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import com.codeandcore.test.R


object Utils {

    fun isNetworkAvailable(context: Context?): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
    fun showToast(context: Context?, mesaage: String, time: Int) {
        if (context != null) {
            val inflater: LayoutInflater = (context as Activity).layoutInflater
            val layout: View = inflater.inflate(
                R.layout.custom_toast,
                (context as Activity).findViewById(R.id.toast_layout_root) as ViewGroup?
            )


            val text = layout.findViewById<View>(R.id.text) as TextView

            text.text = mesaage

            val toast = Toast(context)

            //toast.setGravity(Gravity.BOTTOM, 0, 0)
            toast.duration = Toast.LENGTH_LONG
            toast.view = layout
            toast.show()
        }

    }

}
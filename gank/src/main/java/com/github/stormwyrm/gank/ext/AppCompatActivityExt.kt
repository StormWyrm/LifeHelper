package com.github.stormwyrm.gank.ext

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.github.stormwyrm.gank.R

fun AppCompatActivity.replaceFragment(fragment: Fragment, @IdRes containerId: Int, tag: String) {
    supportFragmentManager.transact {
        replace(containerId, fragment, tag)
    }
}

fun AppCompatActivity.hideKeyboard() {
    val view = currentFocus
    if (view != null) {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}

fun AppCompatActivity.transparentStatusBar() {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        //android5.0以上 清除半透明的效果
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        //能将内容扩展到状态栏下
        val flags = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.decorView.systemUiVisibility = flags

        window.statusBarColor = Color.TRANSPARENT
    }

}

private fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        setCustomAnimations(
                R.anim.anim_grow_fade_in_from_bottom,
                R.anim.anim_fade_out,
                R.anim.anim_fade_in,
                R.anim.anim_shrink_fade_out_from_bottom
        )
        action()
    }.commit()
}
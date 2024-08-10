package com.aman.androidlibrary.my_helper.view.controller

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.viewbinding.ViewBinding

abstract class BaseLandscapeActivity<T : ViewBinding> : BaseActivity<T>() {
    override val TAG: String = "logs." + BaseLandscapeActivity::class.java.canonicalName

    override fun onCreate(savedInstanceState: Bundle?) {
        val windowInsetsController =
            WindowCompat.getInsetsController(window, window.decorView)
        // Configure the behavior of the hidden system bars.
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        // Add a listener to update the behavior of the toggle fullscreen button when
        // the system bars are hidden or revealed.
        window.decorView.setOnApplyWindowInsetsListener { view: View, windowInsets: WindowInsets ->
            // You can hide the caption bar even when the other system bars are visible.
            // To account for this, explicitly check the visibility of navigationBars()
            // and statusBars() rather than checking the visibility of systemBars().
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (windowInsets.isVisible(WindowInsetsCompat.Type.navigationBars())
                    || windowInsets.isVisible(WindowInsetsCompat.Type.statusBars())
                    || windowInsets.isVisible(WindowInsetsCompat.Type.systemBars())
                ) {
                    windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
                    windowInsetsController.hide(WindowInsetsCompat.Type.statusBars())
                    windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())
                }
            }
            view.onApplyWindowInsets(windowInsets)
        }
        super.onCreate(savedInstanceState)
    }
}

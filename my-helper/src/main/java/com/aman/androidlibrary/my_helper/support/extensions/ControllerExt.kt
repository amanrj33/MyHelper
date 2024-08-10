package com.aman.androidlibrary.my_helper.support.extensions

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.aman.androidlibrary.my_helper.support.thread.MainHandler
import com.google.android.material.snackbar.BaseTransientBottomBar

fun Application.toast(msg: String) {
    if (msg.isEmpty()) return
    MainHandler.post {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}

fun AppCompatActivity.toast(msg: String) {
    if (msg.isEmpty()) return
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(msg: String) {
    if (msg.isEmpty()) return
    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.snackBar(message: String) {
    if (message.isNotEmpty()) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
    }
}

fun Fragment.snackBar(message: String) {
    if (message.isNotEmpty()) {
        Snackbar.make(
            requireActivity().findViewById(android.R.id.content),
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }
}

fun AppCompatActivity.snackbar(
    message: String,
    @BaseTransientBottomBar.Duration duration: Int,
    actionText: String? = null,
    action: Runnable? = null
): Snackbar {
    val snackbar = Snackbar.make(findViewById(android.R.id.content), message, duration)
    if (actionText != null && action != null) snackbar.setAction(actionText) { v: View -> action.run() }
    snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
    snackbar.show()
    return snackbar
}

fun Fragment.snackbar(
    message: String,
    @BaseTransientBottomBar.Duration duration: Int,
    actionText: String? = null,
    action: Runnable? = null
): Snackbar {
    val snackbar =
        Snackbar.make(requireActivity().findViewById(android.R.id.content), message, duration)
    if (actionText != null && action != null) snackbar.setAction(actionText) { v: View -> action.run() }
    snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
    snackbar.show()
    return snackbar
}

fun AppCompatActivity.navigate(destination: Class<*>, bundle: Bundle? = null) {
    val intent = Intent(this, destination)
    if (bundle != null) {
        intent.putExtras(bundle)
    }
    startActivity(intent)
}

fun Fragment.navigate(destination: Class<*>, bundle: Bundle? = null) {
    val intent = Intent(requireContext(), destination)
    if (bundle != null) {
        intent.putExtras(bundle)
    }
    startActivity(intent)
}

fun AppCompatActivity.navigateWithFinish(destination: Class<*>) {
    startActivity(Intent(this, destination))
    finish()
}

fun Fragment.navigateWithFinish(destination: Class<*>) {
    startActivity(Intent(requireContext(), destination))
    requireActivity().finish()
}

fun AppCompatActivity.navigateFinishAffinity(destination: Class<*>) {
    startActivity(Intent(this, destination))
    finishAffinity()
}

fun Fragment.navigateFinishAffinity(destination: Class<*>) {
    startActivity(Intent(requireContext(), destination))
    requireActivity().finishAffinity()
}

fun AppCompatActivity.navigateWithTransitionAnimation(
    destination: Class<*>,
    sharedView: View,
    sharedName: String
) {
    val activityOptionsCompat =
        ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedView, sharedName)
    startActivity(Intent(this, destination), activityOptionsCompat.toBundle())
}

fun Fragment.navigateWithTransitionAnimation(
    destination: Class<*>,
    sharedView: View,
    sharedName: String
) {
    val activityOptionsCompat =
        ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(), sharedView, sharedName)
    startActivity(Intent(requireContext(), destination), activityOptionsCompat.toBundle())
}
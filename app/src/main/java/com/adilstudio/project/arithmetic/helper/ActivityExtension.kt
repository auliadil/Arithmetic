package com.adilstudio.project.arithmetic.helper

import android.app.Activity
import android.widget.Toast

fun Activity.showTopToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}
package com.adilstudio.project.arithmetic.ui.login

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * User details post authentication that is exposed to the UI
 */
@Parcelize
data class LoggedInUserView(
    val displayName: String
    //... other data fields that may be accessible to the UI
) : Parcelable
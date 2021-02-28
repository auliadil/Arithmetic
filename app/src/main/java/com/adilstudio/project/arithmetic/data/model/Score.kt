package com.adilstudio.project.arithmetic.data.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Score(
    var id: String? = null,
    var player: String? = null,
    var level: String? = null,
    var point: String? = null
)
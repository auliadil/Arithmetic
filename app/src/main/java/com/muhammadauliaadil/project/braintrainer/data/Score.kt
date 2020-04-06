package com.muhammadauliaadil.project.braintrainer.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Score(
    var id: String? = null,
    var player: String? = null,
    var level: String? = null,
    var point: String? = null
)
package com.greenmen.domain

import kotlinx.datetime.Instant

data class Cardio(
    val distance: Double,
    val time: Long,
    val timestamp: Instant,
)
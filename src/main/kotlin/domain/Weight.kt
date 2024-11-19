package com.greenmen.domain

import kotlinx.datetime.Instant

data class Weight(
    val value: Double,
    val timestamp: Instant,
)
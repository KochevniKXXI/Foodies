package ru.requestdesign.test.nomad.core.network.data

import kotlinx.serialization.Serializable

@Serializable
data class NetworkTag(
    val id: Int,
    val name: String,
)

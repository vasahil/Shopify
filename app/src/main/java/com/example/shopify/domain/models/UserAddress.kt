package com.example.shopify.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class UserAddress(
    val firstName: String = "",
    val lastName: String = "",
    val address: String = "",
    val city: String = "",
    val state: String = "",
    val pinCode: String = "",
    val country: String = "",
    val phoneNumber: String = "",
)

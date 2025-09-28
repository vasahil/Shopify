package com.example.shopify.domain.models

import com.example.shopify.presentation.viewModels.UploadUserProfileImageState
import kotlinx.serialization.Serializable

@Serializable
data class ProductDataModels(
    val name: String = "",
    val description: String = "",
    val price: String = "",
    val finalPrice: String = "",
    val category: String = "",
    val date: Long = System.currentTimeMillis(),
    val createdBy: String = "",
    val availableUnits: Int = 0,
    var productId: String = "",
    var image: String = ""

    )

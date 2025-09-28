package com.example.shopify.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class CategoryDataModels(
    var name: String = "",
    var date: Long = System.currentTimeMillis(),
    var createdBy: String = "",       // default value added
    var categoryImage: String = ""    // default value added
)

package com.example.shopify.common

import com.example.shopify.domain.models.CategoryDataModels
import com.example.shopify.domain.models.ProductDataModels

data class HomeScreenState (
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val categories: List<CategoryDataModels>? = null,
    val products: List<ProductDataModels>? = null,

)
package com.example.shopify.domain.useCase

import com.example.shopify.common.ResultState
import com.example.shopify.domain.models.ProductDataModels
import com.example.shopify.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(private val repo: Repo) {
    fun getProductById(productId: String): Flow<ResultState<ProductDataModels>> {
        return repo.getProductById(productId)
    }

}
package com.example.shopify.domain.useCase

import com.example.shopify.common.ResultState
import com.example.shopify.domain.models.ProductDataModels
import com.example.shopify.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCheckOutUseCase @Inject constructor(private val repo: Repo){
    fun getCheckOutUseCase(productId: String): Flow<ResultState<ProductDataModels>> {
        return repo.getCheckOut(productId)
    }
}
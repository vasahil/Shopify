package com.example.shopify.domain.useCase

import com.example.shopify.common.ResultState
import com.example.shopify.domain.models.ProductDataModels
import com.example.shopify.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

    class GetAllFavUseCase @Inject constructor(private val repo: Repo) {
    fun getAllFav(): Flow<ResultState<List<ProductDataModels>>> {
        return repo.getAllFav()
    }
}
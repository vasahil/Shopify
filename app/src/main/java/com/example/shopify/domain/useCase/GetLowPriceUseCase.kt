package com.example.shopify.domain.useCase

import com.example.shopify.common.ResultState
import com.example.shopify.domain.models.LowPriceDataModel
import com.example.shopify.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLowPriceUseCase @Inject constructor(private val repo: Repo){
    fun getlowPriceUseCase(): Flow<ResultState<List<LowPriceDataModel>>> {
        return repo.getLowPriceProducts()
    }
}
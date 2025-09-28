package com.example.shopify.domain.useCase

import com.example.shopify.common.ResultState
import com.example.shopify.domain.models.CartDataModels
import com.example.shopify.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(private val repo: Repo) {

    fun addToCart(cartDataModels: CartDataModels): Flow<ResultState<String>>{
        return repo.addToCart(cartDataModels)
    }


}
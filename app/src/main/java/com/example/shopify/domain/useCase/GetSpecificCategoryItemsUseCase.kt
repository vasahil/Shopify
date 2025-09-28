package com.example.shopify.domain.useCase

import com.example.shopify.common.ResultState
import com.example.shopify.domain.models.ProductDataModels
import com.example.shopify.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSpecificCategoryItemsUseCase @Inject constructor(private val repo: Repo) {
    fun getSpecificCategoryItems(categoryName: String): Flow<ResultState<List<ProductDataModels>>> {
        return repo.getSpecificCategoryItems(categoryName)
    }

}
package com.example.shopify.domain.useCase

import com.example.shopify.common.ResultState
import com.example.shopify.domain.models.CartDataModels
import com.example.shopify.domain.models.CategoryDataModels
import com.example.shopify.domain.models.ProductDataModels
import com.example.shopify.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCategoryUseCase @Inject constructor(private val repo: Repo) {
    fun getAllCategoriesUseCase(): Flow<ResultState<List<CategoryDataModels>>> {
        return repo.getAllCategories()
    }
}
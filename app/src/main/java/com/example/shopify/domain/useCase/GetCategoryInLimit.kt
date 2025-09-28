package com.example.shopify.domain.useCase

import com.example.shopify.common.ResultState
import com.example.shopify.domain.models.CategoryDataModels
import com.example.shopify.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoryInLimit @Inject constructor(private val repo: Repo){
    fun getCategoryInLimited(): Flow<ResultState<List<CategoryDataModels>>> {
        return repo.getCategoriesInLimited()
    }

}
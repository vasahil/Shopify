package com.example.shopify.domain.useCase

import com.example.shopify.common.ResultState
import com.example.shopify.domain.models.ProductDataModels
import com.example.shopify.domain.models.UserData
import com.example.shopify.domain.models.UserDataParent
import com.example.shopify.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repo: Repo) {
    fun getUserById(uid: String): Flow<ResultState<UserDataParent>> {
        return repo.getUserById(uid)
    }

}
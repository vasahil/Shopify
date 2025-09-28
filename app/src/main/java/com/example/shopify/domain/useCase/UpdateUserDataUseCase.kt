package com.example.shopify.domain.useCase

import com.example.shopify.common.ResultState
import com.example.shopify.domain.models.ProductDataModels
import com.example.shopify.domain.models.UserData
import com.example.shopify.domain.models.UserDataParent
import com.example.shopify.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateUserDataUseCase @Inject constructor(private val repo: Repo) {
    fun updateUserData(userDataParent: UserDataParent): Flow<ResultState<String>> {
        return repo.updateUserData(userDataParent)
    }

}
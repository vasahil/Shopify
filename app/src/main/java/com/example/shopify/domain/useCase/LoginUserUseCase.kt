package com.example.shopify.domain.useCase

import com.example.shopify.common.ResultState
import com.example.shopify.domain.models.UserData
import com.example.shopify.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(private val repo: Repo) {
    fun loginUser(userData: UserData): Flow<ResultState<String>> {
        return repo.loginUserWithEmailAndPassword(userData)
    }

}
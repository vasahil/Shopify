package com.example.shopify.domain.useCase

import android.net.Uri
import com.example.shopify.common.ResultState
import com.example.shopify.domain.models.ProductDataModels
import com.example.shopify.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserProfileImageUseCase @Inject constructor(private val repo: Repo) {
    fun userProfileImage(uri: Uri): Flow<ResultState<String>> {
        return repo.userProfileImage(uri)
    }

}
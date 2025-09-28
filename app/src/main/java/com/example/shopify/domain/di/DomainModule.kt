package com.example.shopify.domain.di

import com.example.shopify.data.repo.RepoImpl
import com.example.shopify.domain.repo.Repo
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    fun providesRepo(firebaseAuth: FirebaseAuth, firebaseFirestore: FirebaseFirestore): Repo {
        return RepoImpl(firebaseAuth, firebaseFirestore)
    }
}
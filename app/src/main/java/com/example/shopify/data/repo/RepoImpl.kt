package com.example.shopify.data.repo

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.graphics.vector.PathNode
import com.example.shopify.common.ADD_TO_CART
import com.example.shopify.common.ADD_TO_FAV
import com.example.shopify.common.PRODUCT_COLLECTION
import com.example.shopify.common.ResultState
import com.example.shopify.common.USER_COLLECTION
import com.example.shopify.domain.models.CartDataModels
import com.example.shopify.domain.models.CategoryDataModels
import com.example.shopify.domain.models.LowPriceDataModel
import com.example.shopify.domain.models.ProductDataModels
import com.example.shopify.domain.models.UserData
import com.example.shopify.domain.models.UserDataParent
import com.example.shopify.domain.repo.Repo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class RepoImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
): Repo{
    override fun registerUserWithEmailAndPassword(userData: UserData): Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.Loading)
        firebaseAuth.createUserWithEmailAndPassword(userData.email, userData.password).addOnCompleteListener {
            if(it.isSuccessful){
               firebaseFirestore.collection(USER_COLLECTION).document(it.result.user?.uid.toString()).set(userData).addOnCompleteListener {
                   if(it.isSuccessful){
                       trySend(ResultState.Success("User Registered Successfully and add to Firestore"))
                   }else{
                       if(it.exception!=null){
                           trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                       }
                   }
               }
                trySend(ResultState.Success("User Registered Successfully"))
            }else{
                if(it.exception!= null) {
                    trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                }
            }

        }
        awaitClose {
            close()
        }
    }

    override fun loginUserWithEmailAndPassword(userData: UserData): Flow<ResultState<String>>  = callbackFlow{
          trySend(ResultState.Loading)
        firebaseAuth.signInWithEmailAndPassword(userData.email, userData.password).addOnCompleteListener {
            if(it.isSuccessful) {
                trySend(ResultState.Success("User Login Successfully"))
            }else{
                if(it.exception != null) {
                    trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                }
            }
        }
        awaitClose {
            close()
        }
    }

    override fun getUserById(uid: String): Flow<ResultState<UserDataParent>>  = callbackFlow{
        trySend(ResultState.Loading)

        firebaseFirestore.collection(USER_COLLECTION).document(uid).get().addOnCompleteListener {
            if(it.isSuccessful) {
                val data = it.result.toObject(UserData::class.java)!!
                val userDataParent = UserDataParent(it.result.id, data)
                trySend(ResultState.Success(userDataParent))
            }else{
                if(it.exception!=null){
                    trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                }
            }
        }
        awaitClose {
            close()
        }
    }

    override fun updateUserData(userDataParent: UserDataParent): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)

        firebaseFirestore.collection(USER_COLLECTION).document(userDataParent.nodeId).update(userDataParent.userData.toMap()).addOnCompleteListener {
            if(it.isSuccessful){
                trySend(ResultState.Success("User data updated Successfully"))
            }else{
                if(it.exception!=null){
                    trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                }
            }
        }
        awaitClose {
            close()
        }
    }

    override fun userProfileImage(uri: Uri): Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.Loading)

        FirebaseStorage.getInstance().reference.child("userProfileImage/${System.currentTimeMillis()} + ${firebaseAuth.currentUser?.uid}").putFile(uri?: Uri.EMPTY).addOnCompleteListener {
            it.result.storage.downloadUrl.addOnSuccessListener { imageUri->
                trySend(ResultState.Success(imageUri.toString()))

            }

            if(it.exception != null) {
                trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
            }
        }
        awaitClose {
            close()
        }
    }

    override fun getCategoriesInLimited(): Flow<ResultState<List<CategoryDataModels>>>  = callbackFlow{
        trySend(ResultState.Loading)

        firebaseFirestore.collection("categories").limit(7).get().addOnSuccessListener {querySnapshot ->
            val categories = querySnapshot.documents.mapNotNull { document->
                document.toObject(CategoryDataModels::class.java)
            }
            trySend(ResultState.Success(categories))
        }.addOnFailureListener {
            trySend(ResultState.Error(it.toString()))
        }
        awaitClose {
            close()
        }
    }

    override fun getProductsInLimited(): Flow<ResultState<List<ProductDataModels>>> = callbackFlow{
        trySend(ResultState.Loading)

        firebaseFirestore.collection(PRODUCT_COLLECTION).limit(10).get().addOnSuccessListener {
            val products = it.documents.mapNotNull { document ->
                document.toObject(ProductDataModels::class.java)?.apply {
                    productId = document.id
                }
            }
            trySend(ResultState.Success(products))
        }.addOnFailureListener {
            trySend(ResultState.Error(it.toString()))
        }

        awaitClose {
            close()
        }
    }

    override fun getAllProducts(): Flow<ResultState<List<ProductDataModels>>> = callbackFlow {
        trySend(ResultState.Loading)

        firebaseFirestore.collection(PRODUCT_COLLECTION).get().addOnSuccessListener {
            val products = it.documents.mapNotNull{documentSnapshot ->
                documentSnapshot.toObject(ProductDataModels::class.java)?.apply {
                    productId = documentSnapshot.id
                }
            }
            trySend(ResultState.Success(products))

        }.addOnFailureListener {
            trySend(ResultState.Error(it.toString()))
        }
        awaitClose {
            close()
        }
    }



    override fun getProductById(productId: String): Flow<ResultState<ProductDataModels>> = callbackFlow{
        trySend(ResultState.Loading)

        firebaseFirestore.collection(PRODUCT_COLLECTION).document(productId).get().addOnSuccessListener {
            val product = it.toObject(ProductDataModels :: class.java)
               trySend(ResultState.Success(product!!))
            }.addOnFailureListener {
                trySend(ResultState.Error(it.toString()))
        }
        awaitClose {
            close()
        }



    }

    override fun addToCart(cartDataModels: CartDataModels): Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.Loading)

        firebaseFirestore.collection(ADD_TO_CART).document(firebaseAuth.currentUser!!.uid).collection("User_Cart").add(cartDataModels).addOnSuccessListener {
            Log.d("Add to Cart", "Cart: ${cartDataModels.name}")
            trySend(ResultState.Success("Product Added To Cart"))
        }.addOnFailureListener {
            trySend(ResultState.Error(it.toString()))
        }
        awaitClose {
            close()
        }
    }

    override fun removeFromCart(cartDataModels: CartDataModels): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)

        val uid = firebaseAuth.currentUser?.uid ?: run {
            trySend(ResultState.Error("User not logged in"))
            close()
            return@callbackFlow
        }

        firebaseFirestore.collection(ADD_TO_CART)
            .document(uid)
            .collection("User_Cart")
            .document(cartDataModels.cartId) // ensure `cartId` is the Firestore documentId
            .delete()
            .addOnSuccessListener {
                trySend(ResultState.Success("Removed from cart"))
            }
            .addOnFailureListener { exception ->
                trySend(ResultState.Error(exception.localizedMessage ?: "Error removing item"))
            }

        awaitClose { close() }
    }


    override fun addToFav(productDataModels: ProductDataModels): Flow<ResultState<String>>  = callbackFlow{
        trySend(ResultState.Loading)

        firebaseFirestore.collection(ADD_TO_FAV).document(firebaseAuth.currentUser!!.uid).collection("User_Fav").add(productDataModels).addOnSuccessListener {
            trySend(ResultState.Success("Product added to Favourite"))
        }.addOnFailureListener {
            trySend(ResultState.Error(it.toString()))
        }
        awaitClose {
            close()
        }
    }

    override fun getAllFav(): Flow<ResultState<List<ProductDataModels>>> = callbackFlow{
        trySend(ResultState.Loading)

        firebaseFirestore.collection(ADD_TO_FAV).document(firebaseAuth.currentUser!!.uid).collection("User_Fav").get().addOnSuccessListener {
            val favProducts = it.documents.mapNotNull {document->
                document.toObject(ProductDataModels::class.java)
            }
            trySend(ResultState.Success(favProducts))
        }.addOnFailureListener {
            trySend(ResultState.Error(it.toString()))
        }
        awaitClose {
            close()
        }
    }

    override fun getCart(): Flow<ResultState<List<CartDataModels>>> = callbackFlow{
        trySend(ResultState.Loading)

        firebaseFirestore.collection(ADD_TO_CART).document(firebaseAuth.currentUser!!.uid).collection("User_Cart").get().addOnSuccessListener {
            val cart = it.documents.mapNotNull {document->
                document.toObject(CartDataModels::class.java)?.apply {
                    cartId = document.id
                }
            }
            trySend(ResultState.Success(cart))
        }.addOnFailureListener {
            trySend(ResultState.Error(it.toString()))
        }
        awaitClose {
            close()
        }
    }

    override fun getAllCategories(): Flow<ResultState<List<CategoryDataModels>>> = callbackFlow{
        trySend(ResultState.Loading)

        firebaseFirestore.collection("categories").get().addOnSuccessListener {
            val categories = it.documents.mapNotNull { document->
                document.toObject(CategoryDataModels::class.java)
            }
             trySend(ResultState.Success(categories))
            Log.d("FirestoreCategories", "All categories: $categories")
        }.addOnFailureListener {
            trySend(ResultState.Error(it.toString()))

        }
        awaitClose {
            close()
        }
    }

    override fun getCheckOut(productId: String): Flow<ResultState<ProductDataModels>> = callbackFlow{
        trySend(ResultState.Loading)

        firebaseFirestore.collection(PRODUCT_COLLECTION).document(productId).get().addOnSuccessListener {
            val product = it.toObject(ProductDataModels::class.java)
            trySend(ResultState.Success(product!!))
        }.addOnFailureListener {
            trySend(ResultState.Error(it.toString()))
        }
        awaitClose {
            close()
        }
    }

    override fun getSpecificCategoryItems(categoryName: String): Flow<ResultState<List<ProductDataModels>>> = callbackFlow{
        trySend(ResultState.Loading)

        firebaseFirestore.collection(PRODUCT_COLLECTION).whereEqualTo("category",categoryName).get().addOnSuccessListener {
            val products = it.documents.mapNotNull {document->
                document.toObject(ProductDataModels::class.java)?.apply {
                    productId = document.id
                }
            }
            trySend(ResultState.Success(products))
        }.addOnFailureListener {
            trySend(ResultState.Error(it.toString()))
        }
        awaitClose {
            close()
        }
    }

    override fun getAllSuggestedProducts(): Flow<ResultState<List<ProductDataModels>>> = callbackFlow {
        trySend(ResultState.Loading)

        firebaseFirestore.collection(ADD_TO_FAV).document(firebaseAuth.currentUser!!.uid)
            .collection("User_Fav").get().addOnSuccessListener {
            val fav = it.documents.mapNotNull { document ->
                document.toObject(ProductDataModels::class.java)

            }
                trySend (ResultState.Success(fav))
        }.addOnFailureListener {
            trySend(ResultState.Error(it.toString()))
        }
        awaitClose {
            close()
        }
    }

    override fun getLowPriceProducts(): Flow<ResultState<List<LowPriceDataModel>>> = callbackFlow{
        trySend(ResultState.Loading)

        firebaseFirestore.collection("low_price_items").get().addOnSuccessListener {
            val items = it.documents.mapNotNull { documentSnapshot ->
                documentSnapshot.toObject(LowPriceDataModel::class.java)
            }
            trySend(ResultState.Success(items))
        }.addOnFailureListener {
            trySend(ResultState.Error(it.toString()))
        }
        awaitClose {
            close()
        }
    }

    override fun googleSignIn(idToken: String, userData: UserData): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)

        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { it ->
            if(it.isSuccessful){
                firebaseFirestore.collection(USER_COLLECTION).document(it.result.user?.uid.toString()).set(userData).addOnCompleteListener {
                    if(it.isSuccessful){
                        trySend(ResultState.Success("User Registered Successfully and add to Firestore"))
                    }else{
                        if(it.exception!=null){
                            trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                        }
                    }
                }
                trySend(ResultState.Success("User Registered Successfully"))
            }else{
                if(it.exception!= null) {
                    trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                }
            }

        }
        awaitClose {
            close()
        }
    }

    override fun removeFav(productDataModels: ProductDataModels): Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.Loading)

        firebaseFirestore.collection(ADD_TO_FAV).document(firebaseAuth.currentUser!!.uid).collection("User_Fav").document(productDataModels.productId).delete().addOnSuccessListener {
            Log.d("RemoveFav", "Removed: ${productDataModels.productId}")
            trySend(ResultState.Success("Removed from wishlist"))
        }
            .addOnFailureListener { exception ->
                Log.e("RemoveFav", "Failed to remove: ${exception.localizedMessage}")
                trySend(ResultState.Error(exception.localizedMessage ?: "Error removing item"))
            }
        awaitClose {
            close()
        }

        }


}
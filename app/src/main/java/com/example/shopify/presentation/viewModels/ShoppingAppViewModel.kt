package com.example.shopify.presentation.viewModels

import android.net.Uri
import android.util.Log
import android.view.View
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shopify.common.HomeScreenState
import com.example.shopify.common.ResultState
import com.example.shopify.domain.models.CartDataModels
import com.example.shopify.domain.models.CategoryDataModels
import com.example.shopify.domain.models.LowPriceDataModel
import com.example.shopify.domain.models.ProductDataModels
import com.example.shopify.domain.models.UserData
import com.example.shopify.domain.models.UserDataParent
import com.example.shopify.domain.useCase.AddToCartUseCase
import com.example.shopify.domain.useCase.AddToFavUseCase
import com.example.shopify.domain.useCase.CreateUserUseCase
import com.example.shopify.domain.useCase.GetAllCategoryUseCase
import com.example.shopify.domain.useCase.GetAllFavUseCase
import com.example.shopify.domain.useCase.GetAllProductUseCase
import com.example.shopify.domain.useCase.GetAllSuggestedProductsUseCase
import com.example.shopify.domain.useCase.GetCartUseCase
import com.example.shopify.domain.useCase.GetCategoryInLimit
import com.example.shopify.domain.useCase.GetCheckOutUseCase
import com.example.shopify.domain.useCase.GetLowPriceUseCase
import com.example.shopify.domain.useCase.GetProductByIdUseCase
import com.example.shopify.domain.useCase.GetProductsInLimitUseCase
import com.example.shopify.domain.useCase.GetSpecificCategoryItemsUseCase
import com.example.shopify.domain.useCase.GetUserUseCase
import com.example.shopify.domain.useCase.GoogleSignInUseCase
import com.example.shopify.domain.useCase.LoginUserUseCase
import com.example.shopify.domain.useCase.RemoveCartUseCase
import com.example.shopify.domain.useCase.RemoveFavUseCase
import com.example.shopify.domain.useCase.UpdateUserDataUseCase
import com.example.shopify.domain.useCase.UserProfileImageUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingAppViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase,
    private val loginUserUseCase: LoginUserUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val updateUserDataUseCase: UpdateUserDataUseCase,
    private val userProfileImageUseCase: UserProfileImageUseCase,
    private val getCategoryInLimit: GetCategoryInLimit,
    private val getProductsInLimitUseCase: GetProductsInLimitUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val addToFavUseCase: AddToFavUseCase,
    private val getAllFavUseCase: GetAllFavUseCase,
    private val getAllCategoryUseCase: GetAllCategoryUseCase,
    private val getCheckOutUseCase: GetCheckOutUseCase,
    private val getSpecificCategoryItemsUseCase: GetSpecificCategoryItemsUseCase,
    private val getAllSuggestedProductsUseCase: GetAllSuggestedProductsUseCase,
    private val getCartUseCase: GetCartUseCase,
    private val getAllProductUseCase: GetAllProductUseCase,
    private val getLowPriceUseCase: GetLowPriceUseCase,
    private val googleSignInUseCase: GoogleSignInUseCase,
    private val removeFavUseCase: RemoveFavUseCase,
    private val removeCartUseCase: RemoveCartUseCase



    ) : ViewModel() {

    private val _signUpScreenState = MutableStateFlow(SignUpScreenState())
    val signUpScreenState = _signUpScreenState.asStateFlow()

    private val _loginScreenState = MutableStateFlow(LoginScreenState())
    val loginScreenState = _loginScreenState.asStateFlow()

    private val _googleSingIn = MutableStateFlow(LoginScreenState())
    val googleSignIn = _googleSingIn.asStateFlow()

    private val _profileScreenState = MutableStateFlow(ProfileScreenState())
    val profileScreenState = _profileScreenState.asStateFlow()

    private val _updateScreenState = MutableStateFlow(UpdateScreenState())
    val updateScreenState = _updateScreenState.asStateFlow()

    private val _uploadUserProfileImageState = MutableStateFlow(UploadUserProfileImageState())
    val uploadUserProfileImageState = _uploadUserProfileImageState.asStateFlow()

    private val _addToCartState = MutableStateFlow(AddToCartScreenState())
    val addToCartState = _addToCartState.asStateFlow()

    private val _removeCart = MutableStateFlow(AddToCartScreenState())
    val removeCart = _removeCart.asStateFlow()


    private val _getProductByIDState = MutableStateFlow(GetProductByIDState())
    val getProductByIDState = _getProductByIDState.asStateFlow()

    private val _addToFavState = MutableStateFlow(AddToFavState())
    val addToFavState = _addToFavState.asStateFlow()

    private val _removeFav = MutableStateFlow(RemoveFavState())
    val removeFav = _removeFav.asStateFlow()

    private val _getAllFavState = MutableStateFlow(GetAllFavState())
    val getAllFavState = _getAllFavState.asStateFlow()

    private val _getAllProductState = MutableStateFlow(GetAllProductsState())
    val getAllProductsState = _getAllProductState.asStateFlow()

    private val _getCartState = MutableStateFlow(GetCartState())
    val getCartState = _getCartState.asStateFlow()

    private val _getAllCategoriesState = MutableStateFlow(GetAllCategoriesState())
    val getAllCategoriesState = _getAllCategoriesState.asStateFlow()

    private val _getAllCheckoutState = MutableStateFlow(GetCheckoutState())
    val getAllCheckoutState = _getAllCheckoutState.asStateFlow()

    private val _getSpecificCategoryItemState = MutableStateFlow(GetSpecificCategoryItemsState())
    val getSpecificCategoryItemsState = _getSpecificCategoryItemState.asStateFlow()

    private val _getAllSuggestedProductsState = MutableStateFlow(GetAllSuggestedProductsState())
    val getAllSuggestedProductsState = _getAllSuggestedProductsState.asStateFlow()

    private val _homeScreenState = MutableStateFlow(HomeScreenState())
    val homeScreenState = _homeScreenState.asStateFlow()


    private val _getLowPrice = MutableStateFlow(GetLowPriceState())
    val getLowPrice = _getLowPrice.asStateFlow()




    fun getSpecificCategoryItems(categoryName: String) {                 //also rename the function

        viewModelScope.launch {
            getSpecificCategoryItemsUseCase.getSpecificCategoryItems(categoryName).collect {

                when (it) {
                    is ResultState.Error -> {
                        _getSpecificCategoryItemState.value =
                            _getSpecificCategoryItemState.value.copy(
                                isLoading = false,
                                error = it.message
                            )
                    }

                    is ResultState.Loading -> {
                        _getSpecificCategoryItemState.value =
                            _getSpecificCategoryItemState.value.copy(
                                isLoading = true
                            )
                    }

                    is ResultState.Success -> {
                        _getSpecificCategoryItemState.value =
                            _getSpecificCategoryItemState.value.copy(
                                isLoading = false,
                                userData = it.data
                            )
                    }
                }
            }
        }

    }


    fun getCheckOut(productId: String) {
        viewModelScope.launch {
            getCheckOutUseCase.getCheckOutUseCase(productId).collect {

                when (it) {
                    is ResultState.Error -> {
                        _getAllCheckoutState.value = _getAllCheckoutState.value.copy(
                            isLoading = false,
                            error = it.message
                        )

                    }

                    is ResultState.Loading -> {
                        _getAllCheckoutState.value = _getAllCheckoutState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _getAllCheckoutState.value = _getAllCheckoutState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }


                }

            }
        }
    }


    fun getAllCategories() {
        viewModelScope.launch {
            getAllCategoryUseCase.getAllCategoriesUseCase().collect {
                when (it) {

                    is ResultState.Error -> {
                        _getAllCategoriesState.value = _getAllCategoriesState.value.copy(
                            isLoading = false,
                            error = it.message
                        )

                    }

                    is ResultState.Loading -> {
                        _getAllCategoriesState.value = _getAllCategoriesState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _getAllCategoriesState.value = _getAllCategoriesState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )

                    }




                }

            }
        }
    }


    fun getCart() {
        viewModelScope.launch {
            getCartUseCase.getCart().collect {
                when (it) {
                    is ResultState.Error -> {
                        _getCartState.value = _getCartState.value.copy(
                            isLoading = false,
                            error = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _getCartState.value = _getCartState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _getCartState.value = _getCartState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }

                }

            }
        }
    }


    fun getAllProducts() {
        viewModelScope.launch {
            getAllProductUseCase.getAllProduct().collect {
                when (it) {
                    is ResultState.Error -> {
                        _getAllProductState.value = _getAllProductState.value.copy(
                            isLoading = false,
                            error = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _getAllProductState.value = _getAllProductState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _getAllProductState.value = _getAllProductState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }


                }

            }
        }
    }

    fun getAllFav() {
        viewModelScope.launch {
            getAllFavUseCase.getAllFav().collect {
                when (it) {
                    is ResultState.Error -> {
                        _getAllFavState.value = _getAllFavState.value.copy(
                            isLoading = false,
                            error = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _getAllFavState.value = _getAllFavState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _getAllFavState.value = _getAllFavState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }
        }
    }


    fun addToFav(productDataModels: ProductDataModels) {
        viewModelScope.launch {
            addToFavUseCase.addToFav(productDataModels).collect {
                when (it) {
                    is ResultState.Error -> {
                        _addToFavState.value = _addToFavState.value.copy(
                            isLoading = false,
                            error = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _addToFavState.value = _addToFavState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _addToFavState.value = _addToFavState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }
        }
    }

    fun removeFromFav(productDataModels: ProductDataModels) {
        viewModelScope.launch {
            removeFavUseCase.removeFavUseCase(productDataModels).collect { result ->
                when (result) {
                    is ResultState.Error -> {
                        _removeFav.value = _removeFav.value.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }

                    is ResultState.Loading -> {
                        _removeFav.value = _removeFav.value.copy(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _removeFav.value = _removeFav.value.copy(
                            isLoading = false,
                            userData = result.data
                        )
                        getAllFav()
                    }
                }
            }
        }
    }



    fun getProductById(productId: String) {
        viewModelScope.launch {
            getProductByIdUseCase.getProductById(productId).collect {
                when (it) {
                    is ResultState.Error -> {
                        _getProductByIDState.value = _getProductByIDState.value.copy(
                            isLoading = false,
                            error = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _getProductByIDState.value = _getProductByIDState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _getProductByIDState.value = _getProductByIDState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }
        }
    }


    fun addToCart(cartDataModels: CartDataModels) {
        viewModelScope.launch {
            addToCartUseCase.addToCart(cartDataModels).collect {
                when (it) {
                    is ResultState.Error -> {
                        _addToCartState.value = _addToCartState.value.copy(
                            isLoading = false,
                            error = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _addToCartState.value = _addToCartState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _addToCartState.value = _addToCartState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }
        }
    }

    fun removeFromCart(cartDataModels: CartDataModels) {
        viewModelScope.launch {
            removeCartUseCase.removeFromCart(cartDataModels).collect { result ->
                when (result) {
                    is ResultState.Error -> {
                        _removeCart.value = _removeCart.value.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                    is ResultState.Loading -> {
                        _removeCart.value = _removeCart.value.copy(isLoading = true)
                    }
                    is ResultState.Success -> {
                        _removeCart.value = _removeCart.value.copy(
                            isLoading = false,
                            userData = result.data
                        )
                        getCart() // refresh list after removal
                    }
                }
            }
        }
    }


    init {
        loadHomeScreenData()
    }

    fun loadHomeScreenData() {
        viewModelScope.launch {
            combine(
                getCategoryInLimit.getCategoryInLimited(),
                getProductsInLimitUseCase.getProductInLimit()
            ) { categoriesResult, productsResult ->
                when {
                    categoriesResult is ResultState.Error -> {
                        HomeScreenState(
                            isLoading = false,
                            errorMessage = categoriesResult.message
                        )
                    }

                    productsResult is ResultState.Error -> {
                        HomeScreenState(
                            isLoading = false,
                            errorMessage = productsResult.message
                        )
                    }

                    categoriesResult is ResultState.Success && productsResult is ResultState.Success -> {
                        HomeScreenState(
                            isLoading = false,
                            categories = categoriesResult.data,
                            products = productsResult.data
                        )
                    }

                    else -> {
                        HomeScreenState(isLoading = true)
                    }
                }
            }.collect { state ->
                _homeScreenState.value = state
            }
        }
    }


    fun uploadUserProfileImage(uri: Uri){
        viewModelScope.launch {

            userProfileImageUseCase.userProfileImage(uri).collect {
                when(it) {
                    is ResultState.Error -> {
                        _uploadUserProfileImageState.value = _uploadUserProfileImageState.value.copy(
                            isLoading = false,
                            error = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _uploadUserProfileImageState.value = _uploadUserProfileImageState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _uploadUserProfileImageState.value = _uploadUserProfileImageState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }
        }
    }


    fun updateUserData(userDataParent: UserDataParent){
        viewModelScope.launch {

            updateUserDataUseCase.updateUserData(userDataParent).collect {
                when (it) {
                    is ResultState.Error -> {
                        _updateScreenState.value = _updateScreenState.value.copy(
                            isLoading = false,
                            error = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _updateScreenState.value = _updateScreenState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _updateScreenState.value = _updateScreenState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }
        }
    }


    fun createUser(userData: UserData){
        viewModelScope.launch {

            createUserUseCase.createUser(userData).collect {
                when (it) {
                    is ResultState.Error -> {
                        _signUpScreenState.value =
                            _signUpScreenState.value.copy(
                                isLoading = false,
                                error = it.message
                            )
                    }

                    is ResultState.Loading -> {
                        _signUpScreenState.value =
                            _signUpScreenState.value.copy(
                                isLoading = true
                            )
                    }

                    is ResultState.Success -> {
                        _signUpScreenState.value =
                            _signUpScreenState.value.copy(
                                isLoading = false,
                                userData = it.data
                            )

                    }
                }
            }
        }
    }


    fun loginUser(userData: UserData) {
        viewModelScope.launch {

            loginUserUseCase.loginUser(userData).collect {
                when (it) {
                    is ResultState.Error -> {
                        _loginScreenState.value =
                            _loginScreenState.value.copy(
                                isLoading = false,
                                error = it.message
                            )
                    }

                    is ResultState.Loading -> {
                        _loginScreenState.value =
                            _loginScreenState.value.copy(
                                isLoading = true
                            )
                    }

                    is ResultState.Success -> {
                        _loginScreenState.value =
                            _loginScreenState.value.copy(
                                isLoading = false,
                                userData = it.data
                            )
                    }
                }
            }
        }
    }


    fun getUserById(uid: String){
        viewModelScope.launch {

            getUserUseCase.getUserById(uid).collect {
                when (it) {
                    is ResultState.Error -> {
                        _profileScreenState.value =
                            _profileScreenState.value.copy(
                                isLoading = false,
                                error = it.message
                            )
                    }

                    is ResultState.Loading -> {
                        _profileScreenState.value =
                            _profileScreenState.value.copy(
                                isLoading = true
                            )
                    }

                    is ResultState.Success -> {
                        _profileScreenState.value =
                            _profileScreenState.value.copy(
                                isLoading = false,
                                userData = it.data
                            )
                    }
                }
            }
        }
    }


    fun getAllSuggestedProducts(){
        viewModelScope.launch {

            getAllSuggestedProductsUseCase.getAllSuggestedProducts().collect {
                when (it) {
                    is ResultState.Error -> {
                        _getAllSuggestedProductsState.value =
                            _getAllSuggestedProductsState.value.copy(
                                isLoading = false,
                                error = it.message
                            )
                    }

                    is ResultState.Loading -> {
                        _getAllSuggestedProductsState.value =
                            _getAllSuggestedProductsState.value.copy(
                                isLoading = true
                            )
                    }

                    is ResultState.Success -> {
                        _getAllSuggestedProductsState.value =
                            _getAllSuggestedProductsState.value.copy(
                                isLoading = false,
                                userData = it.data
                            )
                    }
                }
            }
        }
    }


    fun getLowPriceItems(){
        viewModelScope.launch {

            getLowPriceUseCase.getlowPriceUseCase().collect {
                when(it) {
                    is ResultState.Error -> {
                        _getLowPrice.value = _getLowPrice.value.copy(
                            isLoading = false,
                            error = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _getLowPrice.value = _getLowPrice.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _getLowPrice.value = _getLowPrice.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }

                }
            }
        }
    }

    fun googleSignIn(idToken: String, userData: UserData) {
        viewModelScope.launch {
            googleSignInUseCase.GoogleSignInCase(idToken, userData).collect {
                when(it) {
                    is ResultState.Error ->  {
                        _googleSingIn.value = _googleSingIn.value.copy(
                            isLoading = false,
                            error = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _googleSingIn.value = _googleSingIn.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _googleSingIn.value = _googleSingIn.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }
        }


    }


}





















data class RemoveFavState(
    val isLoading: Boolean = false,
    val userData: String? = null,
    val error: String? = null
)



data class ProfileScreenState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val userData: UserDataParent? = null
)

data class SignUpScreenState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val userData: String? = null
)

data class LoginScreenState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val userData: String? = null
)

data class UpdateScreenState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val userData: String? = null
)

data class UploadUserProfileImageState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val userData: String? = null
)

data class AddToCartScreenState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val userData: String? = null
)

data class GetProductByIDState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val userData: ProductDataModels? = null
)

data class AddToFavState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val userData: String? = null
)

data class GetAllFavState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val userData: List<ProductDataModels?> = emptyList()
)

data class GetAllProductsState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val userData: List<ProductDataModels?> = emptyList()
)

data class GetLowPriceState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val userData: List<LowPriceDataModel?> = emptyList()
)

data class GetCartState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val userData: List<CartDataModels?> = emptyList()
)

data class GetAllCategoriesState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val userData: List<CategoryDataModels?> = emptyList()
)

data class GetCheckoutState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val userData: ProductDataModels? = null
)

data class GetSpecificCategoryItemsState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val userData: List<ProductDataModels?> = emptyList()
)

data class GetAllSuggestedProductsState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val userData: List<ProductDataModels?> = emptyList()
)





















package com.example.shopify.presentation.navigation

sealed class Routes(val route: String) {

    object Login : Routes("login")
    object SignUp : Routes("signup")
    object Home : Routes("home")
    object Category : Routes("category")
    object Mall : Routes("mall")
    object Orders : Routes("orders")
    object Profile : Routes("profile")
    object Wishlist : Routes("wishlist")
    object Cart : Routes("cart")
    object Search : Routes("search")
    object Pay : Routes("pay")
    object SeeAllProducts : Routes("see_all_products")
    object Shipping : Routes("shipping_address")

    data class EachCategoryProduct(val categoryName: String) :
        Routes("each_category_name/$categoryName") {
            companion object {
                const val route = "each_category_name/{categoryName}"
            }
        }





    data class ProductDetails(val productId: String) :
        Routes("product_details/$productId") {
        companion object {
            const val route = "product_details/{productId}"
        }
    }

    data class Checkout(val productId: String) :
        Routes("checkout/$productId") {
        companion object {
            const val route = "checkout/{productId}"
        }
    }
}

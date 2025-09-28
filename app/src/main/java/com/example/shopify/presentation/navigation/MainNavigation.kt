package com.example.shopify.presentation.navigation


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.shopify.R
import com.example.shopify.presentation.Screen.*
import com.google.firebase.auth.FirebaseAuth


data class BottomNavItem(val label: String, val icon: Painter, val unselectedIcon: Painter)

@Composable
fun App(firebaseAuth: FirebaseAuth, payTest: () -> Unit) {
    val navController = rememberNavController()
    var showBottomBar by remember { mutableStateOf(false) }

    // ✅ Track current destination
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var selectedItem by remember { mutableIntStateOf(0) }

    val bottomNavItems = listOf(
        BottomNavItem(
            label = "Home",
            icon = painterResource(id = R.drawable.outline_home_24),
            unselectedIcon = painterResource(id = R.drawable.outline_home_24)
        ),
        BottomNavItem(
            label = "Categories",
            icon = painterResource(id = R.drawable.outline_category_24),
            unselectedIcon = painterResource(id = R.drawable.outline_category_24)
        ),
        BottomNavItem(
            label = "Mall",
            icon = painterResource(id = R.drawable.outline_local_mall_24),
            unselectedIcon = painterResource(id = R.drawable.outline_local_mall_24)
        ),
        BottomNavItem(
            label = "My Orders",
            icon = painterResource(id = R.drawable.outline_orders_24),
            unselectedIcon = painterResource(id = R.drawable.outline_orders_24)
        ),
    )
    // ✅ Decide start screen
    val startScreen =
        if (firebaseAuth.currentUser == null) Routes.Login.route
        else Routes.Home.route

    // ✅ Show bottom bar only on main routes
    showBottomBar = currentRoute in listOf(
        Routes.Home.route,
        Routes.Category.route,
        Routes.Mall.route,
        Routes.Orders.route
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(containerColor = Color.White) {
                    bottomNavItems.forEachIndexed { index, navigationItem ->
                        NavigationBarItem(
                            selected = selectedItem == index,
                            onClick = {
                                selectedItem = index
                                when (index) {
                                0 -> navController.navigate(Routes.Home.route)
                                1 -> navController.navigate(Routes.Category.route)
                                2 -> navController.navigate(Routes.Mall.route)
                                3 -> navController.navigate(Routes.Orders.route)
                            }
                            },
                            icon = {
                                Icon(
                                    painter = navigationItem.icon,
                                    contentDescription = navigationItem.label
                                )
                            },
                            label = { Text(navigationItem.label) })
                    }
                }
            }
        }
            ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                NavHost(navController, startDestination = startScreen) {

                    composable(Routes.Login.route) { LoginScreen(navController = navController) }
                    composable(Routes.SignUp.route) {
                        SignUpScreen(
                            navController = navController
                        )
                    }

                    composable(Routes.Home.route) { HomeScreenUI(navController = navController) }
                    composable(Routes.Category.route) { GetAllCategories(navController = navController) }
                    composable(Routes.Mall.route) { MesshoMallScreenUI(navController = navController) }
                    composable(Routes.Orders.route) { MyOrderScreenUI(navController) }
                    composable(Routes.Profile.route) {
                        ProfileScreenUI(
                            firebaseAuth = firebaseAuth,
                            navController = navController
                        )
                    }
                    composable(Routes.Wishlist.route) { GetAllFav(navController = navController) }
                    composable(Routes.Cart.route) { CartScreen(navController = navController) }
                    composable(Routes.Search.route) { SearchBarScreen(navController) }
                    composable(Routes.Pay.route) { PayScreen(navController) }
                    composable(Routes.SeeAllProducts.route) { GetAllProductsScreen(navController = navController) }
                    composable(Routes.Shipping.route){ShippingAddressScreen(navController = navController)}

                    composable(Routes.EachCategoryProduct.route,
                        arguments = listOf(navArgument("categoryName"){
                            type = NavType.StringType
                        })
                    ){
                        val categoryName = it.arguments?.getString("categoryName") ?: ""
                        EachCategoryProductScreen(
                            navController = navController,
                            categoryName = categoryName

                        )

                    }

                    // ✅ Dynamic routes
                    composable(
                        Routes.ProductDetails.route,
                        arguments = listOf(navArgument("productId") { type = NavType.StringType })
                    ) {
                        val productId = it.arguments?.getString("productId") ?: ""
                        EachProductDetailsScreenUi(
                            navController = navController,
                            productID = productId
                        )
                    }

                    composable(
                        Routes.Checkout.route,
                        arguments = listOf(navArgument("productId") { type = NavType.StringType })
                    ) {
                        val productId = it.arguments?.getString("productId") ?: ""
                        CheckOutScreenUI(
                            productId = productId,
                            navController = navController,
                            pay = payTest
                        )
                    }
                }
            }
        }
        }

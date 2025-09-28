package com.example.shopify.presentation.Screen

import android.R.attr.onClick
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.shopify.R
import com.example.shopify.domain.models.ProductDataModels
import com.example.shopify.presentation.navigation.Routes
import com.example.shopify.presentation.viewModels.ShoppingAppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetAllFav(
    viewModel: ShoppingAppViewModel = hiltViewModel(),
    navController: NavController
) {
    val getAllFav = viewModel.getAllFavState.collectAsStateWithLifecycle()
    val removeFav = viewModel.removeFav.collectAsStateWithLifecycle()
    val getFavData: List<ProductDataModels> = getAllFav.value.userData.orEmpty().filterNotNull()

    LaunchedEffect(Unit) {
        viewModel.getAllFav()
    }

//    LaunchedEffect(removeFav.value.userData) {
//        if (removeFav.value.userData != null) {
//            viewModel.getAllFav() // refresh list after successful removal
//        }
//    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text(
                    text = "My Products",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )},
                navigationIcon = {
                    IconButton(
                        onClick = {navController.popBackStack()}
                    ){
                        Icon(
                            painterResource(R.drawable.baseline_arrow_back_24),
                            contentDescription = "Navigate to ProfileScreen",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate(Routes.Wishlist.route)
                        }
                    ) {
                        Icon(
                            Icons.Default.Favorite,
                            contentDescription = "Favorite"
                        )
                    }

                    IconButton(onClick = {}) {
                      Icon (
                          Icons.Default.Notifications,
                          contentDescription = "Notification",
                          modifier = Modifier.size(20.dp))

                    }

                    IconButton(onClick = {
                        navController.navigate(Routes.Cart.route)
                    }) {
                        Icon (
                            Icons.Default.ShoppingCart,
                            contentDescription = "Cart",
                            modifier = Modifier.size(20.dp))

                    }
                }

            )
        }
    ) { innerPadding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ){
            HorizontalDivider(
                color = Color.LightGray,
                thickness = 0.5.dp
            )

            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = {Text("Search")},
                leadingIcon = {Icon(Icons.Default.Search, contentDescription = null)}
            )

            when {
                getAllFav.value.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                getAllFav.value.error != null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(getAllFav.value.error!!)
                    }
                }

                getFavData.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Your wishList is empty")
                    }
                }
                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

                        items(getFavData) {product ->
                            ProductCard(product = product, onProductClick = {
                                navController.navigate(Routes.ProductDetails(product.productId).route)
                            },
                                onRemoveClick = {
                                    viewModel.removeFromFav(it)
                                })
                        }
                    }
                }

            }
        }
    }
}


@Composable
fun ProductCard(product: ProductDataModels, onProductClick: () -> Unit, onRemoveClick: (ProductDataModels) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onProductClick
    ) {
        Column {
            AsyncImage(
                model = product.image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Column (modifier = Modifier.padding(8.dp)){
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Rs ${product.price}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Button(
                    onClick = {onRemoveClick(product)},
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Remove", modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}

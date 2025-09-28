package com.example.shopify.presentation.Screen

import android.graphics.PathIterator
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter.State.Empty.painter
import com.example.shopify.R
import com.example.shopify.presentation.components.DetailedProductCard
import com.example.shopify.presentation.components.DetailedProductItem
import com.example.shopify.presentation.navigation.Routes
import com.example.shopify.presentation.viewModels.ShoppingAppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MesshoMallScreenUI(
    navController: NavController,
    viewModel: ShoppingAppViewModel = hiltViewModel()
) {
    val homeState by viewModel.homeScreenState.collectAsStateWithLifecycle()
    var selectedIndex by remember { mutableIntStateOf(0) }
    val options = listOf("Sort", "Category", "Gender", "Filters")

    val productItems = listOf(
        DetailedProductItem(
            image = R.drawable.kids,
            productName = "Kids Wear",
            productPrice = "289",
            discountedPrice = "248",
            discount = "20% Off",
            rating = "4.2",
            ratingCount = "(12,707)"
        ),

        DetailedProductItem(
            image = R.drawable.girl,
            productName = "kids Wear",
            productPrice = "289",
            discountedPrice = "248",
            discount = "20% Off",
            rating = "4.2",
            ratingCount = "(12,707)"
        ),

        DetailedProductItem(
            image = R.drawable.kurti,
            productName = "Kuti",
            productPrice = "289",
            discountedPrice = "248",
            discount = "20% Off",
            rating = "4.2",
            ratingCount = "(12,707)"
        ),
        DetailedProductItem(
            image = R.drawable.formal,
            productName = "Pant Shirt",
            productPrice = "289",
            discountedPrice = "248",
            discount = "20% Off",
            rating = "4.2",
            ratingCount = "(12,707)"
        ),
//        DetailedProductItem(
//            image = R.drawable.outline_boy_24,
//            productName = "Kids Wear",
//            productPrice = "289",
//            discountedPrice = "248",
//            discount = "20% Off",
//            rating = "4.2",
//            ratingCount = "(12,707)"
//        ),
//        DetailedProductItem(
//            image = R.drawable.outline_boy_24,
//            productName = "Kids Wear",
//            productPrice = "289",
//            discountedPrice = "248",
//            discount = "20% Off",
//            rating = "4.2",
//            ratingCount = "(12,707)"
//        ),
//        DetailedProductItem(
//            image = R.drawable.outline_boy_24,
//            productName = "Kids Wear",
//            productPrice = "289",
//            discountedPrice = "248",
//            discount = "20% Off",
//            rating = "4.2",
//            ratingCount = "(12,707)"
//        ),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "MEESHO MALL",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp)
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Routes.Search.route) }) {
                        Icon(Icons.Default.Search,
                            modifier = Modifier.size(26.dp),
                            contentDescription = "Search")
                    }

                    IconButton(onClick = {
                        navController.navigate(Routes.Wishlist.route)
                    }) {
                        Icon(
                            Icons.Default.Favorite,
                            modifier = Modifier.size(22.dp),
                            contentDescription = "Search"
                        )
                    }
                    IconButton(onClick = {
                        navController.navigate(Routes.Cart.route)
                    }) {
                        Icon(Icons.Default.ShoppingCart,
                            contentDescription = "Cart",
                            modifier = Modifier.size(22.dp))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(Color.White)
            )
        }
    ) { innerPadding->
        LazyColumn (modifier = Modifier.fillMaxSize().padding(innerPadding)){

            item{
                Column (modifier = Modifier.fillMaxWidth().background(Color.LightGray)){
                    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        Text("Budget Buys",
                            fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.weight(1f))
                        Text(text = "View All", color = Color.Red)
                        IconButton(
                            onClick = {}
                        ) {
                            painter?.let {
                                Icon(
                                    it,
                                    modifier = Modifier.size(16.dp).background(color = Color.Red, shape = CircleShape).clip(CircleShape),
                                    contentDescription = "View All Products"
                                )
                            }
                        }
                    }

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 0.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(productItems) {product->
                            DetailedProductCard(
                                product = product,
                                modifier = Modifier.size(160.dp)
                            )
                        }
                    }
                }
            }
            item {
                SingleChoiceSegmentedButtonRow {
                    options.forEachIndexed { index, label ->
                        SegmentedButton(
                            modifier = Modifier.background(color = Color.White),
                            shape = SegmentedButtonDefaults.itemShape(
                                baseShape = RoundedCornerShape(0.dp),
                                index = index,
                                count = options.size
                            ),
                            onClick = {selectedIndex = index},
                            selected = index == selectedIndex,
                            label = {Text(text = label)}
                        )
                    }
                }
            }
            items(productItems.chunked(2)) {rowItems ->

                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    rowItems.forEach { product->
                        DetailedProductCard(
                            product,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    if(rowItems.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }

        }
    }
}
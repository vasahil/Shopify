package com.example.shopify.presentation.Screen

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.shopify.R
import com.example.shopify.domain.models.ProductDataModels
import com.example.shopify.domain.models.UserData
import com.example.shopify.presentation.components.DetailedProductCard
import com.example.shopify.presentation.components.DetailedProductItem
import com.example.shopify.presentation.components.LowPriceCard
import com.example.shopify.presentation.components.SearchBarComponentForHomeScreen
import com.example.shopify.presentation.components.TopBarComponent
import com.example.shopify.presentation.navigation.Routes
import com.example.shopify.presentation.viewModels.ShoppingAppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenUI(
    viewModel: ShoppingAppViewModel = hiltViewModel(),
    navController: NavController
) {
    val homeState by viewModel.homeScreenState.collectAsStateWithLifecycle()

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        snapAnimationSpec = spring(
            stiffness = Spring.StiffnessMedium
        )
    )

    val listState = rememberLazyListState()

    var selectedIndex by remember { mutableIntStateOf(0) }
    val options = listOf("Sort", "Category", "Gender", "Filters")

    val getAllSuggestedProduct =
        viewModel.getAllSuggestedProductsState.collectAsStateWithLifecycle()
    val getSuggestedProductData: List<ProductDataModels> =
        getAllSuggestedProduct.value.userData.orEmpty().filterNotNull()

    val userData = viewModel.signUpScreenState.collectAsStateWithLifecycle().value.userData

    val lowPrice = viewModel.getLowPrice.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getAllSuggestedProducts()
    }

    if (homeState.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    } else if (homeState.errorMessage != null) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(text = homeState.errorMessage!!)
        }
    } else {


        val productItems = listOf(
            DetailedProductItem(
                image = R.drawable.kids,
                productName = "Kids Boy",
                productPrice = "289",
                discountedPrice = "248",
                discount = "20% Off",
                rating = "4.2",
                ratingCount = "(12,707)"
            ),

            DetailedProductItem(
                image = R.drawable.girl,
                productName = "Kids Girl",
                productPrice = "289",
                discountedPrice = "248",
                discount = "20% Off",
                rating = "4.2",
                ratingCount = "(12,707)"
            ),

            DetailedProductItem(
                image = R.drawable.saree,
                productName = "Women Saree",
                productPrice = "289",
                discountedPrice = "248",
                discount = "20% Off",
                rating = "4.2",
                ratingCount = "(12,707)"
            ),
            DetailedProductItem(
                image = R.drawable.jeans,
                productName = "Jeans",
                productPrice = "289",
                discountedPrice = "248",
                discount = "20% Off",
                rating = "4.2",
                ratingCount = "(12,707)"
            ),
            DetailedProductItem(
                image = R.drawable.kurta,
                productName = "kurta for Men",
                productPrice = "289",
                discountedPrice = "248",
                discount = "20% Off",
                rating = "4.2",
                ratingCount = "(12,707)"
            ),
            DetailedProductItem(
                image = R.drawable.kurti,
                productName = "kurti for girls",
                productPrice = "289",
                discountedPrice = "248",
                discount = "20% Off",
                rating = "4.2",
                ratingCount = "(12,707)"
            ),
            DetailedProductItem(
                image = R.drawable.denium1,
                productName = "Denium Jacket",
                productPrice = "289",
                discountedPrice = "248",
                discount = "20% Off",
                rating = "4.2",
                ratingCount = "(12,707)"
            ),
        )

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    TopBarComponent(scrollBehavior, navController, userData as UserData?)
                    SearchBarComponentForHomeScreen(navController)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        ) { innerPadding ->

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .background(color = Color.White)
                    .padding(innerPadding)
            ) {
                item {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        item {
                            ViewAllCategoryItem(
                                onClick = {
                                    navController.navigate(Routes.Category.route)
                                }
                            )
                        }

                        items(
                            homeState.categories ?: emptyList()
                        ) { category ->
                            CategoryItem(
                                ImageUrl = category.categoryImage,
                                Category = category.name,
                                onClick = {
                                    navController.navigate(
                                        Routes.EachCategoryProduct(categoryName = category.name).route
                                    )
                                }
                            )
                        }
                    }
                }

                item {
                    //    HorizontalCarousel()

                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Flash Sale",
                                fontWeight = FontWeight.Bold,
                                color = Color.DarkGray,
                                fontSize = 20.sp
                            )

                            Text(
                                text = "See more",
                                fontWeight = FontWeight.Bold,
                                color = colorResource(R.color.teal_700),
                                modifier = Modifier.clickable {
                                    navController.navigate(Routes.SeeAllProducts.route)
                                }
                            )
                        }

                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(homeState.products ?: emptyList()) { product ->
                                productCard(
                                    product = product,
                                    navController = navController
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    LaunchedEffect(Unit) {
                        viewModel.getLowPriceItems()
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 16.dp)

                    ) {
                        Text(
                            "Low Price Store",
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray,
                            fontSize = 20.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(horizontal = 0.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(lowPrice.value.userData.filterNotNull()) { cardData ->
                                LowPriceCard(
                                    cardData = cardData
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
                                onClick = { selectedIndex = index },
                                selected = index == selectedIndex,
                                label = { Text(text = label) }
                            )
                        }
                    }
                }



                items(productItems.chunked(2)) { rowItems ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowItems.forEach { product ->
                            DetailedProductCard(
                                product,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        if (rowItems.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                        } } }

                    }
                    }
                }
            }


            @Composable
            fun CategoryItem(
                ImageUrl: String,
                Category: String,
                onClick: () -> Unit
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .clickable {
                            onClick()
                        }) {
                    Box(modifier = Modifier
                        .size(60.dp)
                        .background(Color.LightGray, CircleShape)) {
                        AsyncImage(
                            model = ImageUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                        )
                    }

                    Text(Category, style = MaterialTheme.typography.bodyMedium)
                }
            }

            @Composable
            fun ViewAllCategoryItem(
                onClick: () -> Unit
            ) {
                Column(
                    modifier = Modifier.clickable(onClick = onClick),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(color = colorResource(R.color.teal_200)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.outline_category_24),
                            contentDescription = "View All",
                            modifier = Modifier.padding(8.dp),
                            tint = Color.Unspecified
                        )
                    }

                    Text("All", style = MaterialTheme.typography.bodyMedium)
                }
            }

            @Composable
            fun productCard(product: ProductDataModels, navController: NavController) {
                Card(
                    modifier = Modifier
                        .width(150.dp)
                        .clickable {
                            navController.navigate(Routes.ProductDetails(productId = product.productId).route)
                        }
                        .aspectRatio(0.7f),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column {
                        AsyncImage(
                            model = product.image,
                            contentDescription = null,

                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .width(100.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .aspectRatio(1f),
                            contentScale = ContentScale.Crop,
                        )

                        Column(modifier = Modifier.padding(8.dp))
                        {
                            Text(
                                product.name, maxLines = 1,
                                style = MaterialTheme.typography.bodyMedium,
                                overflow = TextOverflow.Ellipsis
                            )

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    "$${product.finalPrice}",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    "$${product.price}",
                                    style = MaterialTheme.typography.bodySmall,
                                    textDecoration = TextDecoration.LineThrough,
                                    color = Color.Gray
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(
                                    "$${product.availableUnits}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            }

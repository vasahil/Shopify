package com.example.shopify.presentation.Screen

import android.graphics.Paint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.shopify.R
import com.example.shopify.presentation.components.CategoryProductCard
import com.example.shopify.presentation.components.CategoryProductItem
import com.example.shopify.presentation.navigation.Routes
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select

data class Category(
    val name: String,
    val iconRes: Int

)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(navController: NavController) {

    val categories = listOf(
        Category("Popular", R.drawable.outline_woman_24),
        Category("Kurti", R.drawable.outline_woman_24),
        Category("Women Western", R.drawable.outline_woman_24),
        Category("Men", R.drawable.outline_boy_24),
        Category("Women", R.drawable.outline_woman_24),
        Category("Kids", R.drawable.outline_boy_24),
        Category("Seasonal", R.drawable.outline_woman_24),
        Category("Home & Kitchen", R.drawable.outline_woman_24),
        Category("Perfume", R.drawable.outline_woman_24),
        Category("Shirts", R.drawable.outline_boy_24)
    )


    val allProducts = listOf(
        CategoryProductItem(R.drawable.outline_boy_24, "Kids wear", "Popular"),
        CategoryProductItem(R.drawable.outline_boy_24, "Girls wear", "Popular"),
        CategoryProductItem(R.drawable.outline_boy_24, "Mens wear", "Men"),
        CategoryProductItem(R.drawable.outline_boy_24, "Kurtis & Dress", "Kurti, Saree & Lehenge"),
        CategoryProductItem(R.drawable.outline_boy_24, "Women Suits", "Kurti, Saree & Lehenge"),
        CategoryProductItem(R.drawable.outline_boy_24, "Wedding Dress", "Kurti, Saree & Lehenge"),
        CategoryProductItem(R.drawable.outline_boy_24, "Party wear", "Kurti, Saree & Lehenge"),
        CategoryProductItem(R.drawable.outline_boy_24, "Engagement", "Kurti, Saree & Lehenge"),
        CategoryProductItem(R.drawable.outline_boy_24, "jeans", "Women Western"),
        CategoryProductItem(R.drawable.outline_boy_24, "Shirt", "Men"),
        CategoryProductItem(R.drawable.outline_boy_24, "Saree", "Women"),
        CategoryProductItem(R.drawable.outline_boy_24, "Shirts & Pants", "kids"),
        CategoryProductItem(R.drawable.outline_boy_24, "Summer", "Seasonal"),
        CategoryProductItem(R.drawable.outline_boy_24, "Mixer", "Home & Kitchen"),
        CategoryProductItem(R.drawable.outline_boy_24, "Stove", "Home & Kitchen"),
        CategoryProductItem(R.drawable.outline_boy_24, "Kurta's", "Kurta"),
        CategoryProductItem(R.drawable.outline_boy_24, "Perfumes", "Perfume"),
        CategoryProductItem(R.drawable.outline_boy_24, "Shirts", "Shirts"),

        )


    val categoryIndexMap = mutableMapOf<String, Int>()
    var itemIndex = 0

    val sections = categories.map { category ->
        categoryIndexMap[category.name] = itemIndex
        val filteredProducts = allProducts.filter { it.categoryName == category.name }
        itemIndex += 2
        CategorySection(category = category, products = filteredProducts)
    }

    var selectedCategory by remember { mutableStateOf(categories[0]) }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(selectedCategory) {
        categoryIndexMap[selectedCategory.name]?.let { index ->
            coroutineScope.launch {
                listState.animateScrollToItem(index)
            }
        }
    }


    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = {
                    Text("CATEGORIES", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Routes.Search.route) }) {
                        Icon(
                            Icons.Default.Search,
                            modifier = Modifier.size(26.dp),
                            contentDescription = "Search"
                        )
                    }

                    IconButton(onClick = {
                        navController.navigate(Routes.Wishlist.route)
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.outline_heart_plus_24),
                            contentDescription = "Favourite",
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    IconButton(onClick = { navController.navigate(Routes.Cart.route) }) {
                        Icon(
                            painter = painterResource(R.drawable.outline_shopping_cart_24),
                            contentDescription = "Cart",
                            modifier = Modifier.size(22.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(Color.White)
            )
        }
    ) { innerPadding ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            CategorySidebar(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it }
            )

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(8.dp, bottom = 16.dp)
            ) {
                sections.forEach { section ->
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = section.category.name,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )

                            HorizontalDivider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 12.dp),
                                thickness = 1.5.dp, color = colorResource(R.color.teal_700)
                            )
                        }
                    }
                    item {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3),
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 100.dp, max = 500.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            userScrollEnabled = false
                        ) {
                            this@LazyVerticalGrid.items(section.products) { product ->
                                CategoryProductCard(product = product)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CategorySidebar(
    categories: List<Category>,
    selectedCategory: Category,
    onCategorySelected: (Category) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(bottom = 12.dp)
            .shadow(elevation =  4.dp)
            .width(80.dp)
            .fillMaxHeight()
            .background(Color.White)
    ) {
        items(categories){category ->
            CategoryItem(
                category = category,
                isSelected = category == selectedCategory,
                onClick = {onCategorySelected(category)}
            )
        }
    }
}


@Composable
fun CategoryItem(
    category: Category,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth()
            .background(Color.White)
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ){

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ){
            Box(
                modifier = Modifier.size(50.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF0F0F0)),
                contentAlignment = Alignment.Center
            ){
                Image(
                    painter = painterResource(id = category.iconRes),
                    contentDescription = category.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = category.name,
                textAlign = TextAlign.Center,
                fontWeight = if(isSelected) FontWeight.Bold else FontWeight.Normal,
                fontSize = 10.sp,
                color = Color.DarkGray,
                maxLines = 2,
                lineHeight = 14.sp,
                overflow = TextOverflow.Ellipsis
            )
        }

        if(isSelected){
            Box(
                modifier = Modifier.width(4.dp)
                    .height(4.dp)
                    .background(
                        color = Color(0xFF28942B),
                        shape = RoundedCornerShape(topStart = 50.dp, bottomStart = 50.dp)
                    )
            )
        }else {
            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}


data class CategorySection(
    val category: Category,
    val products: List<CategoryProductItem>
)
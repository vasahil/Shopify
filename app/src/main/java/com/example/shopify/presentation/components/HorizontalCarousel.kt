package com.example.shopify.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.shopify.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HorizontalCarousel(){

    data class CarouselItem(
        val id: Int,
        @DrawableRes val imageResId: Int,
        val contentDescription: String
    )

    val items = listOf(
        CarouselItem(1, R.drawable.outline_view_carousel_24, "Profile image"),
        CarouselItem(2, R.drawable.outline_view_carousel_24, "Another profile image"),
        CarouselItem(3, R.drawable.outline_view_carousel_24, "Boy image 1"),
        CarouselItem(4, R.drawable.outline_view_carousel_24, "Boy image 2"),

    )

    val state = rememberCarouselState { items.count() }

    HorizontalMultiBrowseCarousel(
        state = state,
        modifier = Modifier.width(412.dp)
            .height(221.dp),
        preferredItemWidth = 186.dp,
        itemSpacing = 8.dp,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ){i ->
        val item = items[i]
        Image(modifier = Modifier.height(205.dp).clip(MaterialTheme.shapes.extraLarge),
            painter = painterResource(id = item.imageResId),
            contentDescription = item.contentDescription,
            contentScale = ContentScale.Crop
        )

    }
}
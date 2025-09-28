package com.example.shopify.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.serialization.internal.throwMissingFieldException

@Composable
fun OutlinedCardForSearchScreen(product: String) {
    OutlinedCard (shape = RoundedCornerShape(24.dp)){
        Text(text = product, fontSize = 14.sp, modifier =  Modifier.padding(8.dp))
    }
}
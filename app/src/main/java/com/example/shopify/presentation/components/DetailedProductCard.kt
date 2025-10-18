package com.example.shopify.presentation.components

import android.view.RoundedCorner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopify.R

data class DetailedProductItem(
    val image: Int,
    val productName: String,
    val productPrice: String,
    val discountedPrice: String,
    val discount: String,
    val rating: String,
    val ratingCount: String
)
@Composable
fun DetailedProductCard(
    product: DetailedProductItem,
    modifier: Modifier = Modifier
){

    Card(modifier = Modifier.background(color = Color.White), shape = RoundedCornerShape(0.dp)) {

        Column (modifier = Modifier.fillMaxWidth().background(color = Color.White)){
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(product.image),
                    contentDescription = "Product Image")

                IconButton(onClick = {}, modifier = Modifier.padding(8.dp).background(color = colorResource(
                    R.color.teal_200), shape = CircleShape
                ). align(Alignment.TopEnd)) {
                    Icon(painter = painterResource(R.drawable.outline_heart_plus_24), modifier = Modifier.padding(6.dp), contentDescription = null)
                }
            }

            Column (modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 8.dp)){
                Text(text = product.productName, color = Color.DarkGray, fontSize = 12.sp)

                Spacer(modifier = Modifier.height(6.dp))
                Row (modifier = modifier.fillMaxWidth()){
                    Text(text = product.discountedPrice, color = Color.Black, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    Spacer(modifier = Modifier.width(4.dp))

                    Text(text = product.productPrice, fontSize = 14.sp, color = Color.Gray, textDecoration = TextDecoration.LineThrough)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = product.discount, color = Color.DarkGray, fontSize = 14.sp)
                }

                Spacer(modifier = Modifier.height(6.dp))
                Box(modifier = Modifier.background(
                    color = Color.Green.copy(alpha = 0.2f)
                )){
                    Row(modifier = Modifier.padding(1.dp), verticalAlignment = Alignment.CenterVertically){
                        Text(text = "Discount applied", fontSize = 10.sp)
                        Spacer(modifier = Modifier.width(4.dp))

                        Icon(painter = painterResource(R.drawable.outline_heart_plus_24), contentDescription = null, modifier = Modifier.size(8.dp))
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))
                Text(text = "Free Delivery", fontSize = 12.sp)
                Spacer(modifier = Modifier.height(6.dp))
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp), verticalAlignment = Alignment.CenterVertically){
                    Card(modifier = Modifier.size(width = 40.dp, height = 22.dp), shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(colorResource(R.color.purple_200)),
                        ){
                        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                            Text(text = product.rating,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold, color = Color.White)
                            Icon(painterResource(R.drawable.outline_star_24), modifier = Modifier.size(10.dp), tint = Color.White, contentDescription = null)
                        }
                    }

                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = product.ratingCount, fontSize = 14.sp, fontWeight = FontWeight.Light)
                }
            }
        }
    }

}
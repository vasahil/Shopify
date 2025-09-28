package com.example.shopify.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.shopify.R
import com.example.shopify.domain.models.UserData
import com.example.shopify.presentation.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarComponent(scrollBehavior: TopAppBarScrollBehavior, navController: NavController, userData: UserData?){

   val userName = userData?.firstName?:"User"
    TopAppBar(
        modifier = Modifier.fillMaxWidth().background(color = Color.White),
        title = {
            Row (horizontalArrangement = Arrangement.Start){
                Text("Hello, ",
                    color = Color.Black,
                    fontSize = 18.sp, lineHeight = 16.sp)

                Text(userName,
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold, lineHeight = 16.sp)
            }
        },
        navigationIcon = {
            IconButton(onClick = {
                navController.navigate(Routes.Profile.route)
            }) {
                Icon(
                    Icons.Default.Person,
                    tint = Color.Unspecified,
                    contentDescription = "Navigate to Profile Screen",
                    modifier = Modifier.size(34.dp)

                )
            }
        },
        actions = {
            IconButton(onClick = {
                navController.navigate(Routes.Wishlist.route)
            }) {
                Icon(
                    painterResource(R.drawable.outline_heart_plus_24),
                    contentDescription = "Favourite",
                    modifier  =Modifier.size(22.dp)
                )
            }

            IconButton(onClick = {}) {
                Icon(
                    painterResource(R.drawable.outline_notifications_24),
                    contentDescription = "Notification",
                    modifier = Modifier.size(22.dp)
                )
            }
            IconButton(onClick = {
                navController.navigate(Routes.Cart.route)
            }) {
                Icon(painterResource(R.drawable.outline_shopping_cart_24),
                    contentDescription = "Cart",
                    modifier = Modifier.size(22.dp))

            }
        },
        scrollBehavior = scrollBehavior
    )
}
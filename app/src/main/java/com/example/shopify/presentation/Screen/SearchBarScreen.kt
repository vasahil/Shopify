package com.example.shopify.presentation.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.shopify.R
import com.example.shopify.presentation.components.OutlinedCardForSearchScreen
import com.example.shopify.presentation.components.SearchBarComponentForSearchScreen

@Composable
fun SearchBarScreen(
    navController: NavController
){
    Scaffold (
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            Row (
                modifier = Modifier.fillMaxWidth().background(color = Color.White),
                verticalAlignment = Alignment.CenterVertically
            ){
                IconButton(
                    onClick=  {navController.popBackStack()}
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_arrow_back_24),
                        contentDescription = null
                    )
                }
                SearchBarComponentForSearchScreen()
            }
        }
    ){ innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding)
        ) {
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 4.dp),
                color = Color.LightGray,
                thickness = 0.5.dp
            )

            Column (modifier = Modifier.fillMaxWidth().background(Color.White)){
                Text("Popular Searches",
                    modifier = Modifier.padding(horizontal = 12.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold)

                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp).padding(top = 10.dp)
                ) {
                    OutlinedCardForSearchScreen(product = "saree")
                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedCardForSearchScreen(product = "kurti")
                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedCardForSearchScreen(product = "short kurti")
                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedCardForSearchScreen(product = "tshirt")
                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedCardForSearchScreen(product = "top for women")

                }
                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp)) {
                    OutlinedCardForSearchScreen(product = "kurti set")
                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedCardForSearchScreen(product = "watch")
                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedCardForSearchScreen(product = "earing")
                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedCardForSearchScreen(product = "top")
                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedCardForSearchScreen(product = "shoes")

                }

                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp)
                ){
                    OutlinedCardForSearchScreen(product = "water bottle")
                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedCardForSearchScreen(product = "slipper")
                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedCardForSearchScreen(product = "bangle")
                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedCardForSearchScreen(product = "tshirt for women")

                }

                Image(painter = painterResource(R.drawable.baseline_search_24),
                    contentDescription = null)
            }
        }
    }
}


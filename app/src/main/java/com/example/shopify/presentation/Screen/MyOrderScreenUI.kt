package com.example.shopify.presentation.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shopify.R
import com.example.shopify.presentation.navigation.Routes
import com.example.shopify.presentation.viewModels.ShoppingAppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyOrderScreenUI(
    navController: NavController
) {

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "My Orders",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold)
                }
            )
        }
    ){ innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding)
        ) {
            HorizontalDivider(
                color = Color.LightGray,
                thickness = 0.5.dp
            )
            Row (modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
                ){

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.padding(vertical = 10.dp),
                    colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color.LightGray),
                    placeholder = {Text("Search")},
                    leadingIcon = {Icon(Icons.Default.Search, contentDescription = null)}
                )

                Row (modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                    ){
                    Icon(
                        painter = painterResource(R.drawable.outline_filter_alt_24),
                        contentDescription = "Filter Icon",
                        modifier = Modifier.align(Alignment.CenterVertically).size(24.dp),
                        tint = colorResource(R.color.teal_700)
                    )

                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Filters",
                        fontSize = 16.sp,
                        color = colorResource(R.color.teal_700),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(50.dp))
            Column (
                modifier= Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                Image(painter = painterResource(R.drawable.outline_package_2_24),
                    contentDescription = "Package")

                Text(
                    text = "No order Yet",
                    modifier = Modifier.padding(vertical = 10.dp),
                    fontSize = 26.sp,
                    color = colorResource(R.color.teal_700),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


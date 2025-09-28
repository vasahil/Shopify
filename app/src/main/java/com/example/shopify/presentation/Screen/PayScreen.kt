package com.example.shopify.presentation.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.shopify.R
import com.example.shopify.presentation.Utils.OrderPlacedDialog
import com.google.firestore.v1.StructuredQuery

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PayScreen(navController: NavController) {
    var showOrderDialog by remember { mutableStateOf(false) }

    Scaffold (
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
                title = {Text(text = "Review Your Order",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold)},
                navigationIcon = {
                    IconButton(
                        onClick = {navController.popBackStack()}
                    ) {
                        Icon(

                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                }
            )
        },
        bottomBar = {
            HorizontalDivider(
                color = Color.LightGray,
                thickness = 0.5.dp
            )
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Column (){
                    Text("Rs. 9401", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("View Price Details", fontWeight = FontWeight.SemiBold, color = colorResource(
                        R.color.purple_200))
                }

                Card (
                    shape = RoundedCornerShape(4.dp),
                    colors = CardDefaults.cardColors(colorResource(R.color.teal_200)),
                    onClick = {
                        showOrderDialog = true
                    }
                ){
                    Text("Place Order",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(12.dp),
                        color = Color.White)
                }
            }
            if(showOrderDialog){
                OrderPlacedDialog(
                    onDismiss = {showOrderDialog = false},
                    onConfirm = {
                        showOrderDialog = false
                    }
                )
            }
        }
    ){ innerPadding ->

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ){

            HorizontalDivider(
                color = Color.LightGray,
                thickness = 0.5.dp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column (modifier = Modifier.fillMaxWidth().background(color = Color.White).padding(vertical = 8.dp)){
                Row (modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)){
                    Box(modifier = Modifier.size(150.dp, 150.dp).padding(horizontal = 12.dp)) {

                        Image(painter = painterResource(R.drawable.outline_woman_24),
                            contentDescription = "Women image")
                    }

                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text("Suits Light full Neck",
                            fontSize = 18.sp, fontWeight = FontWeight.SemiBold)

                        Spacer(modifier = Modifier.height(6.dp))

                        Text("No Returns-only Exchange",
                            fontSize = 16.sp, color = Color.Gray)

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(){
                            Text("Size: M", fontSize = 16.sp, color = Color.Gray)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Qty: 1", fontSize = 16.sp, color = Color.Gray)

                        }
                    }
                }

                HorizontalDivider(
                    color = Color.LightGray,
                    thickness = 0.5.dp
                )
                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)) {
                    Text("sold by: ", color = Color.Gray)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Growth Textile Private Limited", color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier.fillMaxWidth()
                    .background(color = Color.White)
                    .padding(vertical = 8.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text("Price Details", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Icon(painter = painterResource(R.drawable.baseline_arrow_back_24),tint = Color.Black,
                        contentDescription = null)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text("Total Product Price", color = Color.Gray, fontSize = 14.sp)
                    Text("+ Rs.10000", color = Color.Gray, fontSize = 14.sp)
                }

                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text("Total Discount", color = Color.Green, fontSize = 14.sp)
                    Text("- Rs.599", color = Color.Gray, fontSize = 14.sp)
                }

                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                    color = Color.Gray,
                    thickness = 0.5.dp
                )

                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text("Order Total", fontSize= 16.sp, fontWeight = FontWeight.Bold)
                    Text("Rs. 9401", fontSize= 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
package com.example.shopify.presentation.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.shopify.R
import com.example.shopify.domain.models.CartDataModels
import com.example.shopify.presentation.navigation.Routes
import com.example.shopify.presentation.viewModels.ShoppingAppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    viewModel: ShoppingAppViewModel = hiltViewModel(),
    navController: NavController
) {
    val cartState = viewModel.getCartState.collectAsStateWithLifecycle()
    val cartData = cartState.value.userData
    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    LaunchedEffect(key1 = Unit) {
        viewModel.getCart()
    }

    Scaffold (
        modifier = Modifier.fillMaxSize().nestedScroll(scrollBehaviour.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {Text ( text = "Shopping Cart", fontSize = 16.sp, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)},

                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack()}){
                        Icon(painter = painterResource(R.drawable.baseline_arrow_back_24), contentDescription = "Back")
                    }
                },
                scrollBehavior = scrollBehaviour
            )
        },
    ){ innerPadding->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {

            when{
                cartState.value.isLoading-> {
                    Box(modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center)
                    {
                        CircularProgressIndicator()
                    }
                }
                cartState.value.error != null ->{
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Spacer(modifier = Modifier.padding(8.dp))
                        Text("Sorry, Unable to Get Information")
                    }
                }
                cartData.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No Products Available")
                    }
                }
                else -> {
                    Column (
                        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
                    ){

                        Row(
                            modifier = Modifier.padding(vertical = 8.dp)
                        ){
                            Text(text = "Items", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.weight(.45f))
                            Text("Details", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)

                            Spacer(modifier = Modifier.weight(1f))
                            Text("QTY", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)

                            Spacer(modifier = Modifier.weight(.15f))
                        }

                        LazyColumn(
                            modifier = Modifier.weight(.6f)
                        ){
                            items(cartData) { item ->
                                CartItemCard(item = item!!,
                                    onRemoveClick = { viewModel.removeFromCart(it) },
                                    onBuyNow = {navController.navigate(Routes.Shipping.route)}
                                   // onBuyNowClick = { navController.navigate(Routes.ShippingAddress.route) }
                               )
                            }
                        }

                        HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))
                    }

                    Button(
                        onClick = {/* Handle Checkout*/},
                        modifier = Modifier.fillMaxWidth().padding(bottom = 5.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.purple_200))

                    ) {
                        Text("Checkout")
                    }
            }

        }
        }
    }
}

@Composable
fun CartItemCard(item: CartDataModels, onRemoveClick: (CartDataModels) -> Unit, onBuyNow: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp)
    ){
        Row(modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = item.image,
                contentDescription = item.name,
                modifier = Modifier.size(80.dp).clip(RoundedCornerShape(8.dp))
            )

            Column (
                modifier = Modifier.weight(1f).padding(16.dp)
            ){
                Text(item.name, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                Text("Size: ${item.size}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("Rs ${item.price}", style =  MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
            }

            Column (horizontalAlignment = Alignment.End){
                Text("QTY: ${item.quantity}", style = MaterialTheme.typography.bodyMedium)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Action buttons
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 5.dp).size(30.dp),
            horizontalArrangement = Arrangement.Center) {
            Button(
                onClick = { onRemoveClick(item) },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("Remove")
            }

            Button(
                onClick = { onBuyNow() },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Buy Now")
            }
        }
    }
}
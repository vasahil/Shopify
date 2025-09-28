package com.example.shopify.presentation.Screen


import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.shopify.R
import com.example.shopify.presentation.navigation.Routes
import com.example.shopify.presentation.viewModels.ShoppingAppViewModel
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckOutScreenUI(
    viewModel: ShoppingAppViewModel = hiltViewModel(),
    navController: NavController,
    productId: String,
    pay: () -> Unit
) {
    val state = viewModel.getProductByIDState.collectAsStateWithLifecycle()
    val productData = state.value.userData

    val email = remember { mutableStateOf("") }
    val country = remember { mutableStateOf("") }
    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }
    val city = remember { mutableStateOf("") }
    val postalCode = remember { mutableStateOf("") }
    val selectedMethod = remember { mutableStateOf("Standard FREE Delivery over Rs. 4500") }

    LaunchedEffect(Unit) {
        viewModel.getProductById(productId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "SHIPPING", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)},
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack()}) {
                        Icon(painter = painterResource(R.drawable.baseline_arrow_back_24), contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->

        when{
            state.value.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.value.error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text("Sorry, Unable to Get Information")
                }
            }

            state.value.userData == null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No Product Available")
                }
            }
            else -> {

                Column (
                    modifier = Modifier.fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ){

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(
                            model = state.value.userData?.image, // safer null check
                            contentDescription = "Profile picture",
                            modifier = Modifier
                                .size(80.dp)
                                .border(1.dp,Color.Gray, shape = CircleShape), // optional shape
                            contentScale = ContentScale.Crop // ensures image is cropped nicely
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = state.value.userData!!.name,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = "$${state.value.userData!!.finalPrice}",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Column{
                        Text("Contact Information", style = MaterialTheme.typography.headlineSmall)
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = email.value,
                            onValueChange = {email.value = it},
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Email")},
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Column {
                        Text("Shipping Address", style = MaterialTheme.typography.headlineSmall)
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = country.value,
                            onValueChange = {country.value = it},
                            modifier = Modifier.fillMaxWidth(),
                            label = {Text("Country/Region")}
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        Row{
                            OutlinedTextField(
                                value = firstName.value,
                                onValueChange = {firstName.value = it},
                                modifier = Modifier.weight(1f)
                                    .padding(end = 8.dp),
                                label = {Text("First Name")}
                            )

                            OutlinedTextField(
                                value = lastName.value,
                                onValueChange = {lastName.value = it},
                                modifier = Modifier.weight(1f),
                                label = {Text("Last Name")}
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = address.value,
                            onValueChange = {address.value = it},
                            modifier = Modifier.weight(1f),
                            label = {Text("Address")}
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        Row{
                            OutlinedTextField(
                                value = city.value,
                                onValueChange = {city.value = it},
                                modifier = Modifier.weight(1f).padding(end = 8.dp),
                                label = {Text("City")}
                            )

                            OutlinedTextField(
                                value = postalCode.value,
                                onValueChange = {postalCode.value = it},
                                modifier = Modifier.weight(1f),
                                label = {Text("postal Code")}
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Column {
                        Text("Shipping Method", style = MaterialTheme.typography.headlineSmall)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = selectedMethod.value == "Standard FREE Delivery over Rs. 4500",
                                onClick = {
                                    selectedMethod.value = "Standard FREE Delivery over Rs. 4500"
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Standard FREE Delivery over Rs. 4500")
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = selectedMethod.value == "Cash on Delivery Rs. 50",
                                onClick = { selectedMethod.value = "Cash on delivery Rs. 50"}
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Cash on Delivery Rs. 50")
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            navController.navigate(Routes.Pay.route)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(colorResource(R.color.purple_500))
                    ) {
                        Text("Continue to Shipping")
                    }

                }
            }
        }
    }
}
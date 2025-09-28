package com.example.shopify.presentation.Screen

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.shopify.R
import com.example.shopify.domain.models.UserData
import com.example.shopify.domain.models.UserDataParent
import com.example.shopify.presentation.Utils.LogOutAlertDialog
import com.example.shopify.presentation.navigation.Routes
import com.example.shopify.presentation.viewModels.ShoppingAppViewModel
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreenUI(
    viewModel: ShoppingAppViewModel = hiltViewModel(),
    firebaseAuth: FirebaseAuth,
    navController: NavController
) {
    LaunchedEffect(Unit) {
        viewModel.getUserById(firebaseAuth.currentUser!!.uid)
    }

    val profileScreenState = viewModel.profileScreenState.collectAsStateWithLifecycle()
    val updateScreenState = viewModel.updateScreenState.collectAsStateWithLifecycle()
    val userProfileImageState = viewModel.uploadUserProfileImageState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    val showDialog = remember { mutableStateOf(false) }

    val isEditing = remember {mutableStateOf(false)}
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val imageUrl = remember{mutableStateOf("")}

    val firstName = remember{mutableStateOf(profileScreenState.value.userData?.userData?.firstName?:"")}
    val lastName = remember{mutableStateOf(profileScreenState.value.userData?.userData?.lastName?:"")}
    val email = remember{mutableStateOf(profileScreenState.value.userData?.userData?.email?:"")}
    val phoneNumber = remember{mutableStateOf(profileScreenState.value.userData?.userData?.phoneNumber?:"")}
    val address = remember{mutableStateOf(profileScreenState.value.userData?.userData?.address?:"")}

    LaunchedEffect(profileScreenState.value.userData) {
        profileScreenState.value.userData?.userData?.let { userData ->
            firstName.value = userData.firstName ?: ""
            lastName.value = userData.lastName ?: ""
            email.value = userData.email ?: ""
            phoneNumber.value = userData.phoneNumber ?: ""
            address.value = userData.address ?: ""
            imageUrl.value = userData.profileImage ?: ""
        }
    }

    val pickMedia = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
        if(uri != null) {
            viewModel.uploadUserProfileImage(uri)
            imageUri.value = uri
        }
    }

    if(updateScreenState.value.userData != null) {
        Toast.makeText(context, updateScreenState.value.userData, Toast.LENGTH_SHORT).show()
    }else if(updateScreenState.value.error != null) {
        Toast.makeText(context, updateScreenState.value.error, Toast.LENGTH_SHORT).show()
    }else if(updateScreenState.value.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }


    if(userProfileImageState.value.userData!=null) {
        imageUrl.value = userProfileImageState.value.userData.toString()

    }else if(userProfileImageState.value.error != null) {
        Toast.makeText(context, userProfileImageState.value.error, Toast.LENGTH_SHORT).show()
    }else if(userProfileImageState.value.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }


    if(profileScreenState.value.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

    }else if(profileScreenState.value.error != null) {
        Text(text = profileScreenState.value.error!!)
    }else if(profileScreenState.value.userData != null) {

        Scaffold (
            topBar = {
                TopAppBar(
                    title = {Text("Account",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold)},
                    navigationIcon = {
                        IconButton(
                            onClick = {navController.popBackStack()}
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_arrow_back_24),
                                contentDescription = "back Button"
                            )
                        }
                    }
                )
            }
        ){  innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                HorizontalDivider(
                    color = Color.LightGray,
                    thickness = 0.5.dp
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .align(Alignment.Start)
                    ){
                        SubcomposeAsyncImage(
                            model = if(isEditing.value) imageUrl.value else imageUrl.value,
                            contentDescription = "Profile Picture",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape)
                                .border(2.dp, color = colorResource(R.color.teal_200), CircleShape)
                        ) {

                            when(painter.state) {
                                is AsyncImagePainter.State.Loading -> CircularProgressIndicator()
                                is AsyncImagePainter.State.Error -> Icon(
                                    Icons.Default.Person,
                                    contentDescription = null
                                )

                                else -> SubcomposeAsyncImageContent()
                            }
                        }
                        if(isEditing.value) {
                            IconButton(
                                onClick = {
                                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                                },
                                modifier = Modifier
                                    .size(40.dp)
                                    .align(Alignment.BottomEnd)
                                    .background(MaterialTheme.colorScheme.primary, CircleShape)
                            ) {
                                Icon(
                                    Icons.Default.Add,
                                    contentDescription = "Change Picture",
                                    tint = Color.White
                                )
                            }
                        }


                    }

                    Spacer(modifier = Modifier.size(16.dp))

                    Row {
                        OutlinedTextField(
                            value = firstName.value,
                            modifier = Modifier.weight(1f),
                            readOnly = if(isEditing.value) false else true,

                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = colorResource(R.color.teal_200),
                                focusedBorderColor = colorResource(R.color.teal_700)
                            ),
                            shape = RoundedCornerShape(10.dp),
                            onValueChange = {firstName.value = it},
                            label = {Text("First Name")}
                        )

                        Spacer(modifier = Modifier.size(16.dp))

                        OutlinedTextField(
                            value = lastName.value,
                            modifier = Modifier.weight(1f),
                            readOnly = if(isEditing.value) false else true,

                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = colorResource(R.color.teal_200),
                                focusedBorderColor = colorResource(R.color.teal_700)
                            ),
                            shape = RoundedCornerShape(10.dp),
                            onValueChange = {lastName.value = it},
                            label = {Text("Last Name")}
                        )
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    OutlinedTextField(
                        value = email.value,
                        modifier = Modifier.weight(1f),
                        readOnly = if(isEditing.value) false else true,

                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = colorResource(R.color.teal_200),
                            focusedBorderColor = colorResource(R.color.teal_700)
                        ),
                        shape = RoundedCornerShape(10.dp),
                        onValueChange = {email.value = it},
                        label = {Text("Email")}
                    )

                    Spacer(modifier = Modifier.size(16.dp))
                    OutlinedTextField(
                        value = phoneNumber.value,
                        modifier = Modifier.weight(1f),
                        readOnly = if(isEditing.value) false else true,

                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = colorResource(R.color.teal_200),
                            focusedBorderColor = colorResource(R.color.teal_700)
                        ),
                        shape = RoundedCornerShape(10.dp),
                        onValueChange = {phoneNumber.value = it},
                        label = {Text("Phone Number")}
                    )

                    Spacer(modifier = Modifier.size(16.dp))

                    OutlinedTextField(
                        value = address.value,
                        modifier = Modifier.weight(1f),
                        readOnly = if(isEditing.value) false else true,

                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = colorResource(R.color.teal_200),
                            focusedBorderColor = colorResource(R.color.teal_700)
                        ),
                        shape = RoundedCornerShape(10.dp),
                        onValueChange = {address.value = it},
                        label = {Text("Address")}
                    )
                    Spacer(modifier = Modifier.size(16.dp))

                    OutlinedButton(
                        onClick = {
                            showDialog.value = true
                        },

                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(colorResource(R.color.teal_200))
                    ){
                        Text("Log Out")
                    }

                    if(showDialog.value) {
                        LogOutAlertDialog (
                            onDismiss = {
                                showDialog.value = false
                            },
                            onConfirm = {
                                firebaseAuth.signOut()
                                navController.navigate(Routes.Login.route)
                            }
                        )
                    }
                    Spacer(modifier = Modifier.size(16.dp))

                    if(!isEditing.value) {
                        OutlinedButton(
                            onClick = {
                                isEditing.value = !isEditing.value
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Edit Profile")
                        }
                    }else {
                        OutlinedButton(
                            onClick = {

                                val updateUserData = UserData(
                                    firstName = firstName.value,
                                    lastName = lastName.value,
                                    email = email.value,
                                    phoneNumber = phoneNumber.value,
                                    address = address.value,
                                    profileImage = imageUrl.value
                                )

                                val userDataParent = UserDataParent(
                                    nodeId = profileScreenState.value.userData!!.nodeId,
                                    userData = updateUserData
                                )


                                viewModel.updateUserData(userDataParent)
                                isEditing.value = !isEditing.value

                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                        ) {
                            Text("Save Profile")
                        }
                    }
                }
            }
        }
    }
}
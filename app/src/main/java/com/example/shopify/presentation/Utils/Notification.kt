package com.example.shopify.presentation.Utils


import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun NotificationPermissionRequest(){
    val context = LocalContext.current

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {isGranted: Boolean ->
        if(isGranted){
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }

    }

    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
        val permissionStatus = ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.POST_NOTIFICATIONS
        )
        if(permissionStatus != PackageManager.PERMISSION_GRANTED){
            LaunchedEffect(Unit) {
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}

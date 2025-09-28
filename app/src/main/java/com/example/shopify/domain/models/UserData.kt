package com.example.shopify.domain.models

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val phoneNumber: String = "",
    val address: String = "",
    val profileImage: String = "",
) {

    fun toMap(): Map<String, Any> {

        val map = mutableStateMapOf<String, Any>()
        map["firstName"] = firstName
        map["lastName"] = lastName
        map["email"] = email
        map["password"] = password
        map["phoneNumber"] = phoneNumber
        map["address"] = address
        map["profileImage"] = profileImage

        return map
    }
}


data class UserDataParent(val nodeId: String = "",
                          val userData: UserData = UserData()
)



package tr.com.sdateapp.logic.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val message: String,
    val success: Boolean,
    val expireDate: String
)

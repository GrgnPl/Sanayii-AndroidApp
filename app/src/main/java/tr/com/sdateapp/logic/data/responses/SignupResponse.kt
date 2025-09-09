package tr.com.sdateapp.logic.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class SignupResponse(
    val message: String,
    val success: Boolean
)

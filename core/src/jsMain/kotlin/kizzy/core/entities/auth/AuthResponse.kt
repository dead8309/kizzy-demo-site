package kizzy.core.entities.auth


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    @SerialName("token")
    val token: String? = null,
    @SerialName("user_id")
    val userId: String? = null,
    @SerialName("user_settings")
    val userSettings: UserSettings? = null
)

@Serializable
data class UserSettings(
    @SerialName("locale")
    val locale: String? = null,
    @SerialName("theme")
    val theme: String? = null
)
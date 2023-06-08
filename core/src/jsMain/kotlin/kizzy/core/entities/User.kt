package kizzy.core.entities


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("avatar")
    val avatar: String? = null,
    @SerialName("banner")
    val banner: String? = null,
    @SerialName("discriminator")
    val discriminator: String? = null,
    @SerialName("id")
    val id: String? = null,
    @SerialName("username")
    val username: String? = null
)
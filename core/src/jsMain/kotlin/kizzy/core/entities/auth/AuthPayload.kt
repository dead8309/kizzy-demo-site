package kizzy.core.entities.auth

import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName

 
@Serializable
data class AuthPayload(
    @SerialName("captcha_key")
    val captchaKey: String,
    @SerialName("login")
    val login: String,
    @SerialName("password")
    val password: String,
    @SerialName("undelete")
    val undelete: Boolean = false
)
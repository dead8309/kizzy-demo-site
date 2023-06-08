package kizzy.core.entities.auth


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthError(
    @SerialName("code")
    val code: Int? = 0,
    @SerialName("errors")
    val errors: Errors? = Errors(),
    @SerialName("message")
    val message: String? = ""
)

@Serializable
data class ErrorsX(
    @SerialName("code")
    val code: Int? = null,
    @SerialName("message")
    val message: String? = null
)

@Serializable
data class Login(
    @SerialName("_errors")
    val errors: ErrorsX? = ErrorsX()
)

@Serializable
data class Errors(
    @SerialName("login")
    val login: Login? = Login()
)
package kizzy.core.entities

import kizzy.core.entities.op.OpCodes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class Payload(
    @SerialName("t")
    val t: String? = null,
    @SerialName("s")
    val s: Int? = null,
    @SerialName("op")
    val op: OpCodes? = null,
    @SerialName("d")
    val d: JsonElement? = null
)

@Serializable
data class OutgoingPayload<T>(
    @SerialName("op")
    val opCode: OpCodes,

    @SerialName("d")
    val data: T?,
)
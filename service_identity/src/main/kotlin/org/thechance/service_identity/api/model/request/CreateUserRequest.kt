package org.thechance.service_identity.api.model.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateUserRequest(
    val fullName: String,
    val username: String,
    val password: String
)
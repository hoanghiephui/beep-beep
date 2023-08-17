package org.thechance.api_gateway.domain.entity

data class TokenConfiguration(
    val secret: String,
    val issuer: String,
    val audience: String,
    val accessTokenExpirationTimestamp: Long,
    val refreshTokenExpirationTimestamp: Long
)
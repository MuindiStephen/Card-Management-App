package com.muindi.stephen.co_opbankapp.data.dto.responses

/**
 * DTO user response
 */
data class RemoteUser(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val avatarUrl: String?,
    val address: Address
)

data class Address(
    val street: String,
    val city: String,
    val country: String,
    val postalCode: String
)
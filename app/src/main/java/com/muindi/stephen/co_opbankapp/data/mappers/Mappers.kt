package com.muindi.stephen.co_opbankapp.data.mappers

import com.muindi.stephen.co_opbankapp.data.dto.responses.GetUserResponse
import com.muindi.stephen.co_opbankapp.data.dto.responses.RemoteUser

/**
 * A mapper to map DTO response of user response to an entity class to make it easier
 * to store local data to room db
 */
fun RemoteUser.toGetUserResponseEntity(): GetUserResponse = GetUserResponse(
    id = id,
    firstName = firstName,
    lastName = lastName,
    email = email,
    phone = phone,
    avatarUrl = avatarUrl,
    street = address?.street ?: "",
    city = address?.city ?: "",
    country = address?.country ?: "",
    postalCode = address?.postalCode ?: ""
)
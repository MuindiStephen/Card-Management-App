package com.muindi.stephen.co_opbankapp.data.dto.responses

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "userProfile")
data class GetUserResponse(
    @PrimaryKey val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val avatarUrl: String?,
    @Embedded val address: Address,
)

data class Address(
    val street: String,
    val city: String,
    val country: String,
    val postalCode: String,
)
package com.muindi.stephen.co_opbankapp.data.local.room.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.muindi.stephen.co_opbankapp.data.dto.responses.GetUserResponse
import kotlinx.coroutines.flow.Flow

interface UserProfileDao {
    @Query("SELECT * FROM userProfile LIMIT 1")
    fun getUser(): Flow<GetUserResponse?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: GetUserResponse)
}
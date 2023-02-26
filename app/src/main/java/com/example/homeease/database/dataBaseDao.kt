package com.example.homeease.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.homeease.model.MyOrder

@Dao
interface dataBaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(order : MyOrder) : Long

    @Query("SELECT * FROM orders")
    fun getAllOrders() : LiveData<List<MyOrder>>

}

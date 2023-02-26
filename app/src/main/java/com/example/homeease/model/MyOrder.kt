package com.example.homeease.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName ="orders"
)
data class MyOrder(
    @PrimaryKey(autoGenerate = true)
    val id :Int = 0,
    val title: String = "Yellow Gloves",
    val subtitle: String,
    val date: String?,
    val otp: String,
    val cost: String = "INR 8000",

): Serializable

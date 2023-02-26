package com.example.homeease.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.homeease.model.MyOrder


@Database(
    entities =[MyOrder::class],
    version = 1
)
abstract class MyOrderDatabase : RoomDatabase(){

    abstract fun getOrders() : dataBaseDao

   companion object{
        @Volatile
        private var instance : MyOrderDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK){
            instance?: createDatabase(context).also {
                instance = it
            }
        }

        fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MyOrderDatabase::class.java,
                "my_orders.db"
            ).build()
    }
}
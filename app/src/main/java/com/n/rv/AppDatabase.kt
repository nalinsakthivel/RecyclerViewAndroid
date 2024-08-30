package com.n.rv

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ProductListDbModel::class], version = 0)
abstract class AppDatabase : RoomDatabase() {
    abstract fun listDao(): ProductLIstDao


}
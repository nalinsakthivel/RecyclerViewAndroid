package com.n.rv.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.n.rv.roomdatabase.dao.ProductLIstDao
import com.n.rv.roomdatabase.dbmodel.ProductListDbModel
import kotlin.concurrent.Volatile


@Database(entities = [ProductListDbModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun listDao(): ProductLIstDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                  "product_list"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

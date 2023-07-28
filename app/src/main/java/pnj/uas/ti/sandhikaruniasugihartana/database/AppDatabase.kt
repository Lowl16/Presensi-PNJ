package pnj.uas.ti.sandhikaruniasugihartana.database

import androidx.room.Database
import pnj.uas.ti.sandhikaruniasugihartana.model.ModelDatabase
import androidx.room.RoomDatabase
import pnj.uas.ti.sandhikaruniasugihartana.database.dao.DatabaseDao

@Database(entities = [ModelDatabase::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun databaseDao(): DatabaseDao?
}
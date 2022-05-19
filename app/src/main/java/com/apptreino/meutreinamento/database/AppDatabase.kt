package com.apptreino.meutreinamento.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.apptreino.meutreinamento.database.dao.UserDao
import com.apptreino.meutreinamento.model.User

const val CURRENT_VERSION_DATABASE = 2

@Database(
    entities = [
        User::class
    ],
    version = CURRENT_VERSION_DATABASE,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {

        abstract fun userDao(): UserDao

        companion object {
            @Volatile
            private var db: AppDatabase? = null
            fun instancia(context: Context): AppDatabase? {
                db = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        DATABASE_NAME)
                    .addMigrations(MIGRATION_1_2)
                    .fallbackToDestructiveMigration()
                    .build()

                return db
            }

            private const val DATABASE_NAME: String = "treino_db"
        }
}



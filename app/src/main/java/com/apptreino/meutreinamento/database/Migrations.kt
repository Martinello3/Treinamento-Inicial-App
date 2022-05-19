package com.apptreino.meutreinamento.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {

    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `Usuario` (
            `id` TEXT NOT NULL,
            `cpf_cnpj` TEXT NOT NULL, 
            `senha` TEXT NOT NULL, PRIMARY KEY(`id`))
            """
        )
        database.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `User`(
            `id` TEXT NOT NULL,
            `cpf` TEXT NOT NULL,
            `birthDay` TEXT NOT NULL,
            `name` TEXT NOT NULL,
            `email` TEXT NOT NULL,
            `phone` TEXT NOT NULL,
            PRIMARY KEY(`id`))""")
    }
}


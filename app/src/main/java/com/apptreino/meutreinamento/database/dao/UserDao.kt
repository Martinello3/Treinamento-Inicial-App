package com.apptreino.meutreinamento.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.apptreino.meutreinamento.model.User

@Dao
interface UserDao {

    @Insert
    suspend fun salva(user: User)

    //Duvida
    @Query("""SELECT * FROM User 
        WHERE _id = :userId
        AND cpf = :cpf_cnpj
        AND senha = :senha
    """) fun autentica(
        userId: Int,
        cpf_cnpj: String,
        senha: String
    ): User?

    @Query("SELECT * FROM User WHERE _id= :userId")
    fun buscaPorId(userId: Int): User



}
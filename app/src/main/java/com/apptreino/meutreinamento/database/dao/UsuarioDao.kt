package com.apptreino.meutreinamento.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.apptreino.meutreinamento.model.Usuario


@Dao
interface UsuarioDao {

    @Insert
    suspend fun salva(usuario: Usuario)

    @Query("""
        SELECT * FROM Usuario 
        WHERE id = :cpf_cnpjId
        AND senha = :senha""")
    fun autentica(
        cpf_cnpjId: String,
        senha: String
    ) : Usuario?

    @Query("SELECT * FROM Usuario WHERE id= :cpf_cnpjId")
    fun buscaPorId(cpf_cnpjId: String): Usuario

    @Query("SELECT * FROM Usuario")
    fun buscaTodos(): MutableList<Usuario>?

    @Query("UPDATE Usuario SET nome =:nome WHERE id =:cpf_cnpjId")
    suspend fun update(cpf_cnpjId: String, nome: String) : Int

}
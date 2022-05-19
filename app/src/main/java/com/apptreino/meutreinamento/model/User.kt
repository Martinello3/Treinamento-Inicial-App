package com.apptreino.meutreinamento.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity
data class User(
    @PrimaryKey
    var _id: Long? = null,
    var cpf: String? = null,
    var birthDay: String? = null,
    var name: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var senha: String? = null
) : Serializable
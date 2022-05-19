package com.apptreino.meutreinamento.webService.resposta

import com.google.gson.annotations.SerializedName

class UsuarioAutenticacaoResposta {
    @SerializedName("IdUsuario")
    var _id : Long? = null

    @SerializedName("CPF")
    var cpf : String? = null

    @SerializedName("Nascimento")
    var dataAniversario : String? = null

    @SerializedName("Nome")
    var nome : String? = null

    @SerializedName("Email")
    var email : String? = null

    @SerializedName("Telefone")
    var telefone : String? = null
}
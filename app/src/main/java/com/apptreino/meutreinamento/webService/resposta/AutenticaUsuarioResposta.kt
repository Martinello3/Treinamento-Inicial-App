package com.apptreino.meutreinamento.webService.resposta

import com.google.gson.annotations.SerializedName

class AutenticaUsuarioResposta {
    @SerializedName("Sucesso")
    val sucesso : Boolean? = null

    @SerializedName("Mensagem")
    val mensagem : String? = null

    @SerializedName("Data")
    val objeto : UsuarioAutenticacaoResposta? = null
}
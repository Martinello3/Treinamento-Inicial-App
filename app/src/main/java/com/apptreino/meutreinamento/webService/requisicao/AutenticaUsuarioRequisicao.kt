package com.apptreino.meutreinamento.webService.requisicao

import com.google.gson.annotations.SerializedName

class AutenticaUsuarioRequisicao {

    data class Login (
        @SerializedName("CPF")
        var cpf : String? = null,

        @SerializedName("Senha")
        var senha : String? = null
    )

    data class Cadastro (
        @SerializedName("CPF")
        var cpf: String? = null,

        @SerializedName("Email")
        var email: String? = null,

        @SerializedName("Nascimento")
        var birthDay: String? = null,

        @SerializedName("Nome")
        var name: String? = null,

        @SerializedName("Senha")
        var senha: String? = null,

        @SerializedName("Telefone")
        var phone: String? = null
    )

    data class Alterar (
        @SerializedName("CPF")
        var cpf: String? = null,

        @SerializedName("Nome")
        var name: String? = null,

        @SerializedName("Nascimento")
        var birthDay: String? = null,

        @SerializedName("Email")
        var email: String? = null,

        @SerializedName("Telefone")
        var phone: String? = null
    )

}
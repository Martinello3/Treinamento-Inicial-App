package com.apptreino.meutreinamento.webService

import com.apptreino.meutreinamento.webService.requisicao.AutenticaUsuarioRequisicao
import com.apptreino.meutreinamento.webService.resposta.AutenticaUsuarioResposta
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface APIUsuario {

    @POST("Login")
    fun login(@Body requisicao: AutenticaUsuarioRequisicao.Login) : Call<AutenticaUsuarioResposta>

    @POST("Cadastro")
    fun cadastro(@Body requisicao: AutenticaUsuarioRequisicao.Cadastro) : Call<AutenticaUsuarioResposta>

    @PUT("Alterar")
    fun altera(@Body requisicao: AutenticaUsuarioRequisicao.Alterar) : Call<AutenticaUsuarioResposta>

}
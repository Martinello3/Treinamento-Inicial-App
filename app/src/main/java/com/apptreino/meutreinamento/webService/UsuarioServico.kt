package com.apptreino.meutreinamento.webService

import com.apptreino.meutreinamento.webService.requisicao.AutenticaUsuarioRequisicao
import com.apptreino.meutreinamento.webService.resposta.AutenticaUsuarioResposta

class UsuarioServico {

    fun login(request : AutenticaUsuarioRequisicao.Login) : AutenticaUsuarioResposta {
        val retrofit = RetrofitImplement().buildRetrofit()
        val api = retrofit.create(APIUsuario::class.java)
        val objetoDeChamada = api.login(request)
        val execucao = objetoDeChamada.execute()
        val resposta= execucao.body()
        if (resposta != null) {
            return resposta
        } else {
            throw Exception("Não foi possível realizar o login nesse momento, tente novamente mais tarde")
        }

    }

    fun cadastra(request : AutenticaUsuarioRequisicao.Cadastro) : AutenticaUsuarioResposta {
        val retrofit = RetrofitImplement().buildRetrofit()
        val api = retrofit.create(APIUsuario::class.java)
        val objetoDeChamada = api.cadastro(request)
        val execucao = objetoDeChamada.execute()
        val resposta = execucao.body()
        if(resposta != null) {
            return resposta
        } else {
            throw Exception("Erro ao realizar o registro do usuário")
        }
    }

    fun altera(request : AutenticaUsuarioRequisicao.Alterar) : AutenticaUsuarioResposta {
        val retrofit = RetrofitImplement().buildRetrofit()
        val api = retrofit.create(APIUsuario::class.java)
        val objetoDeChamada = api.altera(request)
        val execucao = objetoDeChamada.execute()
        val resposta = execucao.body()
        if(resposta != null) {
            return resposta
        } else {
            throw Exception("Erro ao alterar informações de usuário")
        }
    }
}
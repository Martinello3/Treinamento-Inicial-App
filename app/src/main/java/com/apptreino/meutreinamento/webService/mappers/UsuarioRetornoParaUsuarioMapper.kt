package com.apptreino.meutreinamento.webService.mappers

import com.apptreino.meutreinamento.model.User
import com.apptreino.meutreinamento.webService.resposta.UsuarioAutenticacaoResposta

class UsuarioRetornoParaUsuarioMapper {

    object Mapper {
        fun transform(resposta: UsuarioAutenticacaoResposta): User {
            val user = User()
            user._id = resposta._id
            user.cpf = resposta.cpf
            user.email = resposta.email
            user.birthDay = resposta.dataAniversario
            user.phone = resposta.telefone
            user.name = resposta.nome
            return user
        }
    }

}
package com.apptreino.meutreinamento

import com.apptreino.meutreinamento.webService.UsuarioServico
import com.apptreino.meutreinamento.webService.requisicao.AutenticaUsuarioRequisicao

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testeServicaoApiA() {
        var requisicao = AutenticaUsuarioRequisicao.Alterar()
        requisicao.cpf = "84152252022"
        requisicao.email = "gilmarsilvamedeiros@gmail.com"
        requisicao.birthDay = "2000-01-11T21:53:21.861Z"
        requisicao.name = "Gilmar Da Rosa"
        requisicao.phone = "49988810383"

        val usuarioServico = UsuarioServico()
        var resposta = usuarioServico.altera(requisicao)

        assert(resposta.sucesso!!)
    }

    @Test
    fun testeServicoApiC() {
        var requisicao = AutenticaUsuarioRequisicao.Cadastro()
        requisicao.cpf = "84152252022"
        requisicao.email = "gilmarsilvamedeiros@gmail.com"
        requisicao.birthDay = "2000-01-11T21:53:21.861Z"
        requisicao.name = "Gilmar Da Silva"
        requisicao.senha = "123456"
        requisicao.phone = "49988810383"

        val usuarioServico = UsuarioServico()
        var resposta = usuarioServico.cadastra(requisicao)

        assert(resposta.sucesso!!)
    }

    @Test
    fun testeFalhaServicoApiC() {
        var requisicao = AutenticaUsuarioRequisicao.Cadastro()
        requisicao.cpf = "77293886004"
        requisicao.email = "gilmarsilvamedeiros@gmail.com"
        requisicao.birthDay = "2000-01-11T21:53:21.861Z"
        requisicao.name = "Gilmar Da Silva"
        requisicao.senha = "123456"
        requisicao.phone = "49988810383"

        val usuarioServico = UsuarioServico()
        var resposta = usuarioServico.cadastra(requisicao)

        assert(resposta.sucesso!!.not())
    }

    @Test
    fun testeSerivoApi() {
        var requisicao = AutenticaUsuarioRequisicao.Login()
        requisicao.cpf = "09518747997"
        requisicao.senha = "123456"

        val usuarioServico = UsuarioServico()
        var resposta = usuarioServico.login(requisicao)

        assert(resposta.sucesso!!)
    }

    @Test
    fun testeSerivoFalhaApi() {
        var requisicao = AutenticaUsuarioRequisicao.Login()
        requisicao.cpf = "09518747997"
        requisicao.senha = "12345"

        val usuarioServico = UsuarioServico()
        var resposta = usuarioServico.login(requisicao)

        assert(resposta.sucesso!!.not())
    }

}
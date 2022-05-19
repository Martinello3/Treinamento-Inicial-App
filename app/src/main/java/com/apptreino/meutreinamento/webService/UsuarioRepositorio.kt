package com.apptreino.meutreinamento.webService

import com.apptreino.meutreinamento.model.User
import com.apptreino.meutreinamento.webService.mappers.UsuarioRetornoParaUsuarioMapper
import com.apptreino.meutreinamento.webService.requisicao.AutenticaUsuarioRequisicao
import com.apptreino.meutreinamento.webService.resposta.AutenticaUsuarioResposta
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class UsuarioRepositorio {

    private val usuarioServico: UsuarioServico
        get() {
            return UsuarioServico()
        }


    private fun login(cpf: String, senha: String) : Single<AutenticaUsuarioResposta> {
        var requisicao = AutenticaUsuarioRequisicao.Login(cpf, senha)
        var observando = Single.create<AutenticaUsuarioResposta> {
            try {
                val resposta = usuarioServico.login(requisicao)
                if(resposta.sucesso!!) {
                    it.onSuccess(resposta)
                } else {
                    it.onError(Exception(resposta.mensagem))
                }
            } catch (ex: Exception) {
                it.onError(ex)
            }
        }
        return observando
    }

    fun login(cpf: String, senha: String, observer: DisposableObserver<User>) {
        login(cpf, senha)
            .toObservable()
            .map {
                UsuarioRetornoParaUsuarioMapper.Mapper.transform(it.objeto!!)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    private fun cadastro(cpf : String, email : String, data : String, name : String, senha : String, telefone : String) : Single<AutenticaUsuarioResposta> {
        var requisicao = AutenticaUsuarioRequisicao.Cadastro(cpf, email, data, name, senha, telefone)
        val observando = Single.create<AutenticaUsuarioResposta> {
            try {
                val resposta = usuarioServico.cadastra(requisicao)
                if (resposta.sucesso!!) {
                    it.onSuccess(resposta)
                } else {
                    it.onError(Exception(resposta.mensagem))
                }
            } catch (ex: Exception) {
                it.onError(ex)
            }
        }
        return observando
    }

    fun cadastra(cpf : String, email : String, data : String, name : String, senha : String, telefone : String, observer: DisposableObserver<User>) {
        cadastro(cpf, email, data, name, senha, telefone)
            .toObservable()
            .map {
                UsuarioRetornoParaUsuarioMapper.Mapper.transform(it.objeto!!)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    private fun altera(cpf : String, name : String, data : String, email : String, telefone : String) : Single<AutenticaUsuarioResposta> {
        var requisicao = AutenticaUsuarioRequisicao.Alterar(cpf, name, data, email, telefone)
        var observando = Single.create<AutenticaUsuarioResposta> {
            try {
                val resposta = usuarioServico.altera(requisicao)
                if (resposta.sucesso!!) {
                    it.onSuccess(resposta)
                } else {
                    it.onError(Exception(resposta.mensagem))
                }
            } catch (ex: Exception) {
                it.onError(ex)
            }
        }
        return observando
    }

    fun alteracao(cpf : String, name : String, data : String, email : String, telefone : String, observer: DisposableObserver<User>) {
        altera(cpf, name, data, email, telefone)
            .toObservable()
            .map {
                UsuarioRetornoParaUsuarioMapper.Mapper.transform(it.objeto!!)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }


}
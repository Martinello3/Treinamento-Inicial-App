package com.apptreino.meutreinamento.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.apptreino.meutreinamento.databinding.ActivityTelaCadastroBinding
import com.apptreino.meutreinamento.extensions.vaiPara
import com.apptreino.meutreinamento.model.User
import com.apptreino.meutreinamento.ui.login.LoginActivity
import com.apptreino.meutreinamento.util.*
import com.apptreino.meutreinamento.webService.UsuarioRepositorio
import com.google.android.material.snackbar.Snackbar
import io.reactivex.observers.DisposableObserver
import java.util.*

class CadastroActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityTelaCadastroBinding.inflate(layoutInflater)
    }

    private val repositorio = UsuarioRepositorio()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configurarTextFields()
        novoUsuario()
        voltarTela()

    }

    private fun voltarTela() {
        binding.voltarLogin.setOnClickListener {
            vaiPara(LoginActivity::class.java)
        }
    }

    private fun novoUsuario() {
        binding.botaoProximo.setOnClickListener {
            if(validarCampos()) {
                val cpf = binding.cadastroCpfCnpjCampo.text.toString()
                val email = binding.cadastroEmailCampo.text.toString()
                val data = binding.cadastroDataCampo.text.toString()
                val novaData = verificaData(data)
//              val dataFormatada = DataUtil.data(data)
                val name = binding.cadastroNomeCampo.text.toString()
                val senha = binding.cadastroSenhaCampo.text.toString()
                val telefone = binding.cadastroTelefoneCampo.text.toString()
//              val telefoneUnMascked = MaskUtil.unmaskNew(telefone)
                showDialogProgress()
                realizaCadastroDeUsuario(cpf, email, DataUtil.data("yyyy-MM-dd", novaData), name, senha, telefone.unmask())
            }
        }
    }

    private fun verificaData(data: String): Date? {
        if (data.isNotEmpty()) {
            val novoNascimento = DataUtil.dataPaymentToDate(data)
            return novoNascimento
        }
        return null
    }

    private fun realizaCadastroDeUsuario(cpf: String, email: String, data: String, name: String, senha: String, telefone: String) {
        repositorio.cadastra(cpf, email, data, name, senha, telefone, object : DisposableObserver<User>() {
                override fun onNext(t: User) {
                    hideDialogProgress()
                    Toast.makeText(this@CadastroActivity, "Cadastro realizado, usuário: ${t.cpf}", Toast.LENGTH_SHORT).show()
                    showLoginActivity(t)
                }

                override fun onError(e: Throwable) {
                    hideDialogProgress()
                    Toast.makeText(
                        this@CadastroActivity,
                        "RETORNOU SUCESSO NOME USUÁRIO: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onComplete() {
                    TODO("Not yet implemented")
                }
            })
    }


    private fun showLoginActivity(user: User) {
        val intent = Intent(this@CadastroActivity, LoginActivity::class.java)
        intent.putExtra("USUARIO", user)
        startActivity(intent)
    }

    private fun showDialogProgress() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideDialogProgress() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun configurarTextFields() {
        binding.cadastroCpfCnpjCampo.addTextChangedListener(EditTextMask.insert(binding.cadastroCpfCnpjCampo))
        binding.cadastroTelefoneCampo.addTextChangedListener(MaskUtil.insert(MaskUtil.CELULAR, binding.cadastroTelefoneCampo))
        binding.cadastroDataCampo.addTextChangedListener(MaskUtil.insert(MaskUtil.DATA, binding.cadastroDataCampo))
    }

    private fun validarCampos(): Boolean {
        if (binding.cadastroCpfCnpjCampo.text.isNullOrEmpty()) {
            binding.cadastroCpfCnpjCampo.error = "Informe um CPF para continuar o cadastro"
            return false
        }
        if (binding.cadastroCpfCnpjCampo.text!!.unmask().isCPF().not()) {
            binding.cadastroCpfCnpjCampo.error = "Informe um CPF/CNPJ válido para continuar o cadastro"
            return false
        }

        if (binding.cadastroDataCampo.text!!.isNullOrEmpty()) {
            binding.cadastroDataCampo.error = "Informe uma data para continuar o cadastro"
            return false
        }
        if (binding.cadastroDataCampo.text!!.length < 10) {
            binding.cadastroDataCampo.error = "Informe uma data adequada"
            return false
        }

        if (binding.cadastroNomeCampo.text.isNullOrEmpty()) {
            binding.cadastroNomeCampo.error = "Informe um nome para continuar o cadastro"
            return false
        }
        if (binding.cadastroNomeCampo.text!!.length < 3) {
            binding.cadastroNomeCampo.error = "Informe um nome válido para continuar o cadastro"
            return false
        }

       if (binding.cadastroEmailCampo.text!!.isNullOrEmpty()) {
           binding.cadastroEmailCampo.error = "Informe um email para continuar o cadastro"
           return false
       }

        if (binding.cadastroTelefoneCampo.text!!.isNullOrEmpty()) {
            binding.cadastroTelefoneCampo.error = "Informe um telefone para continuar o cadastro"
            return false
        }
        if (binding.cadastroTelefoneCampo.text!!.length < 15) {
            binding.cadastroTelefoneCampo.error = "Informe um número de telefone válido"
            return false
        }

        if (binding.cadastroSenhaCampo.text.isNullOrEmpty()) {
            binding.cadastroSenhaCampo.error = "Informe a senha para continuar o cadastro"
            return false
        }
        if (binding.cadastroSenhaCampo.text!!.length <= 5) {
            binding.cadastroSenhaCampo.error = "A senha deve conter no mínimo 6 caracteres"
            return false
        }
        return true
    }
}
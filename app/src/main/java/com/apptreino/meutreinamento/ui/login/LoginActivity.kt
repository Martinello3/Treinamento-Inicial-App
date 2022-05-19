package com.apptreino.meutreinamento.ui.login

// COPIA
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.apptreino.meutreinamento.databinding.ActivityTelaLoginBinding
import com.apptreino.meutreinamento.extensions.vaiPara
import com.apptreino.meutreinamento.model.User
import com.apptreino.meutreinamento.ui.activity.CadastroActivity
import com.apptreino.meutreinamento.ui.activity.InfoActivity
import com.apptreino.meutreinamento.util.MaskUtil
import com.apptreino.meutreinamento.util.isCPF
import com.apptreino.meutreinamento.util.unmask
import com.apptreino.meutreinamento.webService.UsuarioRepositorio
import io.reactivex.observers.DisposableObserver


class LoginActivity : AppCompatActivity() {

//    Dialog de progresso
//    private var loadingProgressBar : Dialog? = null

    private val binding by lazy {
        ActivityTelaLoginBinding.inflate(layoutInflater)
    }

    private val repositorio by lazy {
        UsuarioRepositorio()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar
        setContentView(binding.root)
        botaoEntrar()
        botaoCadastrar()
        configurarTextFields()

    }


    fun showInfoActivity(user: User) {
        val intent = Intent(this@LoginActivity, InfoActivity::class.java)
        intent.putExtra("USUARIO", user)
        startActivity(intent)
    }

    private fun realizarAutenticacaoUsuario(cpf: String, senha: String) {
        repositorio.login(cpf, senha, object : DisposableObserver<User>() {
            override fun onNext(t: User) {
                hideDialogProgress()
                Toast.makeText(this@LoginActivity, "BEM VINDO: ${t.name}", Toast.LENGTH_SHORT).show()
                showInfoActivity(t)
            }

            override fun onError(e: Throwable) {
                hideDialogProgress()
                Toast.makeText(this@LoginActivity, "${e.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onComplete() {

            }

        })
    }

    private fun showDialogProgress() {
        hideDialogProgress()
//        loadingProgressBar = CommunUtil.showLoadingDialog(this)
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideDialogProgress() {
//        loadingProgressBar?.let { if (it.isShowing)it.cancel() }
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun configurarTextFields() {
        binding.cpfCnpjCampoLogin.addTextChangedListener(MaskUtil.insert(MaskUtil.CPF, binding.cpfCnpjCampoLogin))
    }

    private fun botaoCadastrar() {
        binding.cadastroBotao.setOnClickListener {
            vaiPara(CadastroActivity::class.java)
        }
    }
    private fun botaoEntrar() {
        binding.entrarBotao.setOnClickListener {
            if (validarCampos()) {
                val cpf_cnpj = binding.cpfCnpjCampoLogin.text.toString()
                val senha = binding.senhaCampoLogin.text.toString() // Código anterior  binding.cpfCnpjCampoLogin.text.toString() COMO VOCÊ ESTAVA PEGANDO O VALOR DO CAMPO DE CNPJ NÃO FARIA LOGIN E ESTAVA RETORNANDO NULL
                autentica(cpf_cnpj, senha)
            }
        }
    }

    private fun autentica(cpf_cnpj: String, senha: String) {
        showDialogProgress()
        realizarAutenticacaoUsuario(cpf_cnpj.unmask(), senha)
    }

    private fun validarCampos(): Boolean {
        if (binding.cpfCnpjCampoLogin.text.isNullOrEmpty()) {
            binding.cpfCnpjCampoLogin.error = "informe um cpf para realizar o login"
            return false
        }

        if (binding.cpfCnpjCampoLogin.text!!.unmask().isCPF().not()) {
            binding.cpfCnpjCampoLogin.error = "Informe um CPF/CNPJ válido para continuar o login"
            return false
        }

        if (binding.senhaCampoLogin.text.isNullOrEmpty()) {
            binding.senhaCampoLogin.error = "Informe a senha do cpf ja cadastrado"
            return false
        }

        if (binding.senhaCampoLogin.text!!.length <= 5) {
            binding.senhaCampoLogin.error = "A senha deve conter no mínimo 6 caracteres"
            return false
        }
        return true
    }
}


//        CoroutineScope(Dispatchers.IO).launch {
//            val user = usuarioDao?.autentica(cpf_cnpj, senha)
//            if(user != null) {
//                dataStore.edit { preferences ->
//                    preferences[usuarioLogadoPreferences] = user.id
//                }
//                vaiPara(InfoActivity::class.java)
//                finish()
//            } else {
//                // Não é possível executar um toast em um scopo de coroutines sem um Handler. Da para jogar essa implementação para e extension function Context.toast()
//                Handler(Looper.getMainLooper()).post {
//                    Toast
//                        .makeText(applicationContext, "Não foi possível realizar o login", Toast.LENGTH_SHORT)
//                        .show()
//                }
//            }
//        }
//    }

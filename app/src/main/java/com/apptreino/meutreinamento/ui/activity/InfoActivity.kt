package com.apptreino.meutreinamento.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.apptreino.meutreinamento.BuildConfig
import com.apptreino.meutreinamento.R
import com.apptreino.meutreinamento.databinding.ActivityTelaInfoBinding
import com.apptreino.meutreinamento.extensions.vaiPara
import com.apptreino.meutreinamento.model.User
import com.apptreino.meutreinamento.ui.login.LoginActivity
import com.apptreino.meutreinamento.util.DataUtil
import com.apptreino.meutreinamento.util.MaskUtil

class InfoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityTelaInfoBinding.inflate(layoutInflater)
    }
    private var user: User? = null
    private var teste: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        user = intent.extras?.getSerializable("USUARIO") as User?


        teste = intent.extras?.getInt("TESTE")
        if(teste != null && teste == 1) {
            user = intent.extras?.getSerializable("USUARIO_DOMINIO") as User?
        }

        setDisplay()
        deslogarBotaoSair()
        vaiParaAlteraActivity()

        user?.let { user ->
            setDisplayInfoUser(user)
        }
    }


//    private fun passaInformacoes() {
//        CoroutineScope(Dispatchers.IO).launch {
//            dataStore.data.collect {
//                val userId = it[usuarioLogadoPreferences]
//            }
//        }
//    }

    private fun setDisplayInfoUser(user: User) {
        binding.cpfInfo.text = user.cpf?.let { MaskUtil.insert(it, MaskUtil.CPF) }
        binding.nomeInfo.text = user.name
        binding.dataNascInfo.text = DataUtil.data("dd/MM/yyyy", user.birthDay)
        binding.emailInfo.text = user.email
        binding.phoneInfo.text = user.phone?.let { MaskUtil.insert(it, MaskUtil.CELULAR) }
    }

    private fun setDisplay() {
        binding.versaoInfo.text = BuildConfig.VERSION_NAME
    }

    private fun vaiParaAlteraActivity() {
        binding.botaoAlterarDados.setOnClickListener {
            val intent = Intent(this, AlteraActivity::class.java)
            intent.putExtra("USUARIO", user)
            startActivity(intent)
//            alertDialogAltera()
        }
    }

//    private fun alertDialogAltera() {
//        val dialog = Dialog(this)
//        val teste: AlertDialogBinding = AlertDialogBinding.inflate(layoutInflater)
//        dialog.setContentView(teste.root)
//        dialog.show()
//        val builder = AlertDialog.Builder(this)
//        builder.setView(binding.root)
//        val botaoConfirmar = teste.buttonUpdate
//        val botaoRecusar = teste.buttonCancel
//        botaoConfirmar.setOnClickListener {
//
//        }
//        botaoRecusar.setOnClickListener { dialog.dismiss() }
//        dialog.show()
//    }


    private fun deslogarBotaoSair() {
        binding.botaoSair.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(resources.getString(R.string.setTitle))
            builder.setMessage(resources.getString(R.string.setMsg))
            builder.setPositiveButton(resources.getString(R.string.setPositiveButton)) { _, _ ->
                finish()
                vaiPara(LoginActivity::class.java)
            }
            builder.setNegativeButton(resources.getString(R.string.setNegativeButton)) { _, _ ->

            }.show()
        }
    }
}













package com.apptreino.meutreinamento.ui.activity

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.apptreino.meutreinamento.databinding.ActivityTelaAlteracaoBinding
import com.apptreino.meutreinamento.databinding.DialogProgressBarBinding
import com.apptreino.meutreinamento.model.User
import com.apptreino.meutreinamento.util.*
import com.apptreino.meutreinamento.util.EditTextMask
import com.apptreino.meutreinamento.webService.UsuarioRepositorio
import com.google.android.material.snackbar.Snackbar
import io.reactivex.observers.DisposableObserver
import java.util.*

class AlteraActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityTelaAlteracaoBinding.inflate(layoutInflater)
    }

    private val repositorio by lazy {
        UsuarioRepositorio()
    }

    private var user : User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        user = intent.extras?.getSerializable("USUARIO") as User?
        user?.let { user ->
            setDisplayInfoUser(user)
        }

        botaoCancelar()
        clicarNoBotaoAposEditar()
        configurarTextFields()

    }

    private fun clicarNoBotaoAposEditar() {
        binding.buttonUpdate.setOnClickListener {
                fazAlteracaoDeCampos()
            }
        }


    private fun fazAlteracaoDeCampos() {
        if (validarCampos()) {
            val cpf = binding.editCpf.text.toString()
            val name = binding.editNome.text.toString()
            val data = binding.editData.text.toString()
            val novaData = verificaData(data)
            val email = binding.editEmail.text.toString()
            val telefone = binding.editTelefone.text.toString()
            realizarAlteracaoDeInfos(cpf, name, DataUtil.data("yyyy-MM-dd", novaData), email, telefone)
            showDialoProgress()
        }
    }

    private fun verificaData(data: String): Date? {
        if (data.isNotEmpty()) {
            val novoNascimento = DataUtil.dataPaymentToDate(data)
            return novoNascimento
        }
        return null
    }

    private fun setDisplayInfoUser(user: User) {
        binding.editCpf.setText(user.cpf)
        binding.editNome.setText(user.name)
        binding.editData.setText(DataUtil.data("dd/MM/yyyy", user.birthDay))
        binding.editEmail.setText(user.email)
        binding.editTelefone.setText(user.phone)
    }

    private fun realizarAlteracaoDeInfos(cpf : String, name : String, data : String, email : String, telefone : String) {
        repositorio.alteracao(cpf, name, data, email, telefone, object : DisposableObserver<User>() {
            override fun onNext(t: User) {
                hideDialogProgress()
                Toast.makeText(this@AlteraActivity, "Alteração bem sucedida", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@AlteraActivity, InfoActivity::class.java)
                intent.putExtra("USUARIO_DOMINIO", t)
                intent.putExtra("TESTE", 1)
                startActivity(intent)
            }

            override fun onError(e: Throwable) {
                hideDialogProgress()
                Toast.makeText(this@AlteraActivity, "Alteração bem sucedida ${e.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onComplete() {

            }
        })
    }

    private fun configurarTextFields() {
        binding.editCpf.addTextChangedListener(EditTextMask.insert(binding.editCpf))
        binding.editData.addTextChangedListener(MaskUtil.insert(MaskUtil.DATA, binding.editData))
    }

    private fun validarCampos(): Boolean {
        if (binding.editCpf.text.isNullOrEmpty()) {
            binding.editCpf.error = "Informe um CPF para continuar o cadastro"
            return false
        }
        if (binding.editCpf.text!!.unmask().isCPF().not()) {
            binding.editCpf.error = "Informe um CPF/CNPJ válido para alterar o usuário"
            return false
        }

        if (binding.editData.text.isNullOrEmpty()) {
            binding.editData.error = "Campo data não pode estar vazio"
            return false
        }
        if (binding.editData.text!!.length < 10) {
            binding.editData.error = "Informe uma data adequada"
            return false
        }

        if (binding.editNome.text.isNullOrEmpty()) {
            binding.editNome.error = "Campo nome não pode estar vazio"
            return false
        }
        if (binding.editNome.text!!.length < 3) {
            binding.editNome.error = "Informe um nome válido para alterar o usuário"
            return false
        }

        if (binding.editEmail.text.isNullOrEmpty()) {
            binding.editEmail.error = "Campo email não pode estar vazio"
            return false
        }

        if (binding.editTelefone.text.isNullOrEmpty()) {
            binding.editTelefone.error = "Campo telefone não pode estar vazio"
            return false
        }

        if (binding.editTelefone.text!!.length < 12) {
            binding.editTelefone.error = "Campo telefone não pode estar vazio"
            return false
        }
        return true
    }

    private fun showDialoProgress() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideDialogProgress() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun botaoCancelar() {
        binding.buttonCancel.setOnClickListener {
            finish()
        }
    }

//    private fun String.toEditable() : Editable = Editable.Factory.getInstance().newEditable(this)

}
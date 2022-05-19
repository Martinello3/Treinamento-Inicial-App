package com.apptreino.meutreinamento.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.core.graphics.drawable.toDrawable
import com.apptreino.meutreinamento.R
import com.apptreino.meutreinamento.databinding.DialogProgressBarBinding

object CommunUtil {

    fun showLoadingDialog(context: Context): Dialog {

        val progressDialog = Dialog(context)

        progressDialog.let {
            it.show()
            it.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
            it.setContentView(R.layout.dialog_progress_bar)
            it.setCancelable(false)
            it.setCanceledOnTouchOutside(false)
            return it
        }
    }

}
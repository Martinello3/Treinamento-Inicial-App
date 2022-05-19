package com.apptreino.meutreinamento.util

import android.widget.EditText
import android.text.TextWatcher
import android.text.Editable
import java.lang.Exception

internal object EditTextMask {
    private const val maskCNPJ = "##.###.###/####-##"
    private const val maskCPF = "###.###.###-##"
    fun unmask(s: String): String {
        return s.replace("[^0-9]*".toRegex(), "").replace("-", "")
    }

    fun insert(editText: EditText): TextWatcher {
        return object : TextWatcher {
            var isUpdating = false
            var old = ""
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val str = unmask(s.toString())
                val mask = maskCPF
                var mascara = ""
                if (isUpdating) {
                    old = str
                    isUpdating = false
                    return
                }
                var i = 0
                for (m in mask.toCharArray()) {
                    if (m != '#' && str.length > old.length || m != '#' && str.length < old.length && str.length != i) {
                        mascara += m
                        continue
                    }
                    mascara += try {
                        str[i]
                    } catch (e: Exception) {
                        break
                    }
                    i++
                }
                isUpdating = true
                editText.setText(mascara)
                editText.setSelection(mascara.length)
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int,
                after: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {}
        }
    }

    private fun getDefaultMask(str: String): String {
        var defaultMask = maskCPF
        if (str.length > 11) {
            defaultMask = maskCNPJ
        }
        return defaultMask
    }
}
package com.apptreino.meutreinamento.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText


object MaskUtil {
    const val CPF = "###.###.###-##"
    const val CNPJ = "##.###.###/####-##"
    const val CELULAR = "(##) #####-####"
    const val PLACA = "###-####"
    const val CEP = "#####-###"
    const val DATA = "##/##/####"
    fun unmask(s: String): String {
        return s.replace("[^a-zA-Z0-9-]*".toRegex(), "")
    }

    fun insert(text: String, maskTypeProps: String): String {
        var text = text
        text = unmask(text)
        var i = 0
        val mascara = StringBuilder()
        for (m in maskTypeProps.toCharArray()) {
            if (m != '#') {
                mascara.append(m)
                continue
            }
            try {
                mascara.append(text[i])
            } catch (e: Exception) {
                break
            }
            i++
        }
        return mascara.toString()
    }

    fun insert(editText: EditText, maskTypeProps: String): TextWatcher {
        return object : TextWatcher {
            var isUpdating = false
            var oldValue = ""
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val value = unmask(s.toString())
                var maskAux = ""
                if (isUpdating) {
                    oldValue = value
                    isUpdating = false
                    return
                }
                var i = 0
                for (m in maskTypeProps.toCharArray()) {
                    if (m != '#' && value.length > oldValue.length || m != '#' && value.length < oldValue.length && value.length != i) {
                        maskAux += m
                        continue
                    }
                    maskAux += try {
                        value[i]
                    } catch (e: Exception) {
                        break
                    }
                    i++
                }
                isUpdating = true
                editText.setText(maskAux)
                editText.setSelection(maskAux.length)
            }

            override fun afterTextChanged(pEditable: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }
        }
    }

    fun unmaskNew(s: String): String {
        return s.replace("[.]".toRegex(), "").replace("[-]".toRegex(), "")
            .replace("[/]".toRegex(), "").replace("[(]".toRegex(), "")
            .replace("[)]".toRegex(), "").replace(" ".toRegex(), "")
            .replace(",".toRegex(), "")
    }

    private fun isASign(c: Char): Boolean {
        return if (c == '.' || c == '-' || c == '/' || c == '(' || c == ')' || c == ',' || c == ' ') {
            true
        } else {
            false
        }
    }

    fun insert(mask: String, ediTxt: EditText): TextWatcher {
        return object : TextWatcher {
            var isUpdating = false
            var old = ""
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val str = unmaskNew(s.toString())
                var mascara = ""
                if (isUpdating) {
                    old = str
                    isUpdating = false
                    return
                }
                var index = 0
                for (i in 0 until mask.length) {
                    val m = mask[i]
                    if (m != '#') {
                        if (index == str.length && str.length < old.length) {
                            continue
                        }
                        mascara += m
                        continue
                    }
                    mascara += try {
                        str[index]
                    } catch (e: Exception) {
                        break
                    }
                    index++
                }
                if (mascara.length > 0) {
                    var last_char = mascara[mascara.length - 1]
                    var hadSign = false
                    while (isASign(last_char) && str.length == old.length) {
                        mascara = mascara.substring(0, mascara.length - 1)
                        last_char = mascara[mascara.length - 1]
                        hadSign = true
                    }
                    if (mascara.length > 0 && hadSign) {
                        mascara = mascara.substring(0, mascara.length - 1)
                    }
                }
                isUpdating = true
                ediTxt.setText(mascara)
                ediTxt.setSelection(mascara.length)
            }

            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {}
        }
    }
}
package com.apptreino.meutreinamento.util

import android.text.Editable
import java.util.*

fun String. isCPF(): Boolean {
    if (this == "00000000000" || this == "11111111111" || this == "22222222222" || this == "33333333333" || this == "44444444444" || this == "55555555555" || this == "66666666666" || this == "77777777777" || this == "88888888888" || this == "99999999999" || this.length != 11) return false
    val dig10: Char
    val dig11: Char

    var sm: Int
    var i: Int
    var r: Int
    var num: Int
    var peso: Int
    return try {
        sm = 0
        peso = 10
        i = 0
        while (i < 9) {
            num = this[i].code - 48
            sm = sm + num * peso
            peso = peso - 1
            i++
        }
        r = 11 - sm % 11
        dig10 = if (r == 10 || r == 11) '0' else (r + 48).toChar()
        sm = 0
        peso = 11
        i = 0
        while (i < 10) {
            num = this[i].code - 48
            sm = sm + num * peso
            peso = peso - 1
            i++
        }
        r = 11 - sm % 11
        dig11 = if (r == 10 || r == 11) '0' else (r + 48).toChar()
        dig10 == this[9] && dig11 == this[10]
    } catch (erro: InputMismatchException) {
        false
    }
}

fun String.isCNPJ(): Boolean {
    if (this == "00000000000000" || this == "11111111111111" || this == "22222222222222" || this == "33333333333333" || this == "44444444444444" || this == "55555555555555" || this == "66666666666666" || this == "77777777777777" || this == "88888888888888" || this == "99999999999999" ||
        this.length != 14
    ) return false
    val dig13: Char
    val dig14: Char
    var sm: Int
    var i: Int
    var r: Int
    var num: Int
    var peso: Int
    return try {
        sm = 0
        peso = 2
        i = 11
        while (i >= 0) {
            num = (this[i].code - 48)
            sm = sm + num * peso
            peso = peso + 1
            if (peso == 10) peso = 2
            i--
        }
        r = sm % 11
        dig13 = if (r == 0 || r == 1) '0' else (11 - r + 48).toChar()
        sm = 0
        peso = 2
        i = 12
        while (i >= 0) {
            num = (this[i].code - 48)
            sm = sm + num * peso
            peso = peso + 1
            if (peso == 10) peso = 2
            i--
        }
        r = sm % 11
        dig14 = if (r == 0 || r == 1) '0' else (11 - r + 48).toChar()
        dig13 == this[12] && dig14 == this[13]
    } catch (erro: InputMismatchException) {
        false
    }
}

fun String.checkCPForCPNJ(s: String): Boolean {
    if(this.length == 11)
        return true
    return false
}

fun Editable.isCPF(): Boolean {
    return this.toString().isCPF()
}

fun Editable.isCNPJ(): Boolean {
    return this.toString().isCNPJ()
}

fun Editable.unmask(): String {
    return this.toString().unmask()
}

fun String.unmask(): String {
    return this.replace("[.]".toRegex(), "").replace("[-]".toRegex(), "").replace("[/]".toRegex(), "")
        .replace("[(]".toRegex(), "").replace("[)]".toRegex(), "").replace("[:]".toRegex(), "")
        .replace("[ ]".toRegex(), "")
}

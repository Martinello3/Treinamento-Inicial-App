package com.apptreino.meutreinamento.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object DataUtil {
    fun data(aData: Date): String {
        return data("dd/MM/yyyy HH:mm", aData)
    }

    @SuppressLint("SimpleDateFormat")
    fun data(aFormato: String, aDate: String?): String {
        val parse = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(aDate!!)
        return data(aFormato, parse)
    }

    @SuppressLint("SimpleDateFormat")
    fun dataPayment(aFormato: String, aDate: String?): String {
        if(aDate!!.contains("/")) {
            return dataPaymentII(aFormato, aDate)
        }
        val parse = SimpleDateFormat("yyyy-MM-dd").parse(aDate)
        return data(aFormato, parse)
    }

    @SuppressLint("SimpleDateFormat")
    fun dataPaymentToDate(aDate: String?): Date{
        if(aDate!!.contains("/")) {
            return SimpleDateFormat("dd/MM/yyyy").parse(aDate)!!
        }
        val parse = SimpleDateFormat("yyyy-MM-dd").parse(aDate)
        return parse!!
    }

    @SuppressLint("SimpleDateFormat")
    fun dataPaymentII(aFormato: String, aDate: String?): String {
        val parse = SimpleDateFormat("dd/MM/yyyy").parse(aDate!!)
        return data(aFormato, parse)
    }

    @SuppressLint("SimpleDateFormat")
    fun dataToDate(formato : String, aData: String): Date {
        return SimpleDateFormat(formato).parse(aData)!!
    }

    @SuppressLint("SimpleDateFormat")
    fun data(aFormato: String, aDate: String, aFormatoString : String): String {
        val parse = SimpleDateFormat(aFormatoString).parse(aDate)
        return data(aFormato, parse)
    }

    @SuppressLint("SimpleDateFormat")
    fun data(aData: String): Date {
        return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(aData)!!
    }

    @SuppressLint("SimpleDateFormat")
    fun data(aFormato: String, aData: Date?): String {
        return if (aData != null) SimpleDateFormat(aFormato).format(aData) else ""
    }

    fun data(aData: Date, aFormato: String): String {
        return data(aFormato, aData)
    }
}
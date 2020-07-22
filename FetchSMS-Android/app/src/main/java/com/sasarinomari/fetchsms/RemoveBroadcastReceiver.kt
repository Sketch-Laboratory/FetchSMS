package com.sasarinomari.fetchsms

import android.widget.Toast
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import java.lang.NullPointerException


class RemoveBroadcastReceiver : BroadcastReceiver() {
    companion object {
        const val eventName = "com.fetchsms.delete"
        const val param_MessageIndex = "messageIndex"
    }

    override fun onReceive(context: Context, intent: Intent) {
        try {
            val mi = intent.getStringExtra(param_MessageIndex)!!
            val index = mi.removePrefix("_").toInt()
            Toast.makeText(context, index.toString(), Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Intercepted", Toast.LENGTH_LONG).show()
        }

    }
}
package com.sasarinomari.fetchsms

import android.Manifest
import android.annotation.SuppressLint
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat


class MainActivity : AppCompatActivity() {
    companion object {
        private const val LOG_TAG = "MainActivity"
    }

    val permissions = arrayOf(
        Manifest.permission.READ_SMS,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )


    val receiver = RemoveBroadcastReceiver()

    override fun onResume() {
        super.onResume()
        registerReceiver(receiver, IntentFilter(RemoveBroadcastReceiver.eventName))
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PermissionHelper.activatePermission(this, permissions) {
            textView.text = "SMS 목록을 가져오는 중입니다."
            val messages = fetchSms()
            val array = listToJsonArray(messages)
            val path = writeToFile(array.toString())
        }
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun writeToFile(array: String) {
        val df = SimpleDateFormat("yyyy-MM-dd HH-mm-ss")
//        val fileName = "SMS ${df.format(Date())}.json"
        val fileName = "SMS.json"
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            val dir = this.getExternalFilesDir(null)!!
            if (!dir.exists()) if (!dir.mkdirs()) {
                Log.i(LOG_TAG, "mkdirs Failed")
                return
            }
            val file = File(dir, fileName)
            try {
                val fw = FileWriter(file, false)
                fw.write(array)
                fw.close()
                textView.text = "SMS 목록을 내보냈습니다.\nExport to: $file"
            } catch (e: IOException) {
                e.printStackTrace()
            }
            /*
            try {
                val os = FileOutputStream(file)
                os.write(array.toByteArray())
                os.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
             */
        } else {
            Log.d(LOG_TAG, "External Storage is not ready")
        }


    }

    private fun listToJsonArray(list: ArrayList<JSONObject>): JSONArray {
        val result = JSONArray()
        for (item in list) result.put(item)
        Log.i(LOG_TAG, result.toString())
        return result
    }

    @SuppressLint("Recycle")
    private fun fetchSms(): ArrayList<JSONObject> {
        val results = ArrayList<JSONObject>()
        val cursor = contentResolver.query(
            Uri.parse("content://sms/inbox"),
            null, null, null, null
        )

        if (cursor!!.moveToFirst()) {
            // must check the result to prevent exception
            do {
                val obj = JSONObject()
                for (idx in 0 until cursor.columnCount) {
                    obj.put(cursor.getColumnName(idx), cursor.getString(idx))
                }
                results.add(obj)
            } while (cursor.moveToNext())
        } else {
            // empty box, no SMS
            Log.i(LOG_TAG, "No SMS")
        }

        return results
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        PermissionHelper.onRequestPermissionsResult(this, permissions, requestCode, grantResults) {
            // fetchSms()
        }
        // super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}

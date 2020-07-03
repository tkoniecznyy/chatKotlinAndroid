package com.example.kotlinapp

import android.content.Intent
import android.os.Build.ID
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_edit.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

class EditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

    }

    //val buttonSend = findViewById<Button>(R.id.editButtonSend)

     fun replaceMsg(del: String){
        val json = JSONObject()
        json.put("content", editText.text)
        json.put("login", User.login)

        HttpTask {
            if (it == null) {
                println("connection error")
                return@HttpTask
            }
        }.execute("PUT", "http://tgryl.pl/shoutbox/message/$del", json.toString())
        println("http://tgryl.pl/shoutbox/message/$del")
        println("Login " + User.login)
        println("Content " + editText.text.toString())
    }

    fun binClicked(view: View) {

        val currentid = 0


        val url = "http://tgryl.pl/shoutbox/message/" + currentid
        var client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .delete()
            .build()
        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {

            }

            override fun onFailure(call: okhttp3.Call, e: IOException) {
            }
        })
        Handler().postDelayed({
        }, 500)
        val intent = Intent(this@EditActivity, MainActivity::class.java)
        startActivity(intent)
    }


}

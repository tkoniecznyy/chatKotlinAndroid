package com.example.kotlinapp

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class GalleryActivity : AppCompatActivity() {

    lateinit var nickname: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        nickname = findViewById(R.id.nicknameEditText);
        nickname.setText(loadData())
    }

    fun setLoginOnClick(view: View) {
        saveData()
        finish()
    }

    private fun saveData() {
        val sharedPreferences =
            getSharedPreferences("KEY", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val nick = nickname.text.toString()
        editor.putString("KEY_NICKNAME", nick)
        editor.apply()
    }

    private fun loadData(): String {
        val sharedPreferences =
            getSharedPreferences("KEY", Context.MODE_PRIVATE)
        return sharedPreferences.getString("KEY_NICKNAME", "")!!
    }
}

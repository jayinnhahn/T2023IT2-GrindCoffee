package com.example.androidprefinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val emailTextFieldValue = findViewById<EditText>(R.id.emailEditText)
        val passwordTextFieldValue = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)

        loginButton.setOnClickListener{
            Log.d("zzz", emailTextFieldValue.text.toString())
            Log.d("yyy", passwordTextFieldValue.text.toString())
        }

        /*val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()*/
    }
}
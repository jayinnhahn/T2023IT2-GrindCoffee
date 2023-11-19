package com.example.androidprefinal

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = Firebase.auth

        val login = findViewById<TextView>(R.id.loginTextView)
        login.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        val displayNameTextFieldValue = findViewById<EditText>(R.id.displayNameEditText)
        val emailTextFieldValue = findViewById<EditText>(R.id.emailEditText)
        val passwordTextFieldValue = findViewById<EditText>(R.id.passwordEditText)
        val registerButton = findViewById<Button>(R.id.registerButton)
        registerButton.setOnClickListener{
            val displayName = displayNameTextFieldValue.text.toString()
            val email = emailTextFieldValue.text.toString()
            val password = passwordTextFieldValue.text.toString()
            auth.createUserWithEmailAndPassword(email, password)

                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        if (user != null) {
                            Toast.makeText(
                                baseContext,
                                "Successfully Registered. ${user.email}",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }

                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Error: Registration failed!",
                            Toast.LENGTH_SHORT,
                        ).show()

                    }
                }

            /*val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()*/
        }
    }
}
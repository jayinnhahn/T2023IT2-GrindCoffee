package com.example.androidprefinal

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.res.Configuration
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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()
    private val itemCollectionName = "user"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val orientation = resources.configuration.orientation
        val layoutResId = if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            R.layout.activity_register_tablet
        } else {
            R.layout.activity_register
        }

        setContentView(layoutResId)

        // firebase
        auth = Firebase.auth
        val login = findViewById<TextView>(R.id.loginTextView)
        login.setOnClickListener{
            finish()
        }
        val displayNameTextFieldValue = findViewById<EditText>(R.id.displayNameEditText)
        val photoURLTextFieldValue = findViewById<EditText>(R.id.photoURLEditText)
        val emailTextFieldValue = findViewById<EditText>(R.id.emailEditText)
        val passwordTextFieldValue = findViewById<EditText>(R.id.passwordEditText)
        val registerButton = findViewById<Button>(R.id.registerButton)
        registerButton.setOnClickListener{
            val displayName = displayNameTextFieldValue.text.toString()
            val photoURL = photoURLTextFieldValue.text.toString()
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
                        val userMap = hashMapOf(
                            "Display Name" to displayName,
                            "Email" to email,
                            "PhotoURL" to photoURL
                        )
            // Add a new document with a generated id.

                        db.collection("user")
                            .add(userMap)
                            .addOnSuccessListener { documentReference ->
                                Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error adding document", e)
                            }
                        finish()

                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Error: Registration failed!",
                            Toast.LENGTH_SHORT,
                        ).show()

                    }
                }

        }
    }
}
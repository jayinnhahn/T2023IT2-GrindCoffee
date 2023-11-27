package com.example.androidprefinal

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

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()
    private val userCollectionName = "user"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val orientation = resources.configuration.orientation
        val layoutResId = if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            R.layout.activity_login_tablet
        } else {
            R.layout.activity_login
        }

        setContentView(layoutResId)


        auth = Firebase.auth

        val registerTextView = findViewById<TextView>(R.id.registerTextView)
        registerTextView.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
           startActivity(intent)
        }

        val emailTextFieldValue = findViewById<EditText>(R.id.emailEditText)
        val passwordTextFieldValue = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {
            val email = emailTextFieldValue.text.toString()
            val password = passwordTextFieldValue.text.toString()

            if (email != "" && password != "") {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success")
                            val user = auth.currentUser
                            if (user != null) {
                                Toast.makeText(
                                    baseContext,
                                    "Logged in as ${user.email}.",
                                    Toast.LENGTH_SHORT,
                                ).show()

                                val email = db.collection(userCollectionName)
                                    .whereEqualTo("Email", user.email)
                                    .get()
                                    .addOnSuccessListener { documents ->
                                        var PhotoURL = ""
                                        var DisplayName = ""
                                        for (document in documents) {
                                              PhotoURL = document.data["PhotoURL"].toString()
                                              DisplayName = document.data["Display Name"].toString()
                                        }
                                        val intent = Intent(this, MainActivity::class.java)
                                        intent.putExtra("PhotoURL", PhotoURL)
                                        intent.putExtra("DisplayName", DisplayName)

                                        startActivity(intent)
                                        // removes field values once logged in
                                        emailTextFieldValue.setText("")
                                        passwordTextFieldValue.setText("")
                                        // sets focus to emailTextField
                                        // so focus in it when logged out
                                        emailTextFieldValue.requestFocus()
                                    }
                                    .addOnFailureListener { exception ->
                                        Log.w(TAG, "Error getting user information", exception)
                                    }

                            } else {
                                Log.w(TAG, "User is not authenticated.")
                                Toast.makeText(
                                    baseContext,
                                    "User is not authenticated.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()

                        }
                    }

            }
             else {
                Log.w(TAG, "Field is missing.")
                Toast.makeText(
                    baseContext,
                    "A field is missing!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
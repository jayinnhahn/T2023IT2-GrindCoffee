package com.example.androidprefinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.type.Date

class AddItemActivity : AppCompatActivity() {
    private val menuCollectionName = "items"
//    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        val titleValue = findViewById<EditText>(R.id.newItemTitle)
        val imageUrlValue = findViewById<EditText>(R.id.newItemImageUrl)
        val priceValue = findViewById<EditText>(R.id.newItemPrice)
        val descriptionValue = findViewById<EditText>(R.id.newItemDescription)

        val addProductButton = findViewById<Button>(R.id.mainNewItemButton)
        val db = Firebase.firestore

        addProductButton.setOnClickListener {
            val name = titleValue.text.toString()
            val description = descriptionValue.text.toString()
            val price = priceValue.text.toString()
            val photoUrl = imageUrlValue.text.toString()

            if (name != "" && description != "" && price != "" && photoUrl != "") {
                val priceDouble = String.format("%.2f", price.toDouble()).toDouble()
                val data = hashMapOf(
                    "name" to name,
                    "description" to description,
                    "price" to priceDouble,
                    "photoUrl" to photoUrl,
                    "dateCreated" to FieldValue.serverTimestamp()
                )

                db.collection(menuCollectionName).document()
                    .set(data)
                    .addOnSuccessListener {
                        Toast.makeText(
                            baseContext,
                            "Added Product Successfully",
                            Toast.LENGTH_SHORT,
                        ).show()
                        finish()
                        val mainActivity = Intent(this, MainActivity::class.java)
                        startActivity(mainActivity)
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            baseContext,
                            "Addition of Product Failed",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
            }
        }
            }
    }









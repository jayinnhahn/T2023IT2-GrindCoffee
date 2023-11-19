package com.example.androidprefinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class ViewItemsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_items)

        val thisIntent: Intent = intent
        val title: String = thisIntent.getStringExtra("name").toString()

        val description: String = thisIntent.getStringExtra("description").toString()
        val imageUrl: String = thisIntent.getStringExtra("photoUrl").toString()
        val price: String = thisIntent.getStringExtra("price").toString()
//
//        val viewProductImage = findViewById<ImageView>(R.id.viewProductImage)
////        Glide.with(this).load(imageUrl).into(viewProductImage)
//
        val viewProductTitle = findViewById<TextView>(R.id.itemName)
        viewProductTitle.text = title

        val viewProductDescription = findViewById<TextView>(R.id.ItemDescription)
        viewProductDescription.text = description
//
        val viewProductPrice = findViewById<TextView>(R.id.itemPrice)
        viewProductPrice.text = price
//
//        val backButton = findViewById<Button>(R.id.backButton)

//        backButton.setOnClickListener {
//            finish()
//        }

    }

}

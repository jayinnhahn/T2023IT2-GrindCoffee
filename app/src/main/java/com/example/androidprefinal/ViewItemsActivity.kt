package com.example.androidprefinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class ViewItemsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_items)

        val thisIntent: Intent = intent
        val title: String = thisIntent.getStringExtra("title").toString()
        val description: String = thisIntent.getStringExtra("description").toString()
        val imageUrl: String = thisIntent.getStringExtra("photoUrl").toString()
        val price: String = thisIntent.getStringExtra("price").toString()

        Log.d("jayjay", "loaded the imageUrl" + imageUrl)

        val viewProductImage = findViewById<ImageView>(R.id.itemImage)
        Glide.with(this).load(imageUrl).into(viewProductImage)

        val viewProductTitle = findViewById<TextView>(R.id.itemName)
        viewProductTitle.text = title

        val viewProductDescription = findViewById<TextView>(R.id.ItemDescription)
        viewProductDescription.text = description

        val viewProductPrice = findViewById<TextView>(R.id.itemPrice)
        viewProductPrice.text = price
    }

}

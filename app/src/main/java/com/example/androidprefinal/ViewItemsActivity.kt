package com.example.androidprefinal

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class ViewItemsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_items)

        val orientation = resources.configuration.orientation
        val layoutResId = if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            R.layout.activity_view_items_tablet
        } else {
            R.layout.activity_view_items
        }

        setContentView(layoutResId)

        val thisIntent: Intent = intent
        val title: String = thisIntent.getStringExtra("title").toString()
        val description: String = thisIntent.getStringExtra("description").toString()
        val imageUrl: String = thisIntent.getStringExtra("imageUrl").toString()
        val price: String = thisIntent.getStringExtra("price").toString()

        val viewProductImage = findViewById<ImageView>(R.id.itemImage)
        Glide.with(this).load(imageUrl).into(viewProductImage)

        val viewProductTitle = findViewById<TextView>(R.id.itemName)
        viewProductTitle.text = title

        val viewProductDescription = findViewById<TextView>(R.id.ItemDescription)
        viewProductDescription.text = description

        val viewProductPrice = findViewById<TextView>(R.id.itemPrice)
        viewProductPrice.text = price

        val websiteUrl: String = "https://www.facebook.com/grindcoffeebardvo"

        val BuyNowButton = findViewById<Button>(R.id.buyNowButton)
        BuyNowButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl))
            startActivity(intent)
        }
    }

}

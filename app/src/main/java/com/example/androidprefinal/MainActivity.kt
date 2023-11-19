package com.example.androidprefinal
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.setMargins
import com.example.androidprefinal.R
import com.example.androidprefinal.ViewItemsActivity
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainActivity : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    private val itemCollectionName = "item"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val menuView = findViewById<GridLayout>(R.id.menuItemView)
//        val addButton = findViewById<lay>(R.layout.add_button)
        val db = Firebase.firestore
        db.collection(itemCollectionName)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val cardLayoutParams = GridLayout.LayoutParams()
                    val marginInPixels = resources.getDimensionPixelSize(R.dimen.card_margin)
                    cardLayoutParams.setMargins(marginInPixels, marginInPixels, marginInPixels, marginInPixels)

                    val title = document.data["name"].toString()
                    val description = document.data["description"].toString()
                    val imageUrl = document.data["photoUrl"].toString()
                    val priceTemp = document.data["price"].toString()
                    val price = String.format("Php.", priceTemp.toFloat())

                    val menuItemView = layoutInflater.inflate(R.layout.card_view, null)
                    menuItemView.layoutParams = cardLayoutParams

                    val titleTextView = menuItemView.findViewById<TextView>(R.id.textViewTitle)
                    val priceTextView = menuItemView.findViewById<TextView>(R.id.textViewPrice)

                    titleTextView.text = title
                    priceTextView.text = price

                    menuItemView.setOnClickListener {
                        val intent = Intent(this, ViewItemsActivity::class.java)
                        intent.putExtra("title", title)
                        intent.putExtra("description", description)
                        intent.putExtra("imageUrl", imageUrl)
                        startActivity(intent)
                    }

                    menuView.addView(menuItemView)
                }

                val buttonLayoutParams = GridLayout.LayoutParams()
                val marginInPixels = resources.getDimensionPixelSize(R.dimen.card_margin)
                buttonLayoutParams.setMargins(marginInPixels, marginInPixels, marginInPixels, marginInPixels)

                val buttonView = layoutInflater.inflate(R.layout.add_button, null)

                buttonView.setOnClickListener {
                    // Handle click action for the "Add" button
                    // Example: Add new item or perform some action
                    // This can be customized based on your requirements
                }

                buttonView.layoutParams = buttonLayoutParams
                menuView.addView(buttonView)
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }
    }
}
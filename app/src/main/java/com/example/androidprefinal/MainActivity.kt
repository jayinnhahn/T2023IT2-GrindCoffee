package com.example.androidprefinal
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
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

        val db = Firebase.firestore
        db.collection(itemCollectionName)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val description = document.data["description"].toString()
                    val imageUrl = document.data["photoUrl"].toString()
                    val priceTemp = document.data["price"].toString()
                    val price = String.format("Php.", priceTemp.toFloat())
                    val title = document.data["name"].toString()

                    val menuItemView = layoutInflater.inflate(R.layout.card_view, null)
                    // Set data to the UI elements in the inflated card
//                    val imageView = menuItemView.findViewById<ImageView>(R.id.header_image)
                    val titleTextView = menuItemView.findViewById<TextView>(R.id.textViewTitle)
                    val priceTextView = menuItemView.findViewById<TextView>(R.id.textViewPrice)

//                    Glide.with(this).load(imageUrl).placeholder(R.drawable.placeholder_image).into(imageView)

                    titleTextView.text = title
                    priceTextView.text = price

                    // Pass the data to the second activity
//                    val intent = Intent(this, ViewItemsActivity::class.java)
//                    intent.putExtra("title", title)
//                    intent.putExtra("description", description)
//                    intent.putExtra("imageUrl", imageUrl)
//                    startActivity(intent)

                    menuView.addView(menuItemView)
                }

            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }
    }
}
package com.example.androidprefinal
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.cardview.widget.CardView
import androidx.core.view.setMargins
import com.bumptech.glide.Glide
import com.example.androidprefinal.R
import com.example.androidprefinal.ViewItemsActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class   MainActivity : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    private val itemCollectionName = "item"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val orientation = resources.configuration.orientation
        val layoutResId = if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            R.layout.activity_main_tablet
        } else {
            R.layout.activity_main
        }

        setContentView(layoutResId)
        val menuView = findViewById<GridLayout>(R.id.menuItemView)
//        val addButton = findViewById<lay>(R.layout.add_button)
        val db = Firebase.firestore
        val logoutButton = findViewById<Button>(R.id.mainLogoutButton)

        val emailAddressView: String = Firebase.auth.currentUser?.email.toString()
        val userPhotoView: String = intent.getStringExtra("PhotoURL").toString()
        val DisplayNameString: String = intent.getStringExtra("DisplayName").toString()
        val userIconView = findViewById<ImageView>(R.id.userIcon)
        Log.d("jayjay123", userPhotoView)
        Glide.with(this)
            .load(userPhotoView)
            .placeholder(R.drawable.usericon)
            .dontAnimate()
            .into(userIconView)

        val emailLabel = findViewById<TextView>(R.id.emailAddressLabel)
        emailLabel.text =  emailAddressView

        var doubleBackToExitPressedOnce = false
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true )  {
            override fun handleOnBackPressed() {
                if (doubleBackToExitPressedOnce) {
                    finish()
                }
                else {
                    Toast.makeText(
                        baseContext,
                        "Press BACK again to Logout.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
                doubleBackToExitPressedOnce = true
                Handler(Looper.getMainLooper()).postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)


            }

        })



        logoutButton.setOnClickListener {
            Firebase.auth.signOut()
            finish()
        }

        userIconView.setOnClickListener {
            val viewUserActivity = Intent(this, ViewUserActivity::class.java)
            viewUserActivity.putExtra("imageUrl", userPhotoView)
            viewUserActivity.putExtra("displayName", DisplayNameString)
            viewUserActivity.putExtra("emailLabel", emailAddressView)
            startActivity(viewUserActivity)
        }

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
                    val menuItemView = layoutInflater.inflate(R.layout.card_view, null)

                    menuItemView.layoutParams = cardLayoutParams
                    val titleTextView = menuItemView.findViewById<TextView>(R.id.textViewTitle)
                    val priceTextView = menuItemView.findViewById<TextView>(R.id.textViewPrice)
                    val imageUrlView = menuItemView.findViewById<ImageView>(R.id.headerImage)
                    titleTextView.text = title
                    priceTextView.text = "₱" + priceTemp
                    Glide.with(this).load(imageUrl).dontAnimate().into(imageUrlView);

                    menuItemView.setOnClickListener {
                        val intent = Intent(this, ViewItemsActivity::class.java)
                        intent.putExtra("title", title)
                        intent.putExtra("description", description)
                        intent.putExtra("price", priceTemp)
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
                    val intent = Intent(this, AddItemActivity::class.java)
                    startActivity(intent)
                }

                buttonView.layoutParams = buttonLayoutParams
                menuView.addView(buttonView)
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }
    }
}
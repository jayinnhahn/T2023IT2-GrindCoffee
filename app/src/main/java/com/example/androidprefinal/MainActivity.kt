import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

        val db = Firebase.firestore
        db.collection(itemCollectionName)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val description = document.data["description"].toString()
                    val imageUrl = document.data["photoUrl"].toString()
                    val priceTemp = document.data["price"].toString()
                    val price = String.format("Php. %.2f", priceTemp.toFloat())
                    val title = document.data["name"].toString()


                    // Pass the data to the second activity
                    val intent = Intent(this, ViewItemsActivity::class.java)
                    intent.putExtra("title", title)
                    intent.putExtra("description", description)
                    intent.putExtra("imageUrl", imageUrl)
                    startActivity(intent)
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }
    }
}
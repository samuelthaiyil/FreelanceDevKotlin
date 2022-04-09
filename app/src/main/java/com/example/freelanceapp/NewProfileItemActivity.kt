package com.example.freelanceapp

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NewProfileItemActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_profile_item)

        val extras = intent.extras

        val uid = intent.extras?.getString("uid")!!
        val mode = intent.extras?.getString("mode")!!

        auth = Firebase.auth
        db = Firebase.firestore

        val title =  findViewById<TextView>(R.id.mode_title);
        val name = findViewById<TextView>(R.id.new_item_name);
        val desc = findViewById<TextView>(R.id.new_item_desc);
        val add = findViewById<Button>(R.id.add_item);

        add.setOnClickListener {
            if (mode == "Education") {
                db.collection("Users").document(uid).collection("Education").document().set(
                    hashMapOf(
                        "school" to name.text.toString(),
                        "description" to desc.text.toString()
                    )).addOnSuccessListener { doc ->
                    val intent = Intent(this, ProfileActivity::class.java)
                    intent.putExtra("uid", uid)
                    startActivity(intent)
                }
            } else {
                db.collection("Users").document(uid).collection("Projects").document().set(
                    hashMapOf(
                        "name" to name.text.toString(),
                        "description" to desc.text.toString()
                    )).addOnSuccessListener { doc ->
                    val intent = Intent(this, ProfileActivity::class.java)
                    intent.putExtra("uid", uid)
                    startActivity(intent)
                }
            }
        }

        title.text = "Add ".plus(mode)
    }
}
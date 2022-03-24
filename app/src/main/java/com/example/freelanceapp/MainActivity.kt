package com.example.freelanceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth
        db = Firebase.firestore

        val createButton = findViewById<Button>(R.id.sign_in_btn)
        val fullName = findViewById<TextView>(R.id.full_name_text)
        val email = findViewById<TextView>(R.id.email_text)
        val password = findViewById<TextView>(R.id.password_text)
        val confirmPassword = findViewById<TextView>(R.id.confirm_password_text)

        createButton.setOnClickListener {
              if (fullName.text.toString().isNotEmpty() &&
                  email.text.toString().isNotEmpty() &&
                  password.text.toString().isNotEmpty() &&
                  confirmPassword.text.toString().isNotEmpty()
             ) {
                 if (confirmPassword.text.toString() == password.text.toString()) {
                     auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                         .addOnCompleteListener(this) { task ->
                             if (task.isSuccessful) {
                                 db.collection("Users").document(task.result.user!!.uid).set(
                                     hashMapOf(
                                         "fullName" to fullName.text.toString(),
                                         "email" to fullName.text.toString()
                                     )
                                 ).addOnSuccessListener { doc ->
                                     val intent = Intent(this, LoginActivity::class.java)
                                     startActivity(intent)
                                 }.addOnFailureListener { e ->
                                         Toast.makeText(baseContext, "An error occurred creating the account. ",
                                             Toast.LENGTH_SHORT).show()
                                 }
                             } else {
                                 Toast.makeText(baseContext, "An error occurred creating the account. ",
                                     Toast.LENGTH_SHORT).show()
                             }
                         }
                 } else {
                     Toast.makeText(baseContext, "Password do not match. ",
                         Toast.LENGTH_SHORT).show()
                 }
            } else {
                  Toast.makeText(baseContext, "Not all fields have been entered. ",
                      Toast.LENGTH_SHORT).show()
              }
        }
    }
}
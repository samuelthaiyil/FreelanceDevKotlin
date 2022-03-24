package com.example.freelanceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth

        val createButton = findViewById<Button>(R.id.create_btn)
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
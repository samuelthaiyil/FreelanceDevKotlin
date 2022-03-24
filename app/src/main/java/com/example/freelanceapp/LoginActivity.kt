package com.example.freelanceapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val email = findViewById<TextView>(R.id.email_text)
        val password = findViewById<TextView>(R.id.password_text)
        val login = findViewById<Button>(R.id.sign_in_btn)

        auth = Firebase.auth

        login.setOnClickListener {

            auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, FeedActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            baseContext, "Couldn't sign you in. ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

        }
    }
}
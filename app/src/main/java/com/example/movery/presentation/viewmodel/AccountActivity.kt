package com.example.movery.presentation.viewmodel

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.movery.R
import com.example.movery.presentation.fragments.home.HomeFragment
import com.google.firebase.auth.FirebaseAuth

class AccountActivity : AppCompatActivity() {

    private lateinit var profileNameTextView: TextView
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        auth = FirebaseAuth.getInstance()

        profileNameTextView = findViewById(R.id.profileName)

        val logOutTextView = findViewById<TextView>(R.id.logOut)
        logOutTextView.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, AuthorizationActivity::class.java)
            startActivity(intent)
            finish()
        }

        val back = findViewById<ImageView>(R.id.back)
        back.setOnClickListener {
            val intent = Intent(this, HomeFragment::class.java)
            startActivity(intent)
        }

        val authorizationText = findViewById<TextView>(R.id.authorization)
        authorizationText.setOnClickListener {
            val intent = Intent(this, AuthorizationActivity::class.java)
            startActivity(intent)
        }

        val registrationText = findViewById<TextView>(R.id.registration)
        registrationText.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }

        val currentUser = auth.currentUser
        if(currentUser != null) {
            profileNameTextView.text = currentUser.email
        } else {
            profileNameTextView.text = getString(R.string.not_logged_in)
        }
    }
}

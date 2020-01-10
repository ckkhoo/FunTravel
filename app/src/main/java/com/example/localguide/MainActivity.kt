package com.example.localguide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonRegister.setOnClickListener {
            goToRegisterActivity()
        }

        buttonSignIn.setOnClickListener {
            goToSignInActivity()
        }
    }

    fun goToRegisterActivity() {
        val intentRegisterActivity = Intent(this, RegisterActivity::class.java)
        startActivity(intentRegisterActivity)
    }

    fun goToSignInActivity() {
        val intentSigninActivity = Intent(this, SigninActivity::class.java)
        startActivity(intentSigninActivity)
    }
}

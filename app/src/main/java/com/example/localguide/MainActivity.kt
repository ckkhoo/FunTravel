package com.example.localguide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        buttonRegister.setOnClickListener {
            goToTravelerGuideActicity()
        }

        buttonSignIn.setOnClickListener {
            goToSignInActivity()
        }
    }


    fun goToSignInActivity() {
        val intentSigninActivity = Intent(this, SigninActivity::class.java)
        startActivity(intentSigninActivity)
    }

    fun goToTravelerGuideActicity() {
        val intent = Intent(this, TravelerGuideActivity::class.java)
        startActivity(intent)
    }
}

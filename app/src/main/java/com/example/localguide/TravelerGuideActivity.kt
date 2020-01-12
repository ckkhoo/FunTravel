package com.example.localguide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_traveler_guide.*

class TravelerGuideActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_traveler_guide)

        buttonTraveler.setOnClickListener {
            goToRegisterActivity()
        }

        buttonGuide.setOnClickListener {
            goToRegisterGuideActivity()
        }
    }

    fun goToRegisterActivity() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    fun goToRegisterGuideActivity() {
        val intent = Intent(this, RegisterGuideActivity::class.java)
        startActivity(intent)
    }
}

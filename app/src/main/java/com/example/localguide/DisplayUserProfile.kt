package com.example.localguide

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_display_user_profile.*


class DisplayUserProfile : AppCompatActivity() {

    private lateinit var sharedPreferencesUID : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_user_profile)

        sharedPreferencesUID = getSharedPreferences(getString(R.string.pref_file_name), Context.MODE_PRIVATE)
        val userID = sharedPreferencesUID.getString(getString(R.string.userID_stored), getString(R.string.default_value))?:return
        val email = sharedPreferencesUID.getString(getString(R.string.email_stored), getString(R.string.default_value))?:return
        val name = sharedPreferencesUID.getString(getString(R.string.name_stored), getString(R.string.default_value))?:return
        val role = sharedPreferencesUID.getString(getString(R.string.role_stored), getString(R.string.default_value))?:return
        val image = sharedPreferencesUID.getString(getString(R.string.image_stored), getString(R.string.default_value))?:return

        textViewUserName.text = String.format("Name  : %s",  name)
        textView2.text = String.format("Email  : %s",  email)
        textView3.text = String.format("Role  : %s",  role)
        textView4.text = String.format("User ID  : %s",  userID)
        textView5.text = String.format("Image  : %s",  image)


    }
}

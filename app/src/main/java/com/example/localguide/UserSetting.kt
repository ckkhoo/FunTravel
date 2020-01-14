package com.example.localguide

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_user_setting.*
import kotlinx.android.synthetic.main.activity_user_setting.imageViewUser
import kotlinx.android.synthetic.main.activity_user_setting.textViewName
import java.net.URL

class UserSetting : AppCompatActivity() {

    private lateinit var sharedPreferencesUID : SharedPreferences
    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_user_setting)

        sharedPreferencesUID = getSharedPreferences(getString(R.string.pref_file_name), Context.MODE_PRIVATE)
        val email = sharedPreferencesUID.getString(getString(R.string.email_stored), getString(R.string.default_value))?:return
        val name = sharedPreferencesUID.getString(getString(R.string.name_stored), getString(R.string.default_value))?:return

        textViewEmail.text = email
        textViewName.text = name

        val EditProfile = findViewById<TextView>(R.id.textViewEditProfile)
        EditProfile.setOnClickListener {
            startActivity(Intent(this@UserSetting, User_Management::class.java))
        }

        textViewFavourite2.setOnClickListener {
            startActivity(Intent(this@UserSetting, SettingActivity::class.java))
        }

        textViewSignOut.setOnClickListener {
            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Signing out...")
            progressDialog.setCancelable(false)
            progressDialog.show()
            mAuth.signOut()
            finish()

            startActivity(Intent(this@UserSetting, MainActivity::class.java))
        }

        val bottomNavigation : BottomNavigationView = findViewById(R.id.btm_nav)
        bottomNavigation.setOnNavigationItemSelectedListener {item->
            when(item.itemId){
                R.id.home->{
                    startActivity(Intent(this@UserSetting, homepage::class.java))
                }
                R.id.profile->{
                    startActivity(Intent(this@UserSetting, UserSetting::class.java))
                }

            }
            true
        }
        lateinit var sDatabase: DatabaseReference
        //lateinit var userDatabase: DatabaseReference

        sDatabase = FirebaseDatabase.getInstance().getReference("Countries")

        sDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val imgUrl = dataSnapshot.child("Malaysia/imageCountry").value.toString()
                //val userName = dataSnapshot.child("User").getValue().toString()
                //val email = dataSnapshot.child("User/name").value.toString()
                //textViewName.text = userName
                //textViewEmail.text=email
                putImage(imgUrl, imageViewUser)
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

    }
    fun putImage(imageUrl: String,imageViewID:ImageView)
    {
        try {
            val thread = Thread(Runnable {
                try {
                    val url = URL(imageUrl)
                    val bmp =
                        BitmapFactory.decodeStream(url.openConnection().getInputStream())
                    runOnUiThread(Runnable { imageViewID.setImageBitmap(bmp) })


                } catch (e: java.lang.Exception) {
                    //textView.text = e.toString()
                }
            })

            thread.start()

        } catch (ex: Exception) {
            //textView.text = ex.toString()
        }
    }
}
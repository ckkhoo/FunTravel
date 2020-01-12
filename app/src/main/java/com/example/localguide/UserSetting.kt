package com.example.localguide

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_user_setting.*
import java.net.URL

class UserSetting : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_user_setting)

        val EditProfile = findViewById<TextView>(R.id.textViewEditProfile)
        EditProfile.setOnClickListener {
            startActivity(Intent(this@UserSetting, User_Management::class.java))
        }

        val bottomNavigation : BottomNavigationView = findViewById(R.id.btm_nav)
        bottomNavigation.setOnNavigationItemSelectedListener {item->
            when(item.itemId){
                R.id.home->{
                    startActivity(Intent(this@UserSetting, User_Management::class.java))
                }
                R.id.profile->{
                    startActivity(Intent(this@UserSetting, homepage::class.java))
                }

            }
            true
        }
        lateinit var sDatabase: DatabaseReference
        lateinit var userDatabase: DatabaseReference

        sDatabase = FirebaseDatabase.getInstance().getReference("Countries")



        sDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val imgUrl = dataSnapshot.child("Malaysia/imageCountry").value.toString()
                val userName = dataSnapshot.child("User").getValue().toString()
                val email = dataSnapshot.child("User/name").value.toString()
                textViewName.text = userName
                textViewEmail.text=email
                putImage(imgUrl, imageViewUser)
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        });

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
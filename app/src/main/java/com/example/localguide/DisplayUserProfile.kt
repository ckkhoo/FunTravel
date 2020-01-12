package com.example.localguide

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_display_user_profile.*


class DisplayUserProfile : AppCompatActivity() {

    private lateinit var databaseRef: DatabaseReference
    private lateinit var sharedPreferencesUID : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_user_profile)

        sharedPreferencesUID = getSharedPreferences(getString(R.string.pref_file_name), Context.MODE_PRIVATE)
        val userID = sharedPreferencesUID.getString(getString(R.string.userID), getString(R.string.default_UserID))?:return

        databaseRef = FirebaseDatabase.getInstance().getReference("User")

        databaseRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if(dataSnapshot?.exists()) {
                    //val user = dataSnapshot.child(uid).getValue()
                    //textViewUserName.text = sharedPreferencesUID.getString("userID", "")
                    for(u: DataSnapshot in dataSnapshot.children.iterator()) {
                        if(u.key!!.equals(userID)) {
                            textViewUserName.text = String.format("Name  : %s",  u.child("name").getValue())
                            return
                        }
                    }
                }
                else {
                    Toast.makeText(applicationContext,"Sorry, record not found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,"Sorry, record not found", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

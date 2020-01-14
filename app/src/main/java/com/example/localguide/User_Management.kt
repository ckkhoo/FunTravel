package com.example.localguide

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_display_user_profile.*
import kotlinx.android.synthetic.main.activity_user__management.*

class User_Management : AppCompatActivity() {

    private lateinit var databaseRef: DatabaseReference
    private lateinit var sharedPreferencesUID : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user__management)

        sharedPreferencesUID = getSharedPreferences(getString(R.string.pref_file_name), Context.MODE_PRIVATE)
        val userID = sharedPreferencesUID.getString(getString(R.string.userID_stored), getString(R.string.default_value))?:return

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        databaseRef = FirebaseDatabase.getInstance().getReference("User")
        databaseRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if(dataSnapshot?.exists()) {
                    //val user = dataSnapshot.child(uid).getValue()
                    //textViewUserName.text = sharedPreferencesUID.getString("userID", "")
                    for(u: DataSnapshot in dataSnapshot.children.iterator()) {
                        if(u.key!!.equals(userID)) {
                            progressDialog.dismiss()
                            editTextEditName.setText(u.child("name").getValue().toString())
                            return
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,"Sorry, error: " + error, Toast.LENGTH_SHORT).show()
            }
        })

        buttonSave.setOnClickListener {
            val name = editTextEditName.text.toString().trim()
            val progressDialog = ProgressDialog(this)
            val intent = Intent(this, UserSetting::class.java)

            progressDialog.setMessage("Updating...")
            progressDialog.setCancelable(false)
            progressDialog.show()

            databaseRef.child(userID).child("name").setValue(name).addOnCompleteListener {
                progressDialog.dismiss()
                Toast.makeText(applicationContext,"Updated SuccessFully", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }
        }

        buttonCancel.setOnClickListener {
            val intent = Intent(this, UserSetting::class.java)
            startActivity(intent)
        }
    }


}

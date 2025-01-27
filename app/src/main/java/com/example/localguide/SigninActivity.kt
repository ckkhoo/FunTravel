package com.example.localguide

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_signin.*
import android.widget.Toast
import com.google.firebase.auth.AuthResult
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*


class SigninActivity : AppCompatActivity() {

    private val mAuth = FirebaseAuth.getInstance()
    private val user = mAuth.currentUser
    private lateinit var databaseRef: DatabaseReference
    private lateinit var sharedPreferencesUID : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        buttonSignIn.setOnClickListener {
            signInAcount()
        }

        textViewForgot.setOnClickListener {
            goToForgotPasswordActivity()
        }

        textViewSignin.setOnClickListener {
            goToTravelerGuideActicity()
        }
    }

    fun signInAcount() {
        val email = editTextEmail.text.toString().trim()
        val password = editTextPassword.text.toString()
        val intent = Intent(this, homepage::class.java)

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Signing In...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, object : OnCompleteListener<AuthResult> {
                override fun onComplete(task: Task<AuthResult>) {
                    if (task.isSuccessful()) {
                        //Toast.makeText(applicationContext, "Signed In", Toast.LENGTH_SHORT).show()

                        databaseRef = FirebaseDatabase.getInstance().getReference("User")

                        databaseRef.addValueEventListener(object : ValueEventListener {

                            override fun onDataChange(dataSnapshot: DataSnapshot) {

                                if(dataSnapshot?.exists()) {
                                    //val uid = intent.getStringExtra(SigninActivity.EXTRA_UID)
                                    //val user = dataSnapshot.child(uid).getValue()
                                    //textViewUserName.text = user?.name
                                    sharedPreferencesUID = getSharedPreferences(getString(R.string.pref_file_name), Context.MODE_PRIVATE)
                                    for(u: DataSnapshot in dataSnapshot.children.iterator()) {
                                        //if(u.child("role").equals("Traveler")) {
                                        //Log.d("SigninActivity", String.format("Role  : %s",  u.child("role").getValue()))
                                        // break
                                        //}
                                        val uid= user?.uid

                                        if(u.key!!.equals(uid)) {
                                            //Log.d("SigninActivity", String.format("Role  : %s",  u.child("role").getValue()))
                                            if(u.child("role").getValue()!!.equals("Traveler")) {
                                                Toast.makeText(applicationContext,"Sign in success", Toast.LENGTH_SHORT).show()

                                                with(sharedPreferencesUID.edit()) {
                                                    putString(getString(R.string.userID_stored), uid)
                                                    putString(getString(R.string.email_stored), u.child("email").getValue().toString())
                                                    putString(getString(R.string.image_stored), u.child("image").getValue().toString())
                                                    putString(getString(R.string.name_stored), u.child("name").getValue().toString())
                                                    putString(getString(R.string.role_stored), u.child("role").getValue().toString())
                                                    apply()
                                                    progressDialog.dismiss()
                                                    startActivity(intent)
                                                }
                                            }
                                            else if(u.child("role").getValue()!!.equals("Guide")) {
                                                Toast.makeText(applicationContext,"Sign in success", Toast.LENGTH_SHORT).show()

                                                //intent.putExtra(EXTRA_UID, uid)
                                                with(sharedPreferencesUID.edit()) {
                                                    putString(getString(R.string.userID_stored), uid)
                                                    putString(getString(R.string.email_stored), u.child("email").getValue().toString())
                                                    putString(getString(R.string.image_stored), u.child("image").getValue().toString())
                                                    putString(getString(R.string.name_stored), u.child("name").getValue().toString())
                                                    putString(getString(R.string.role_stored), u.child("role").getValue().toString())
                                                    apply()
                                                    progressDialog.dismiss()
                                                    startActivity(intent)
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                progressDialog.dismiss()
                                Toast.makeText(applicationContext,"Sorry, error: " + error, Toast.LENGTH_SHORT).show()
                            }
                        })

                    } else {
                        progressDialog.dismiss()
                        Toast.makeText(applicationContext, "Sorry, email or password incorrect. Please type again", Toast.LENGTH_SHORT).show()
                    }

                }
            })
    }

    fun goToForgotPasswordActivity() {
        val intentForgotPasswordActivity = Intent(this, UserForgotPasswordActivity::class.java)
        startActivity(intentForgotPasswordActivity)
    }

    fun goToTravelerGuideActicity() {
        val intent = Intent(this, TravelerGuideActivity::class.java)
        startActivity(intent)
    }

}

package com.example.localguide

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.localguide.Model.User
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_register.*
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import com.google.firebase.auth.AuthResult
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task


class RegisterActivity : AppCompatActivity() {

    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        buttonJoin.setOnClickListener {
            registerAccount()
        }
    }

    fun registerAccount() {
        val databaseRef = FirebaseDatabase.getInstance().getReference("User")
        val email = editTextEmail.text.toString()
        val name = editTextName.text.toString()
        val password = editTextPassword.text.toString()

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Signing Up...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, object : OnCompleteListener<AuthResult> {
                override fun onComplete(task: Task<AuthResult>) {
                    if (task.isSuccessful()) {
                        val userAuth = mAuth.currentUser
                        val userID = userAuth?.uid
                        val user = User(userID.toString(), email, name, password)
                        progressDialog.dismiss()
                        databaseRef.child(userID.toString()).setValue(user).addOnCompleteListener {
                            Toast.makeText(applicationContext,"Added", Toast.LENGTH_SHORT).show()
                            goToMainActivity()
                        }

                    } else {
                        progressDialog.dismiss()
                        Toast.makeText(applicationContext,"Sorry", Toast.LENGTH_SHORT).show()
                    }
                }

            })
    }

    fun goToMainActivity() {
        val intentRegisterActivity = Intent(this, MainActivity::class.java)
        startActivity(intentRegisterActivity)
    }

}

package com.example.localguide

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.localguide.Model.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.buttonJoin
import kotlinx.android.synthetic.main.activity_register.editTextEmail
import kotlinx.android.synthetic.main.activity_register.editTextName
import kotlinx.android.synthetic.main.activity_register.editTextPassword
import kotlinx.android.synthetic.main.activity_register_guide.*
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Patterns
import java.util.regex.Pattern


class RegisterGuideActivity : AppCompatActivity() {

    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_guide)

        buttonJoin.setOnClickListener {
            registerGuideAccount()
        }

        textViewSigninGuide.setOnClickListener {
            goToSignInActivity()
        }
    }

    fun registerGuideAccount() {
        val databaseRef = FirebaseDatabase.getInstance().getReference("User")
        val email = editTextEmail.text.toString().trim()
        val name = editTextName.text.toString().trim()
        val password = editTextPassword.text.toString().trim()
        val conPass = editTextCPass.text.toString().trim()

        if(email.isEmpty() || name.isEmpty() ||password.isEmpty() || conPass.isEmpty())
        {
            Toast.makeText(applicationContext,"Incompletely field filling in", Toast.LENGTH_SHORT).show()

        }else if(!password.equals(conPass))
        {
            Toast.makeText(applicationContext,"Password doesn't match", Toast.LENGTH_SHORT).show()

        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            Toast.makeText(applicationContext,"Email doesn't match", Toast.LENGTH_SHORT).show()
        }
        else if(!LETTER.matcher(name).matches()) {
            Toast.makeText(this, "Name should be characters only", Toast.LENGTH_LONG).show();
        }
        else if(!PASSWORD_PATTERN.matcher(password).matches()) {
            Toast.makeText(this, "Password should be at least 1 digit, 1 lower case, 1 upper case, 1 special character, and 8 characters", Toast.LENGTH_LONG).show()
        }
        else {

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
                            val role = "Guide"
                            val image = "No image"
                            val user = User(userID.toString(), email, name, role, image)
                            progressDialog.dismiss()
                            databaseRef.child(userID.toString()).setValue(user).addOnCompleteListener {
                                Toast.makeText(applicationContext,"Added SuccessFully", Toast.LENGTH_SHORT).show()
                                goToMainActivity()
                            }

                        } else {
                            progressDialog.dismiss()
                            Toast.makeText(applicationContext,"Sorry, this email has been used", Toast.LENGTH_SHORT).show()
                        }
                    }

                })
        }

    }

    fun goToMainActivity() {
        val intentRegisterActivity = Intent(this, MainActivity::class.java)
        startActivity(intentRegisterActivity)
    }

    fun goToSignInActivity() {
        val intentSigninActivity = Intent(this, SigninActivity::class.java)
        startActivity(intentSigninActivity)
    }

    companion object {
        val PASSWORD_PATTERN =
            Pattern.compile("^" + "(?=.*[0-9])" + "(?=.*[a-z])" + "(?=.*[A-Z])" + "(?=.*[!@#$%^&+=])" + ".{8,}" + "$")
        val LETTER = Pattern.compile("[a-zA-Z\\s]+")
    }
}

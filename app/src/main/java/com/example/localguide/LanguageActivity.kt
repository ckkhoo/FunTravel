package com.example.localguide

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import java.util.*

class LanguageActivity : AppCompatActivity() {

    lateinit var mBtn : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocate()
        setContentView(R.layout.activity_language)


        val actionBar = supportActionBar
        actionBar!!.title=resources.getString(R.string.app_name)

        mBtn= findViewById(R.id.mChangeLang)
        mBtn.setOnClickListener {

            showChangeLang()
        }
    }
    private fun showChangeLang() {
        val listItmes = arrayOf("Chinese","Malay","English")

        val mBuilder = AlertDialog.Builder(this@LanguageActivity)
        mBuilder.setTitle("Choose Language")
        mBuilder.setSingleChoiceItems(listItmes, -1)  { dialog, which->
            if(which==0){
                setLocate("ms")
                recreate()
            }
            else if(which==1){
                setLocate("zh")
                recreate()
            }
            else if ( which == 2){
                setLocate("en")
                recreate()
            }
            dialog.dismiss()
        }
        val mDialog = mBuilder.create()
        mDialog.show()

    }
    private fun setLocate(Lang : String){
        val locale = Locale(Lang)

        Locale.setDefault(locale)
        val config = Configuration()
        config.locale=locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        val editor = getSharedPreferences("Setting", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", Lang)
        editor.apply()

    }

    private fun loadLocate(){
        val sharedPreferences = getSharedPreferences ( "Setting", Activity.MODE_PRIVATE)
        val language= sharedPreferences.getString("My_Lang","")
        if (language != null) {
            setLocate(language)
        }
    }
}

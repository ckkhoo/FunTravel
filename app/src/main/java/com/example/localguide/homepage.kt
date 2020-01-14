package com.example.localguide
 

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_homepage.*
import org.json.JSONArray
import org.json.JSONObject

class homepage : AppCompatActivity() {

    lateinit var countryList: ArrayList<Country>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        countryList = ArrayList<Country>()
        getAllCountry()
        onItemClick()
        val bottomNavigation : BottomNavigationView = findViewById(R.id.btm_nav)
        bottomNavigation.setOnNavigationItemSelectedListener {item->
            when(item.itemId){
                R.id.home->{
                    startActivity(Intent(this@homepage, homepage::class.java))
                }
                R.id.profile->{
                    startActivity(Intent(this@homepage,UserSetting::class.java))
                }

            }
            true
        }
    }
    private fun onItemClick(){

        val china=findViewById<TextView>(R.id.textViewChina)
        val singapore=findViewById<TextView>(R.id.textViewSinagpore)
        val thailand=findViewById<TextView>(R.id.textViewThailand)
        val london=findViewById<TextView>(R.id.textViewLondon)
        val malaysia=findViewById<TextView>(R.id.textViewMalaysia)

        thailand.setOnClickListener() {

            textViewThailand.setText("Thailand")
            val text1=textViewThailand.getText()
            val intentcountryDetails= Intent(this , CountryDetails::class.java)
            intentcountryDetails.putExtra("name",text1)
            startActivity(intentcountryDetails)

        }
        china.setOnClickListener() {

            textViewChina.setText("China")
            val china1=textViewChina.getText()
            val intentcountryDetails= Intent(this , CountryDetails::class.java)
            intentcountryDetails.putExtra("name",china1)
            startActivity(intentcountryDetails)

        }
        london.setOnClickListener() {

            textViewLondon.setText("London")
            val London=textViewLondon.getText()
            val intentcountryDetails= Intent(this , CountryDetails::class.java)
            intentcountryDetails.putExtra("name",London)
            startActivity(intentcountryDetails)

        }
        malaysia.setOnClickListener() {

            textViewMalaysia.setText("Malaysia")
            val malaysia1=textViewMalaysia.getText()
            val intentcountryDetails= Intent(this , CountryDetails::class.java)
            intentcountryDetails.putExtra("name",malaysia1)
            startActivity(intentcountryDetails)

        }
        singapore.setOnClickListener() {

            textViewSinagpore.setText("Singapore")
            val singapore1=textViewSinagpore.getText()
            val intentcountryDetails= Intent(this , CountryDetails::class.java)
            intentcountryDetails.putExtra("name",singapore1)
            startActivity(intentcountryDetails)

        }
    }
    private fun getAllCountry() {
        val url = getString(R.string.url_server) + getString(R.string.url_read_all_country)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                // Process the JSON
                try{
                    if(response != null){
                        val strResponse = response.toString()
                        val jsonResponse  = JSONObject(strResponse)
                        val jsonArray: JSONArray = jsonResponse.getJSONArray("records")
                        val size: Int = jsonArray.length()
                        for(i in 0..size-1){
                            var jsonCountry: JSONObject = jsonArray.getJSONObject(i)
                            var country: Country = Country(
                                jsonCountry.getString("name"),
                                jsonCountry.getString("description"),
                                jsonCountry.getString("ethnicity"),
                                jsonCountry.getString("etiquette"),
                                jsonCountry.getString("language"),
                                jsonCountry.getString("religion")
                            )

                            countryList.add(country)
                        }
                    }
                }catch (e:Exception){
                    Log.d("Main", "Response: %s".format(e.message.toString()))

                }
            },
            Response.ErrorListener { error ->
                Log.d("Main", "Response: %s".format(error.message.toString()))
            }
        )

        //Volley request policy, only one time request
        jsonObjectRequest.retryPolicy = DefaultRetryPolicy (
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            0, //no retry
            1f
        )

        //Access the RequestQueue through singleton class
        WebhostSingleton.getInstace(this).addToRequestQueue(jsonObjectRequest)
    }

}

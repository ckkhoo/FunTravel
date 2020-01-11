package com.example.localguide
 

import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONArray
import org.json.JSONObject

class homepage : AppCompatActivity() {

    lateinit var countryList: ArrayList<Country>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        countryList = ArrayList<Country>()
        getAllCountry()
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
                                jsonCountry.getString("country_id"),
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

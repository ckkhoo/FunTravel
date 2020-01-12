package com.example.localguide

import android.app.Application
import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONArray
import org.json.JSONObject

class CountryViewModel(application: Application) : AndroidViewModel(application){
    //val countryListLive: MutableLiveData<ArrayList<Country>>()
    //var countryList = ArrayList<Country>()

//    init {
//        //getAllCountry()
//        //countryListLive.value = countryList
//    }

//    private fun getAllCountry() {
//        val url = Resources.getSystem().getString(R.string.url_server) +
//                Resources.getSystem().getString(R.string.url_read_all_country)
//
//        val jsonObjectRequest = JsonObjectRequest(
//            Request.Method.GET, url, null,
//            Response.Listener { response ->
//                // Process the JSON
//                try{
//                    if(response != null){
//                        val strResponse = response.toString()
//                        val jsonResponse  = JSONObject(strResponse)
//                        val jsonArray: JSONArray = jsonResponse.getJSONArray("records")
//                        val size: Int = jsonArray.length()
//                        for(i in 0..size-1){
//                            var jsonCountry: JSONObject = jsonArray.getJSONObject(i)
//                            var country: Country = Country(
//                                jsonCountry.getString("country_id"),
//                                jsonCountry.getString("name"),
//                                jsonCountry.getString("description"),
//                                jsonCountry.getString("ethnicity"),
//                                jsonCountry.getString("etiquette"),
//                                jsonCountry.getString("language"),
//                                jsonCountry.getString("religion")
//                            )
//
//                            countryList.add(country)
//                        }
//                    }
//                }catch (e:Exception){
//                    Log.d("Main", "Response: %s".format(e.message.toString()))
//
//                }
//            },
//            Response.ErrorListener { error ->
//                Log.d("Main", "Response: %s".format(error.message.toString()))
//            }
//        )
//
//        //Volley request policy, only one time request
//        jsonObjectRequest.retryPolicy = DefaultRetryPolicy (
//            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
//            0, //no retry
//            1f
//        )
//
//        //Access the RequestQueue through singleton class
//        WebhostSingleton.getInstance(getApplication()).addToRequestQueue(jsonObjectRequest)
//    }
}
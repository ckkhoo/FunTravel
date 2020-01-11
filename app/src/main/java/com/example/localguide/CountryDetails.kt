package com.example.localguide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_country_details.*
import org.json.JSONObject

class CountryDetails : AppCompatActivity() {
    lateinit var country: Country

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_details)

        getOneCountry("9")
    }

    private fun getOneCountry(country_id: String) {
        val url = getString(R.string.url_server) + getString(R.string.url_read_one_country) + "?id=" + country_id

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                //Process the JSON
                try {
                    if (response != null) {
                        val strResponse = response.toString()
                        val jsonResponse = JSONObject(strResponse)
                        country = Country(
                            jsonResponse.getString("country_id"),
                            jsonResponse.getString("name"),
                            jsonResponse.getString("description"),
                            jsonResponse.getString("ethnicity"),
                            jsonResponse.getString("etiquette"),
                            jsonResponse.getString("language"),
                            jsonResponse.getString("religion")
                        )

                        if (country.name != null) {
                            textViewCountry.text = country.name
                            textViewCountry.visibility = View.VISIBLE

                            textViewAbout.text = country.description
                            textViewAboutTitle.visibility = View.VISIBLE
                            textViewAbout.visibility = View.VISIBLE

                            textViewEthnicity.text = country.ethnicity
                            textViewEthnicityTitle.visibility = View.VISIBLE
                            textViewEthnicity.visibility = View.VISIBLE

                            textViewEtiquette.text = country.etiquette
                            textViewEtiquetteTitle.visibility = View.VISIBLE
                            textViewEtiquette.visibility = View.VISIBLE

                            textViewLanguage.text = country.language
                            textViewLanguageTitle.visibility = View.VISIBLE
                            textViewLanguage.visibility = View.VISIBLE

                            textViewReligion.text = country.religion
                            textViewReligionTitle.visibility = View.VISIBLE
                            textViewReligion.visibility = View.VISIBLE
                        } else {
                            textViewCountry.text = "Null"
                            textViewCountry.visibility = View.VISIBLE
                        }
                    }
                } catch (e: Exception) {
                    Log.d("Volley", "Response: %s".format(e.message.toString()))
                }
            },
            Response.ErrorListener { error ->
                Log.e("Volley", "Response: %s".format(error.message.toString()))
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

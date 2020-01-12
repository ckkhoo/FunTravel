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
import kotlinx.android.synthetic.main.fragment_edit_country.*
import org.json.JSONObject

class CountryDetails : AppCompatActivity() {
    lateinit var country: Country
    private val manager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_details)

        getOneCountry("Singapore")

        imageViewEdit.setOnClickListener {
            makeInvisible()
            val transaction = manager.beginTransaction()
            val fragment = EditCountryFragment()
            transaction.replace(R.id.country_container, fragment)
            transaction.commit()
        }

        imageViewBack.setOnClickListener {
            finish()
        }
    }

    private fun getOneCountry(country_name: String) {
        val url = getString(R.string.url_server) + getString(R.string.url_read_one_country) + "?name=" + country_name

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                //Process the JSON
                try {
                    if (response != null) {
                        val strResponse = response.toString()
                        val jsonResponse = JSONObject(strResponse)
                        country = Country(
                            jsonResponse.getString("name"),
                            jsonResponse.getString("description"),
                            jsonResponse.getString("ethnicity"),
                            jsonResponse.getString("etiquette"),
                            jsonResponse.getString("language"),
                            jsonResponse.getString("religion")
                        )
                        if (country != null) {
                            setCountryText()
                            makeVisible()
                        }
                    }
                } catch (e: Exception) {
                    Log.d("Volley", "Response: %s".format(e.message.toString()))
                }
            },
            Response.ErrorListener { error ->
                Log.e("Volley", "Response: %s".format(error.message.toString()))

                textViewCountry.text = "Null"
                textViewCountry.visibility = View.VISIBLE
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

    private fun makeVisible() {
        textViewCountry.visibility = View.VISIBLE
        textViewAboutTitle.visibility = View.VISIBLE
        textViewAbout.visibility = View.VISIBLE
        textViewEthnicityTitle.visibility = View.VISIBLE
        textViewEthnicity.visibility = View.VISIBLE
        textViewEtiquetteTitle.visibility = View.VISIBLE
        textViewEtiquette.visibility = View.VISIBLE
        textViewLanguageTitle.visibility = View.VISIBLE
        textViewLanguage.visibility = View.VISIBLE
        textViewReligionTitle.visibility = View.VISIBLE
        textViewReligion.visibility = View.VISIBLE
        imageViewEdit.visibility = View.VISIBLE
        imageViewBack.visibility = View.VISIBLE
    }

    private fun makeInvisible() {
        textViewCountry.visibility = View.INVISIBLE
        textViewAboutTitle.visibility = View.INVISIBLE
        textViewAbout.visibility = View.INVISIBLE
        textViewEthnicityTitle.visibility = View.INVISIBLE
        textViewEthnicity.visibility = View.INVISIBLE
        textViewEtiquetteTitle.visibility = View.INVISIBLE
        textViewEtiquette.visibility = View.INVISIBLE
        textViewLanguageTitle.visibility = View.INVISIBLE
        textViewLanguage.visibility = View.INVISIBLE
        textViewReligionTitle.visibility = View.INVISIBLE
        textViewReligion.visibility = View.INVISIBLE
        imageViewEdit.visibility = View.INVISIBLE
        imageViewBack.visibility = View.INVISIBLE
    }

    private fun setCountryText() {
        textViewCountry.text = country.name
        textViewAbout.text = country.description
        textViewEthnicity.text = country.ethnicity
        textViewEtiquette.text = country.etiquette
        textViewLanguage.text = country.language
        textViewReligion.text = country.religion
    }

    private fun setFragmentText() {
        editTextCountryName.setText(country.name)
        editTextAbout.setText(country.description)
        editTextEthnicity.setText(country.ethnicity)
        editTextEtiquette.setText(country.etiquette)
        editTextLanguage.setText(country.language)
        editTextReligion.setText(country.religion)
    }
}



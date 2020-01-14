package com.example.localguide

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_country_details.*
import org.json.JSONObject

class CountryDetails : AppCompatActivity() {
    lateinit var country: Country
    private val manager = supportFragmentManager
    lateinit var countryViewModel: CountryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_details)

        //LiveData View Model
        countryViewModel = ViewModelProviders.of(this).get(CountryViewModel:: class.java)

        //Intent
        val intent = getIntent()
        val passedName = intent.getStringExtra("name")
        getOneCountry(passedName)
    }

    private fun getOneCountry(country_name: String) {

        var loadingAnimation =
            LoadingAnimation(
                this,
                "loading.json"
            )
        loadingAnimation.playAnimation(true)

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

                        loadingAnimation.stopAnimation(R.layout.activity_country_details)

                        setCountryText(country)
                        countryViewModel.countryLive.value = country
                        makeVisible()
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

    private fun setOneCountry(country: Country) {
        val url = getString(R.string.url_server) + getString(R.string.url_update_country) +
                "?name=" + country.name +
                "&description=" + country.description +
                "&ethnicity=" + country.ethnicity +
                "&etiquette=" + country.etiquette +
                "&language=" + country.language +
                "&religion=" + country.religion

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                //Process the JSON
                try {
                    if (response != null) {
                        val strResponse = response.toString()
                        val jsonResponse = JSONObject(strResponse)

                        Log.d("Volley", jsonResponse.toString())

                        if (jsonResponse.getString("success").equals("1")) {
                            Toast.makeText(this, "Country Details Updated", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Failed to Update Country Details", Toast.LENGTH_SHORT).show()
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
        imageViewBack.visibility = View.VISIBLE
        imageViewEdit.visibility = View.VISIBLE

        //Button listeners
        imageViewEdit.setOnClickListener {
            //Fire observer
            val countryObserver = Observer<Country> { newCountry ->
                setCountryText(newCountry)
                setOneCountry(newCountry)
            }
            countryViewModel.countryLive.observe(this, countryObserver)

            val transaction = manager.beginTransaction()
            val fragment = EditCountryFragment()
            transaction.replace(R.id.countrydetails_big_container, fragment)
            transaction.commit()
        }
        imageViewBack.setOnClickListener {
            finish()
        }
    }

    private fun setCountryText(country: Country) {
        textViewCountry.text = country.name
        textViewAbout.text = country.description
        textViewEthnicity.text = country.ethnicity
        textViewEtiquette.text = country.etiquette
        textViewLanguage.text = country.language
        textViewReligion.text = country.religion
    }
}



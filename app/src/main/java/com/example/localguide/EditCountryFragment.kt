package com.example.localguide

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_edit_country.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [EditCountryFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [EditCountryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditCountryFragment : Fragment() {
    lateinit var countryViewModel: CountryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        countryViewModel = activity?.run {
            ViewModelProviders.of(this)[CountryViewModel:: class.java]
        } ?: throw Exception("Invalid Activity")

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_edit_country, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setFragmentText(countryViewModel.countryLive.value!!)

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setFragmentText(country: Country) {
        editTextCountryName.setText(country.name)
        editTextAbout.setText(country.description)
        editTextEthnicity.setText(country.ethnicity)
        editTextEtiquette.setText(country.etiquette)
        editTextLanguage.setText(country.language)
        editTextReligion.setText(country.religion)
    }
}

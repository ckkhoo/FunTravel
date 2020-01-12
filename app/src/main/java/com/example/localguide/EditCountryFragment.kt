package com.example.localguide

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

//        val binding = DataBindingUtil.inflate<>(inflater,
//            R.layout.fragment_edit_country,container,false)
//        return binding.root

        return inflater!!.inflate(R.layout.fragment_edit_country, container, false)
    }
}

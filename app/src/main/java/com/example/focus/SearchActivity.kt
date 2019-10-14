package com.example.focus

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener


class SearchActivity : AppCompatActivity(), PlaceSelectionListener {

    val TAG = "AutocompleteFragment"
    val KEY = "AIzaSyAZJEVgNh1xM8oi9AR0gopsifTWsG0ihTQ"

    override fun onError(status: Status) {
        Log.i(TAG, "An error occurred: $status")
    }

    override fun onPlaceSelected(place: Place) {
        findViewById<TextView>(R.id.result).setText(place.name)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        // Initialize Places.
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, KEY)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        //initialize autocompletefragment
        val autocompleteFragment = supportFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment

        autocompleteFragment.setPlaceFields(listOf(Place.Field.LAT_LNG, Place.Field.NAME))

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(this)

    }


}

package com.example.focus

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import android.content.Intent
import android.graphics.Color
import android.location.Location
import android.widget.Button
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.*
import com.google.firebase.database.FirebaseDatabase


class SearchActivity : FragmentActivity(), PlaceSelectionListener, OnMapReadyCallback {

    val TAG = "AutocompleteFragment"
    val KEY = "AIzaSyAlPaa0hBK3RwIO5iZNEXm6fyOD7y7Hg1Y"

    lateinit var radius: EditText
    lateinit var currCircle: Circle
    var currPlace: Place? = null

    lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        findViewById<Button>(R.id.add_button).setOnClickListener {
            if (currPlace != null) {
                val latLng = currPlace?.latLng
                MyDatabase(this, FirebaseDatabase.getInstance().reference).write(MyLocation(currPlace?.name ?: "name", latLng?.latitude ?: 0.0,
                    latLng?.longitude ?: 0.0, currCircle?.radius ?: 0.0))
                startActivity(Intent(this, ListActivity::class.java))
            }
        }


        initPlacesFragment()
        initMapsFragment()

        initEditTexts()
    }

    private fun initEditTexts() {
        radius = findViewById(R.id.radius)
        radius.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val size : Double = radius.text.toString().toDouble()
                currCircle.radius = size
            }
        })
    }

    private fun initPlacesFragment() {
        // Initialize Places.
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, KEY)
        }

        //initialize autocompletefragment
        val autocompleteFragment = supportFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment

        autocompleteFragment.setPlaceFields(listOf(Place.Field.LAT_LNG, Place.Field.NAME))

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(this)
    }

    override fun onError(status: Status) {
        Log.i(TAG, "An error occurred: $status")
    }

    override fun onPlaceSelected(place: Place) {
        currPlace = place
        findViewById<TextView>(R.id.result).setText(place.name)
        moveMap(place)
    }

    private fun moveMap(place: Place) {

        if (place.latLng != null) {
            moveMap(place.latLng as LatLng, place.name as String)
        }
    }

    private fun moveMap(latLng: LatLng, name: String) {
        currCircle.center = latLng

        googleMap.addMarker(MarkerOptions().position(latLng).title(name))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(10.0f))

        currCircle.center = latLng
    }

    private fun initMapsFragment() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(newMap: GoogleMap?) {

        googleMap = newMap as GoogleMap
        googleMap.setMinZoomPreference(15.0f)
        googleMap.setMaxZoomPreference(20.0f)

        moveMapToCurrLocation()

    }

    private fun moveMapToCurrLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                var latitude: Double
                var longitude: Double
                if (location == null) {
                    latitude = 37.8
                    longitude = 122.2
                } else {
                    latitude = location.latitude
                    longitude = location.longitude
                }

                val pos: LatLng = LatLng(latitude, longitude)

                currCircle = googleMap.addCircle(CircleOptions().center(pos).strokeColor(Color.RED).fillColor(Color.BLUE))
                moveMap(pos, "($latitude, $longitude)")
            }
    }
}
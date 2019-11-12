package com.example.focus

import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.maps.model.LatLng

data class MyLocation (val name: String, val lat: Double, val lng: Double, val radius: Double) {

    fun formatLoc(): String = "$lat, $lng"

    constructor(): this("", 0.0, 0.0, 0.0)

    //fun toMap(): Map<String, Any> = mapOf(Pair(NAME, name), Pair(LAT, lat), Pair(LNG, lng), Pair(RADIUS, radius))


    //constructor(map: Map<String, Any>) : this(map[NAME] as String, map[LAT] as Double, map[LNG] as Double, map[RADIUS] as Double)
//
//    companion object {
//        const val LAT = "lat"
//        const val LNG = "lng"
//        const val NAME = "name"
//        const val RADIUS = "radius"
//    }
}
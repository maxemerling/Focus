package com.example.focus

data class MyLocation (val name: String, val lat: Double, val lng: Double) {

    fun formatLoc(): String = "$lat, $lng"

    fun toMap(): Map<String, Any> = mapOf(Pair(NAME, name), Pair(LAT, lat), Pair(LNG, lng))

    constructor(map: Map<String, Any>) : this(map[NAME] as String, map[LAT] as Double, map[LNG] as Double)

    companion object {
        const val LAT = "lat"
        const val LNG = "lng"
        const val NAME = "name"
    }
}
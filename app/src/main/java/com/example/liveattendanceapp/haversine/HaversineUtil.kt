package com.example.liveattendanceapp.haversine

class HaversineUtil {
    fun haversine(
        lat1: Double, lon1: Double
    ): Double {
        // distance between latitudes and longitudes

        var lat1 = lat1
        var lat2 = -6.20893
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(106.79977 - lon1)

        // convert to radians
        lat1 = Math.toRadians(lat1)
        lat2 = Math.toRadians(lat2)

        // apply formulae
        val a = Math.pow(Math.sin(dLat / 2), 2.0) +
                Math.pow(Math.sin(dLon / 2), 2.0) *
                Math.cos(lat1) *
                Math.cos(lat2)
        val rad = 6371.0
        val c = 2 * Math.asin(Math.sqrt(a))
        return rad * c
    }
}
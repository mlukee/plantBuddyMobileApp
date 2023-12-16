package com.example.plantbuddy

import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.plantbuddy.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import org.json.JSONObject
import java.io.IOException

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var application: MyApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        application = getApplication() as MyApplication

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Load JSON data
        val jsonData = application.loadMarkers()
        val jsonArray = JSONObject(jsonData).getJSONArray("stores")

        for (i in 0 until jsonArray.length()) {
            val store = jsonArray.getJSONObject(i)
            val lat = store.getDouble("lat")
            val lng = store.getDouble("lng")
            val name = store.getString("name")
            val latLng = LatLng(lat, lng)

            val markerOptions = MarkerOptions().position(latLng).title(name)
            mMap.addMarker(markerOptions)
        }

        // Adjust camera to a central position
        val centralPtuj = LatLng(46.42, 15.87)
        val zoomLevel = 12.0f
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centralPtuj, zoomLevel))
    }
}
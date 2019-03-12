package com.ahmedazooz.pokemon

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        checkpermission()
    }

    val AccessLocation = 123
    fun checkpermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_DENIED
            ) {
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), AccessLocation)
                return
            }
        }
        getuserlocation()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        when (requestCode) {
            AccessLocation -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getuserlocation()
                } else {
                    Toast.makeText(this, "Location Access is denied", Toast.LENGTH_LONG).show()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @SuppressLint("MissingPermission")
    fun getuserlocation() {
        Toast.makeText(this, "Location Access now", Toast.LENGTH_LONG).show()
        val mlocation = MyLocation()
        val locationManager=getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3,3f,mlocation)
    val myThread=MyThread()
        myThread.start()
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
         }

    var location: Location? = null

    inner class MyLocation : LocationListener {
        constructor() {
            location = Location("Me")
            location!!.longitude = 0.0
            location!!.latitude = 0.0
        }

        override fun onLocationChanged(p0: Location?) {
            location = p0
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onProviderEnabled(provider: String?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onProviderDisabled(provider: String?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }
    inner class MyThread :Thread {
        constructor() : super() {

        }

        override fun run() {
            while (true) {
                try {
                    runOnUiThread {
                        mMap!!.clear()
                        val sydney = LatLng(location!!.longitude, location!!.latitude)
                        mMap!!.addMarker(
                            MarkerOptions()
                                .position(sydney)
                                .title("Me")
                                .snippet("Here is my location")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mariotwo))
                        )
                        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 8f))
                    }



                    Thread.sleep(1000)
                } catch (ex: Exception) {
                }
            }
        }
    }
}

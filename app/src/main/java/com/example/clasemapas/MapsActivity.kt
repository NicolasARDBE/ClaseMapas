package com.example.clasemapas

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.lights.Light
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.clasemapas.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.MapStyleOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private lateinit var sensorManager: SensorManager
    private lateinit var lightSensor: Sensor
    private lateinit var lightEventListener: SensorEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        lightEventListener = createLightSensorListener()
    }

    fun createLightSensorListener(): SensorEventListener{
        val ret : SensorEventListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                if (mMap != null && lightSensor != null) {
                    if (event != null) {
                        if (event.values[0] < 5000) {
                            mMap.setMapStyle(
                                MapStyleOptions.loadRawResourceStyle(
                                    baseContext,
                                    R.raw.nightmap
                                )
                            )
                        } else {
                            mMap.setMapStyle(
                                MapStyleOptions.loadRawResourceStyle(
                                    baseContext,
                                    R.raw.classicmap
                                )
                            )
                        }
                    }
                }
            }

            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
            }
        }
        return ret
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
        mMap.uiSettings.setAllGesturesEnabled(true)
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.classicmap))

        // Add a marker in Sydney and move the camera
        val colombia = LatLng(4.71, -74.3)
        mMap.addMarker(MarkerOptions().position(colombia).title("Marker in Colombia"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(colombia))
    }
    override fun onResume(){
        super.onResume ()
        sensorManager.registerListener(lightEventListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }
    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(lightEventListener)
    }
}


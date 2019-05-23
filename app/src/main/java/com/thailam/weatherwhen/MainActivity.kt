package com.thailam.weatherwhen

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient
import com.thailam.weatherwhen.viewmodel.ForecastViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val forecastViewModel: ForecastViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        checkPermissions()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode != PERMISSION_CODE) return
        for (x in 0 until permissions.size) {
            if (isPermissionGranted(PERMISSION_FINE_LOCATION)) getLastLocation()
        } // TODO: may have a better way to do this in kotlin
    }

    private fun initViewModel() {
        forecastViewModel.dailyForecastsLiveData.observe(this, Observer {
            // TODO: update UI in load ata to UI task
        })
    }

    private fun checkPermissions() {
        val permission = arrayOf(PERMISSION_FINE_LOCATION)
        if (isPermissionGranted(PERMISSION_FINE_LOCATION)) {
            getLastLocation()
        } else { // ask for permission if is not granted
            ActivityCompat.requestPermissions(this, permission, PERMISSION_CODE)
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        val locationClient: FusedLocationProviderClient = getFusedLocationProviderClient(this)
        locationClient.lastLocation
            .addOnSuccessListener {
                onLocationChanged(location = it)
            }
            .addOnFailureListener {
                onLocationFailure(it)
            }
    }

    private fun isPermissionGranted(permission: String): Boolean =
        ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

    /**
     * updates the view model on location change (using latitude and longitude)
     */
    private fun onLocationChanged(location: Location?) {
        val geoposition = Pair(location?.latitude.toString(), location?.longitude.toString())
        forecastViewModel.fetchDailyForecasts(geoposition)
    }

    private fun onLocationFailure(e: Exception) =
        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()

    companion object {
        /**
         * PERMISSION_CODE: permission code for Manifest.ACCESS_FINE_LOCATION
         * PERMISSION FINE LOCATION: String value of Manifest.permission.ACCESS_FINE_LOCATION
         */
        private const val PERMISSION_CODE = 1
        private const val PERMISSION_FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION
    }
}

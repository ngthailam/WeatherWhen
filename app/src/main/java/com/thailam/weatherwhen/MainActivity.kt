package com.thailam.weatherwhen

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.thailam.weatherwhen.viewmodel.ForecastViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val forecastViewModel: ForecastViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermissions()
        initViewModel()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode != PERMISSION_CODE) return
        for (x in 0 until permissions.size) {
            if (ContextCompat.checkSelfPermission(
                    this, android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) forecastViewModel.fetchDailyForecasts(getCurrentLatLong())
        }
    }

    private fun initViewModel() {
        forecastViewModel.dailyForecastsLiveData.observe(this, Observer {
            // TODO: update UI in load ata to UI task
        })
    }

    private fun checkPermissions() {
        val permission = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            forecastViewModel.fetchDailyForecasts(getCurrentLatLong())
        } else { // ask for permission if is not granted
            ActivityCompat.requestPermissions(this, permission, PERMISSION_CODE)
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLatLong(): Pair<String, String> {
        val lm: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        return Pair(location.latitude.toString(), location.longitude.toString())
    }

    companion object {
        private const val PERMISSION_CODE = 1
    }
}

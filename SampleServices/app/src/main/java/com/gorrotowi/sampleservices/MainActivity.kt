package com.gorrotowi.sampleservices

import android.Manifest
import android.content.ComponentName
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener
import com.karumi.dexter.listener.multi.DialogOnAnyDeniedMultiplePermissionsListener
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var messenger: Messenger? = null
    private var isBounded = false

    private lateinit var serviceConnection: ServiceConnection

    private val locationBCR by lazy {
        LocationBroadCastReceiver()
    }

    private val airplaneBCR by lazy {
        AirPlaneBroadCastReceiver()
    }

    private val intentServiceLocation by lazy {
        Intent(this, SampleService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createServiceConnection()
//        val msg = Message.obtain(null, 1, 0, 0)

//        onPermissionGranted {
//            ContextCompat.startForegroundService(this, intentServiceLocation.apply {
//                putExtra("NAME", "Sebastian")
//                putExtra("AGE", "27")
//            })
//
//        }

        val intentFilters = IntentFilter().apply {
            addAction(EVENT_LOCATION)
        }
        val intentFiltersAirPlane = IntentFilter().apply {
            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        }

        registerReceiver(locationBCR, intentFilters)
        registerReceiver(airplaneBCR, intentFiltersAirPlane)

        locationBCR.broadcastLocation.observe(this, Observer { data ->
            Log.wtf("BROADCAST OBSERVER", data)
        })

        LocationLcService.liveData.observe(this, Observer { data ->
            Log.wtf("LIVEDATA", data)
        })

        btnStart?.setOnClickListener {
            onPermissionGranted {
                ContextCompat.startForegroundService(this, intentServiceLocation.apply {
                    putExtra("NAME", "Christian")
                    putExtra("AGE", "26")
                    putExtra("OTHER", true)
                })
//                bindService(
//                    intentServiceLocation,
//                    serviceConnection,
//                    Context.BIND_AUTO_CREATE
//                )
            }
        }

        btnSendMessg?.setOnClickListener {
            if (isBounded) {
                val msg = Message.obtain(null, 20)
                try {
                    messenger?.send(msg)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        btnEnd?.setOnClickListener {
            stopService(intentServiceLocation)
//            if (isBounded) {
//                unbindService(serviceConnection)
//                isBounded = false
//            }
        }

    }

    private fun createServiceConnection() {
        serviceConnection = object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
                Log.e("UNBIND", "onServiceDisconnected: ${name?.toString()} ")
                messenger = null
                isBounded = false
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                Log.e("BIND", "onServiceDisconnected: ${name?.toString()} ")
                messenger = Messenger(service)
                isBounded = true
            }
        }
    }


    override fun onPause() {
        super.onPause()
//        stopService(intentServiceLocation)
        unregisterReceiver(locationBCR)
        unregisterReceiver(airplaneBCR)
    }

    private fun onPermissionGranted(block: () -> Unit) {

        val dialogOnDeniedPermissionListener = DialogOnAnyDeniedMultiplePermissionsListener.Builder
            .withContext(this)
            .withTitle("Permiso de Localización")
            .withMessage("Este permiso es necesario para poder seguir tus pasos 6_6")
            .withButtonText("Ok")
            .build()
        val permissionListener = object : MultiplePermissionsListener {
            override fun onPermissionsChecked(multiplePermissionsReport: MultiplePermissionsReport?) {
                if (multiplePermissionsReport?.areAllPermissionsGranted() == true) {
                    block()
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                permissionsRequest: MutableList<PermissionRequest>?,
                token: PermissionToken?
            ) {
                token?.continuePermissionRequest()
            }

        }

        val combinedListener =
            CompositeMultiplePermissionsListener(
                permissionListener,
                dialogOnDeniedPermissionListener
            )

        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            .withListener(combinedListener)
            .check()
    }

}
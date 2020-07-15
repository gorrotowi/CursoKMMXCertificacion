package com.gorrotowi.cert108

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import androidx.appcompat.app.AppCompatActivity
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
    private lateinit var serviceConnection: ServiceConnection

    private val intentLocationService by lazy {
        Intent(this, LocationService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createServiceConnection()
        setUpListeners()

    }

    private fun createServiceConnection() {
        serviceConnection = object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
                messenger = null
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                messenger = Messenger(service)
            }

        }
    }

    private fun setUpListeners() {

        btnStart?.setOnClickListener {
            onPermissionGranted {
//                ContextCompat.startForegroundService(this, intentLocationService.apply {
//                    putExtra("NAME", "Christian")
//                    putExtra("AGE", "25")
//                    putExtra("OTHER", true)
//                })
                bindService(
                    intentLocationService,
                    serviceConnection,
                    Context.BIND_AUTO_CREATE
                )
            }
        }

        btnSend?.setOnClickListener {
            messenger?.let { messengerNotNull ->
                val msg = Message.obtain(null, LocationService.IncommingHandler.MESSAGE_UPADTE)
                try {
                    messengerNotNull.send(msg)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        btnStop?.setOnClickListener {
//            stopService(intentLocationService)
            unbindLocationService()
        }

    }

    override fun onPause() {
        super.onPause()
        unbindLocationService()
    }

    private fun unbindLocationService() {
        messenger?.let {
            unbindService(serviceConnection)
        }
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
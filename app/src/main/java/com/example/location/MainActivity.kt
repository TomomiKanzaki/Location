package com.example.location

import android.Manifest
import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.*
import permissions.dispatcher.*

@RuntimePermissions
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        getLocation()
    }

    @SuppressLint("MissingPermission")
//    @NeedsPermission
    fun getLocation(){
        LocationServices.getFusedLocationProviderClient(this).let {
            it.lastLocation.addOnCompleteListener(this) {
                if (it.isSuccessful && it.result != null){
                    Log.d("location: ", "latitude: ${it.result!!.latitude} longtitude: ${it.result!!.longitude}")
                } else {
                    //getLocation() is failed
                    Log.e("getLocation", ": failed")

                    getUpdateLocation()
                }
            }
        }
    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        // 自動生成された関数にパーミッション・リクエストの結果に応じた処理の呼び出しを委譲
////        onRequestPermissionsResult(requestCode, grantResults)
//    }

    private var fusedLocationClient: FusedLocationProviderClient? = null
    private val locationCallback: LocationCallback by lazy {
        (object: LocationCallback() {
            override fun onLocationResult(p0: LocationResult?) {
                super.onLocationResult(p0)
                // 位置情報の更新が取得できた場合
                // p0?.lastLocationのlatitudeとlongitudeで位置情報を取得
            }
        })
    }

    @SuppressLint("MissingPermission")
    private fun getUpdateLocation() {
        // とりあえず1回だけ取得
        val request = LocationRequest.create().apply {
            interval = 10000 // ms
            numUpdates = 1
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient?.let { client ->
            client.requestLocationUpdates(request, locationCallback, null)
                .addOnCompleteListener { task ->
                    if (task.result == null) {
                        // 1回目で失敗した場合
                    } else {

                    }
                }
        }
    }

    override fun onStop() {
        super.onStop()
        fusedLocationClient?.removeLocationUpdates(locationCallback)
    }

//    @OnPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION)
//    fun onContactsDenied() {
//        Toast.makeText(this, "「許可しない」が選択されました", Toast.LENGTH_SHORT).show()
//    }
//
//    @OnShowRationale(Manifest.permission.ACCESS_FINE_LOCATION)
//    fun showRationaleForContacts(request: PermissionRequest) {
//        AlertDialog.Builder(this)
//            .setPositiveButton("許可") { _, _ -> request.proceed() }
//            .setNegativeButton("許可しない") { _, _ -> request.cancel() }
//            .setCancelable(false)
//            .setMessage("登録数を取得するために連絡先にアクセスする必要があります。")
//            .show()
//    }
//
//    @OnNeverAskAgain(Manifest.permission.ACCESS_FINE_LOCATION)
//    fun onContactsNeverAskAgain() {
//        Toast.makeText(this, "「今後表示しない」が選択されました", Toast.LENGTH_SHORT).show()
//    }
}

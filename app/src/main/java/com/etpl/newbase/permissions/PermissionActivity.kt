package com.etpl.newbase.permissions

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.etpl.newbase.R


class PermissionActivity : AppCompatActivity() {
    internal lateinit var permissions: Array<String>

    private var checker: PermissionsChecker? = null
    private var requiresCheck: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent == null || !intent.hasExtra(EXTRA_PERMISSIONS)) {
            throw RuntimeException("This Activity needs to be launched using the static startActivityForResult() method.")
        }
        checker = PermissionsChecker(this)
        requiresCheck = true
    }

    override fun onResume() {
        super.onResume()
        if (requiresCheck) {
            permissions = getPermissions()

            if (checker!!.lacksPermissions(*permissions)) {
                requestPermissions(*permissions)
            } else {
                allPermissionsGranted()
            }
        } else {
            requiresCheck = true
        }
    }

    private fun getPermissions(): Array<String> {
        return intent.getStringArrayExtra(EXTRA_PERMISSIONS)
    }

    private fun requestPermissions(vararg permissions: String) {
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE)
    }

    private fun allPermissionsGranted() {
        setResult(PERMISSIONS_GRANTED)
        finish()

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_REQUEST_CODE && hasAllPermissionsGranted(grantResults)) {
            requiresCheck = true
            println("Granted All")
            allPermissionsGranted()
        } else {
            requiresCheck = false
            showMissingPermissionDialog()
        }
    }

    private fun hasAllPermissionsGranted(grantResults: IntArray): Boolean {
        for (grantResult in grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                println("Not Granted $grantResult")
                return false
            }
            println("Granted $grantResult")
        }
        return true
    }

    private fun showMissingPermissionDialog() {
        val dialogBuilder = AlertDialog.Builder(this@PermissionActivity)
        dialogBuilder.setTitle(R.string.permission_missing)
        dialogBuilder.setCancelable(false)
        dialogBuilder.setMessage(R.string.action_settings)

        dialogBuilder.setPositiveButton(R.string.allow) { dialog, which ->

            if (checker!!.lacksPermissions(*permissions)) {
                if (counter > 3) {
                    startAppSettings()
                } else
                    requestPermissions(*permissions)
            } else {
                allPermissionsGranted()
            }
        }
        dialogBuilder.show()
        counter++
    }

    private fun startAppSettings() {
        val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse(PACKAGE_URL_SCHEME + packageName)
        startActivity(intent)
    }

    companion object {
        var counter = 0
        val PERMISSIONS_GRANTED = 0
        val PERMISSIONS_DENIED = 1

        private val PERMISSION_REQUEST_CODE = 0
        private val EXTRA_PERMISSIONS = "com.etpl.frogmendiver.EXTRA_PERMISSIONS"
        private val PACKAGE_URL_SCHEME = "package:"

        fun startActivityForResult(activity: Activity, requestCode: Int, vararg permissions: String) {
            val intent = Intent(activity, PermissionActivity::class.java)
            intent.putExtra(EXTRA_PERMISSIONS, permissions)
            ActivityCompat.startActivityForResult(activity, intent, requestCode, null)
        }
    }
}

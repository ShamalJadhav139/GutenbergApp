package com.etpl.newbase.permissions

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat


class PermissionsChecker(private val context: Context) {

    fun lacksPermissions(vararg permissions: String): Boolean {
        for (permission in permissions) {
            if (lacksPermission(permission)) {
                return true
            }
        }
        return false
    }

    private fun lacksPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED
    }
}

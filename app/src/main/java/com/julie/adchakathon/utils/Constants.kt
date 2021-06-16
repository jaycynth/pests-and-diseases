package com.julie.adchakathon.utils

import android.Manifest
import android.content.Context
import android.os.Build
import pub.devrel.easypermissions.EasyPermissions

object  Constants {

     const val TAG = "ADCHACKATHON"
     const val REQUEST_CODE_PERMISSIONS = 999
     const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
     const val SHARED_PREFERENCES_NAME = "sharedPref"
     const val USER_FIRST_TIME = "USER_FIRST_TIME"
     const val TOKEN = "TOKEN"



     fun hasLocationPermission(context: Context) =
          if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
               EasyPermissions.hasPermissions(
                    context,
                    Manifest.permission.CAMERA,
               )
          } else {
               EasyPermissions.hasPermissions(
                    context,
                    Manifest.permission.CAMERA,

               )
          }

}
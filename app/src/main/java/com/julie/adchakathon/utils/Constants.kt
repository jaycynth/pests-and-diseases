package com.julie.adchakathon.utils

import android.Manifest
import android.content.Context
import android.os.Build
import pub.devrel.easypermissions.EasyPermissions

object  Constants {

     const val MAX_RESULT_DISPLAY = 4
     const val TAG = "ADCHACKATHON"
     const val REQUEST_CODE_PERMISSIONS = 999
     const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
     const val FLOAT_TYPE_SIZE = 4
     const val CHANNEL_SIZE = 3
     const val IMAGE_MEAN = 127.5f
     const val IMAGE_STD = 127.5f


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
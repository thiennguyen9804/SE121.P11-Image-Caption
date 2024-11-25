package com.example.se121p11new.core.presentation.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun getBitmapFromUri(uri: Uri, applicationContext: Context): Bitmap =
    withContext(Dispatchers.IO) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            uri.let {
                ImageDecoder.createSource(
                    applicationContext.contentResolver,
                    it
                )
            }.let { ImageDecoder.decodeBitmap(it) }
        } else {
            MediaStore.Images.Media.getBitmap(
                applicationContext.contentResolver,
                uri
            )
        }
    }

package com.example.projectapp.utils

import android.content.Context
import android.net.Uri
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import java.util.*

fun uploadToCloudinary(uri: Uri, context: Context, onResult: (String?) -> Unit) {
    val requestId = UUID.randomUUID().toString()

    MediaManager.get().upload(uri)
        .option("public_id", "profile_images/$requestId")
        .callback(object : UploadCallback {
            override fun onStart(requestId: String?) {}
            override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {}

            override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {
                val url = resultData?.get("secure_url")?.toString()
                onResult(url)
            }

            override fun onError(requestId: String?, error: ErrorInfo) {
                onResult(null)
            }

            override fun onReschedule(requestId: String?, error: ErrorInfo) {}
        }).dispatch()
}

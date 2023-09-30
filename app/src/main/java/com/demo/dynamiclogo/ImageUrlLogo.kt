package com.demo.dynamiclogo


import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class ImageUrlLogo : AppCompatActivity() {

    private val imageUrl = "https://fastly.picsum.photos/id/1/200/300.jpg?hmac=jH5bDkLr6Tgy3oAg5khKCHeunZMHq0ehBZr6vGifPLY"
    private lateinit var imageView: ImageView
    private lateinit var appIcon: Bitmap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_url_logo)
        imageView = findViewById(R.id.imageView)

        // Load the existing app icon
        appIcon = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)

        // Download and set the image from URL
        DownloadImageTask().execute(imageUrl)

    }

    private inner class DownloadImageTask : AsyncTask<String, Void, Bitmap>() {
        override fun doInBackground(vararg urls: String): Bitmap? {
            val imageUrl = urls[0]
            val urlConnection: HttpURLConnection = URL(imageUrl).openConnection() as HttpURLConnection
            urlConnection.connect()
            val inputStream: InputStream = urlConnection.inputStream
            return BitmapFactory.decodeStream(inputStream)
        }

        override fun onPostExecute(result: Bitmap?) {
            super.onPostExecute(result)
            if (result != null) {
                // Combine the existing app icon with the downloaded image
                val newAppIcon = combineImages(appIcon, result)

                // Set the new app icon
                setAppIcon(newAppIcon)
            }
        }
    }

    private fun combineImages(baseImage: Bitmap, overlayImage: Bitmap): Bitmap {
        val width = baseImage.width
        val height = baseImage.height
        val combinedImage = Bitmap.createBitmap(width, height, baseImage.config)

        val canvas = android.graphics.Canvas(combinedImage)
        canvas.drawBitmap(baseImage, 0f, 0f, null)
        canvas.drawBitmap(overlayImage, 0f, 0f, null)

        return combinedImage
    }

    private fun setAppIcon(newIcon: Bitmap) {
        val componentName = ComponentName(applicationContext, MainActivity::class.java)
        val packageManager = applicationContext.packageManager

        // Disable the current app icon
        packageManager.setComponentEnabledSetting(
            componentName,
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )

        // Set the new app icon
       /* val appIconSetter = AppIconSetter(this.application, componentName)
        appIconSetter.setIcon(newIcon)*/

        packageManager.setComponentEnabledSetting(
            ComponentName(
                this@ImageUrlLogo,
                "com.demo.dynamiclogo.MainActivity"),
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP)

        // Enable the new app icon
        packageManager.setComponentEnabledSetting(
            componentName,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )


    }

}
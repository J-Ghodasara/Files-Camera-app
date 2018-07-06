package com.example.jayghodasara.files

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.SystemClock
import android.provider.MediaStore
import android.provider.Settings
import android.support.v4.content.FileProvider
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_camera.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class CameraActivity : AppCompatActivity() {

    var photopath: String? = null
    lateinit var photouri: Uri
    var size: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        display.setOnClickListener(View.OnClickListener {
            var imgBitmap: Bitmap? = null
            if (photouri != null) {
                imgBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, photouri)
                img.setImageBitmap(imgBitmap)
            }


        })

        snap.setOnClickListener(View.OnClickListener {

            var takesnap: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takesnap.resolveActivity(packageManager) != null) {

                var photofile: File? = null
                try {
                    var file: File = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                    //   var file:File= getDir("photogrid", Context.MODE_PRIVATE)
                    Log.i("size", (file.freeSpace.toDouble() * 0.000001).toString())
                    photofile = File(file, "mypic.jpg")
                    size = (file.freeSpace.toDouble() * 0.000001)

                } catch (e: IOException) {
                    e.printStackTrace()
                }

                if (photofile != null) {
                    if (size!! < 100) {
                        Toast.makeText(applicationContext, "Low Memory", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(applicationContext, "Yay!!...Memory available", Toast.LENGTH_LONG).show()
                        photopath = photofile.absolutePath
                        photouri = FileProvider.getUriForFile(this, "com.example.jayghodasara.files.provider", photofile)
                        Log.i("PhotoUri", photouri.toString())
                        takesnap.putExtra(MediaStore.EXTRA_OUTPUT, photouri)
                        startActivityForResult(takesnap, 11)
                    }

                }
            }

        })

        sdcard.setOnClickListener(View.OnClickListener {
            var takesnap: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takesnap.resolveActivity(packageManager) != null) {
                var path: String = "/storage/9016-4EF8" + File.separator + "photogrid"

                Log.i("path", path)


                var img: File = File("/storage/9016-4EF8" + File.separator + "photogrid", "mypic.png")

                var Fos: FileOutputStream? = null
                try {
                    Fos = FileOutputStream(img)

                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                } finally {
                    if (Fos != null) {
                        try {
                            Fos.flush()
                            Fos.close()

                        } catch (e: IOException) {
                            e.printStackTrace()
                        }


                    }
                }
//
                takesnap.putExtra(MediaStore.EXTRA_OUTPUT, path)
                startActivityForResult(takesnap, 11)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 11 && resultCode == Activity.RESULT_OK) {
            Toast.makeText(applicationContext, "Image Saved", Toast.LENGTH_LONG).show()

        }
    }
}

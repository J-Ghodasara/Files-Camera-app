package com.example.jayghodasara.files

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    var filename: String? = null
    var message: String? = null
    var isEOF = false
    var isEnd = false
    var stringbuffer: StringBuffer = StringBuffer()
    var stringbufferpublic: StringBuffer = StringBuffer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var permissioncheck: Int = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permissioncheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        }

        Write.setOnClickListener(View.OnClickListener {

            filename = file_name.text.toString()
            message = msg.text.toString()

            var fos: FileOutputStream? = null

            try {
                fos = openFileOutput(filename, Context.MODE_PRIVATE)

                fos.write(message!!.toByteArray())
                Toast.makeText(applicationContext, "message Inserted", Toast.LENGTH_LONG).show()

            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                if (fos != null) {
                    try {
                        fos.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }

            }

        })

        read.setOnClickListener(View.OnClickListener {

            filename = file_name.text.toString()
            var fis: FileInputStream? = null

            try {
                fis = openFileInput(filename)
                var read: Int = 0
                while (!isEOF) {

                    if (read != -1) {
                        read = fis.read()
                        stringbuffer.append(read.toChar())
                    } else {
                        isEOF = true
                    }

                }

                read_msg.text = stringbuffer.toString()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                if (fis != null) {
                    try {
                        fis.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        })

        Write_External.setOnClickListener(View.OnClickListener {

            filename = file_name.text.toString()
            message = msg.text.toString()
            var file: File = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            var filewrite: File = File(file, filename)

            var fos2: FileOutputStream? = null
            try {
                fos2 = FileOutputStream(filewrite)
                fos2!!.write(message!!.toByteArray())
                Toast.makeText(applicationContext, "message Inserted", Toast.LENGTH_LONG).show()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                if (fos2 != null) {
                    try {
                        fos2!!.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        })

        pic.setOnClickListener(View.OnClickListener {
            var intent: Intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        })
        read_external.setOnClickListener(View.OnClickListener {
            filename = file_name.text.toString()

            var fileread: File = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            var file: File = File(fileread, filename)

            var fis: FileInputStream? = null
            try {

                fis = FileInputStream(file)
                var read2 = 0
                while (!isEnd) {

                    if (read2 != -1) {
                        read2 = fis!!.read()
                        stringbufferpublic.append(read2.toChar())
                    } else {
                        isEnd = true
                    }

                }
                read_msg.text = stringbufferpublic.toString()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                if (fis != null) {
                    try {
                        fis!!.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }

        })

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {

            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                    if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                    }
                } else {
                    Toast.makeText(applicationContext, " Permission Denied", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}

package com.example.animations

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter

class QrCodeActivity : AppCompatActivity() {

    private lateinit var btnScan: Button
    private lateinit var ivCode: ImageView

    private val activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()){ isGranted ->
            // Handle Permission granted/rejected
            if (isGranted) {
                startActivity(Intent(this, ScanActivity::class.java))
            } else {
                Toast.makeText(this, "try More", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_code)

        btnScan = findViewById(R.id.btnScan)
        ivCode = findViewById(R.id.ivCode)

        btnScan.setOnClickListener {
            activityResultLauncher.launch(Manifest.permission.CAMERA)
        }

        ivCode.setImageBitmap(getQrCodeBitmap("https://www.kaspersky.com"))


    }


    fun getQrCodeBitmap(ssid: String): Bitmap {
        val hints = hashMapOf<EncodeHintType, Int>().also { it[EncodeHintType.MARGIN] = 1 } // Make the QR code buffer border narrower
        val size = 512 //pixels
        val bits = QRCodeWriter().encode(ssid, BarcodeFormat.QR_CODE, size, size, hints)
        return Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
            for (x in 0 until size) {
                for (y in 0 until size) {
                    it.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        }
    }
}
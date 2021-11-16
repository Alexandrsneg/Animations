package com.example.animations

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.zxing.*
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.common.HybridBinarizer

import android.graphics.BitmapFactory





class QrCodeActivity : AppCompatActivity() {

    private lateinit var btnScan: Button
    private lateinit var ivCode: ImageView
    private lateinit var tvDecodedCode: TextView

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
        tvDecodedCode = findViewById(R.id.tvDecodedCode)

        btnScan.setOnClickListener {
            activityResultLauncher.launch(Manifest.permission.CAMERA)
        }

        ivCode.setImageBitmap(getQrCodeBitmap("https://www.kaspersky.com"))

        val icon = BitmapFactory.decodeResource(
            this.resources,
            R.drawable.screenshot
        )

        decodeFromBitmap(icon)


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

    fun decodeFromBitmap(bitmap: Bitmap){
        val width = bitmap.width
        val height = bitmap.height
        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)

        val source = RGBLuminanceSource(width, height, pixels)

        val binaryBitmap = BinaryBitmap(HybridBinarizer(source))

        val reader: Reader = MultiFormatReader()
        var result: Result? = null
        try {
            result = reader.decode(binaryBitmap)
        } catch (e: NotFoundException) {
            e.printStackTrace()
        } catch (e: ChecksumException) {
            e.printStackTrace()
        } catch (e: FormatException) {
            e.printStackTrace()
        }
        val text: String = result?.getText() ?: "ppc"
        tvDecodedCode.text = " CONTENT1111: $text"
    }
}
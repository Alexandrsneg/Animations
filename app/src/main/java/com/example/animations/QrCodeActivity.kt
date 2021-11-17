package com.example.animations

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.*
import com.google.zxing.Reader
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.qrcode.QRCodeWriter
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.rendering.ImageType

import com.tom_roush.pdfbox.rendering.PDFRenderer
import java.io.*


class QrCodeActivity : AppCompatActivity() {

    private lateinit var btnScan: Button
    private lateinit var btnGetPdf: Button
    private lateinit var ivCode: ImageView
    private lateinit var tvDecodedCode: TextView
    private lateinit var progressBar: ProgressBar
    private var pageImage: Bitmap? = null
    var root: File? = null

    private val cameraPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()){ isGranted ->
            // Handle Permission granted/rejected
            if (isGranted) {
                startActivity(Intent(this, ScanActivity::class.java))
            } else {
                Toast.makeText(this, "try More", Toast.LENGTH_SHORT).show()
            }
        }

    private val documentLauncher = registerForActivityResult(
        ActivityResultContracts.OpenDocument()) {
        Toast.makeText(this, it.path, Toast.LENGTH_SHORT).show()
        progressBar.visibility = View.VISIBLE
        object : Thread() {
            override fun run() {
                renderFile(it)
            }
        }.start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_code)

        btnScan = findViewById(R.id.btnScan)
        btnGetPdf = findViewById(R.id.btnGetPdf)
        ivCode = findViewById(R.id.ivCode)
        tvDecodedCode = findViewById(R.id.tvDecodedCode)
        progressBar = findViewById(R.id.progressBar)

        btnScan.setOnClickListener {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
        tvDecodedCode.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW)
            browserIntent.data = Uri.parse(tvDecodedCode.text as String?)
            startActivity(browserIntent)
        }

        btnGetPdf.setOnClickListener {
            documentLauncher.launch(arrayOf(
                "application/pdf"
            ))
        }

//        ivCode.setImageBitmap(getQrCodeBitmap("https://www.kaspersky.com"))

        val icon = BitmapFactory.decodeResource(
            this.resources,
            R.drawable.screenshot
        )

//        decodeFromBitmap(icon)

        PDFBoxResourceLoader.init(applicationContext);
        root = applicationContext.cacheDir;
    }


    //вернет битмап со строки
    fun getQrCodeBitmap(codeString: String): Bitmap {
        val hints = hashMapOf<EncodeHintType, Int>().also { it[EncodeHintType.MARGIN] = 1 } // Make the QR code buffer border narrower
        val size = 512 //pixels
        val bits = QRCodeWriter().encode(codeString, BarcodeFormat.QR_CODE, size, size, hints)
        return Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
            for (x in 0 until size) {
                for (y in 0 until size) {
                    it.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        }
    }

    //расшифовка кода в строку
    fun decodeFromBitmap(bitmap: Bitmap): String? {
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
        } catch (e: Exception) {
            e.printStackTrace()
        }
        tvDecodedCode.text = "${result?.text}"
        return result?.text
    }

    // Render the page and save it to an image file
    private fun renderFile(uri: Uri) {
        val inputStream: InputStream? = contentResolver.openInputStream(uri)
        try {
            val renderer = PDFRenderer(PDDocument.load(inputStream))
            // Render the image to an RGB Bitmap
            //todo будет искать только на первой странице
            pageImage = renderer.renderImage(0, 1f, ImageType.RGB)

            // Save the render result to an image
            val path: String = root?.absolutePath.toString() + "/render.jpg"
            val renderFile = File(path)
            val fileOut = FileOutputStream(renderFile)
            pageImage?.compress(Bitmap.CompressFormat.JPEG, 100, fileOut)
            fileOut.close()
            inputStream?.close()

            // handle pageImage
            displayRenderedImage()
        } catch (e: IOException) {
            Log.d("PdfBox-Android-Sample", "Exception thrown while rendering file", e)
        }
    }

    private fun displayRenderedImage() {
        object : Thread() {
            override fun run() {
                runOnUiThread {
                    progressBar.visibility = View.GONE
//                    ivCode.setImageBitmap(pageImage)
                    val codeText = decodeFromBitmap(pageImage!!)
                    codeText?.let { ivCode.setImageBitmap(getQrCodeBitmap(it)) }
                }
            }
        }.start()
    }
}
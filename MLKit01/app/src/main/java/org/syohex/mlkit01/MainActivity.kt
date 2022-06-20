package org.syohex.mlkit01

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.barcode.common.Barcode.*
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val scanner = GmsBarcodeScanning.getClient(this)

        button.setOnClickListener {
            scanner.startScan()
                .addOnSuccessListener { barcode: Barcode ->
                    val valueType = valueTypeToString(barcode.valueType)
                    val format = formatToString(barcode.format)

                    Log.d(TAG, "FORMAT: $format")
                    Log.d(TAG, "TYPE: $valueType")

                    when (barcode.valueType) {
                        TYPE_EMAIL -> {
                            Toast.makeText(
                                this,
                                "Address: ${barcode.email?.address}",
                                LENGTH_LONG
                            ).show()
                        }
                        TYPE_WIFI -> {
                            Toast.makeText(
                                this,
                                "SSID: ${barcode.wifi?.ssid}",
                                LENGTH_LONG
                            ).show()
                        }
                        TYPE_URL -> {
                            Toast.makeText(
                                this,
                                "URL: ${barcode.url?.url}",
                                LENGTH_LONG
                            ).show()
                        }
                        else -> {
                            Toast.makeText(
                                this,
                                "FORMAT: $format, TYPE: $valueType",
                                LENGTH_LONG
                            ).show()
                        }
                    }
                }
                .addOnFailureListener { e: Throwable ->
                    Log.d(TAG, "ERROR: ${e.message}")
                }
        }
    }

    private companion object {
        private const val TAG = "org.syohex.mlkit01"

        private fun valueTypeToString(valueType: Int): String {
            return when (valueType) {
                TYPE_CONTACT_INFO -> "CONTACT_INFO"
                TYPE_EMAIL -> "EMAIL"
                TYPE_URL -> "URL"
                TYPE_WIFI -> "WIFI"
                TYPE_ISBN -> "ISBN"
                TYPE_PHONE -> "PHONE"
                TYPE_SMS -> "SMS"
                TYPE_PRODUCT -> "PRODUCT"
                TYPE_TEXT -> "TEXT"
                TYPE_GEO -> "GEO"
                TYPE_CALENDAR_EVENT -> "CALENDAR_EVENT"
                TYPE_DRIVER_LICENSE -> "DRIVER_LICENSE"
                else -> "UNKNOWN_VALUE_TYPE: $valueType"
            }
        }

        private fun formatToString(format: Int): String {
            return when (format) {
                FORMAT_CODE_128 -> "CODE_128"
                FORMAT_CODE_93 -> "CODE_93"
                FORMAT_CODE_39 -> "CODE_39"
                FORMAT_CODABAR -> "CODABAR"
                FORMAT_DATA_MATRIX -> "DATA_MATRIX"
                FORMAT_EAN_13 -> "EAN_13"
                FORMAT_EAN_8 -> "EAN_8"
                FORMAT_ITF -> "ITF"
                FORMAT_QR_CODE -> "QR_CODE"
                FORMAT_UPC_A -> "UPC_A"
                FORMAT_UPC_E -> "UPC_E"
                FORMAT_PDF417 -> "PDF417"
                FORMAT_AZTEC -> "AZTEC"
                else -> "UNKNOWN"
            }
        }
    }
}
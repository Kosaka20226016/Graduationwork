package jp.ac.nkc_cta09.graduationwork

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.FirebaseAuth
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import android.graphics.Bitmap

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //ここから書く
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: "UNKNOWN"
        val barcodeImageView = findViewById<ImageView>(R.id.barcodeImageView)

        val barcodeBitmap = generateBarcodeBitmap(userId)
        barcodeImageView.setImageBitmap(barcodeBitmap)
    }

    fun generateBarcodeBitmap(content: String, width: Int = 1000, height: Int = 150): Bitmap {
        val bitMatrix: BitMatrix = MultiFormatWriter().encode(
            content,
            BarcodeFormat.CODE_128,  // QRコードにしたいなら BarcodeFormat.QR_CODE に変更可能
            width,
            height
        )
        val encoder = BarcodeEncoder()
        return encoder.createBitmap(bitMatrix)
    }
}
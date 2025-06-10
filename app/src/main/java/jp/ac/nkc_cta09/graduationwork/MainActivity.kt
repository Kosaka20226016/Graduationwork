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
import android.widget.TextView
import android.util.Log

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
        val view_user_id = findViewById<TextView>(R.id.userid)
        val view_user_name = findViewById<TextView>(R.id.username)
        val database = FirebaseDatabase.getInstance().reference
        val barcodeBitmap = generateBarcodeBitmap(userId)
        barcodeImageView.setImageBitmap(barcodeBitmap)

        database.child("users").child(userId).child("name").get()
            .addOnSuccessListener { dataSnapshot ->
                if (dataSnapshot.exists()) {
                    val userName = dataSnapshot.getValue(String::class.java)
                    view_user_name.text = "名前: $userName"
                } else {
                    view_user_name.text = "ユーザー名: 取得できませんでした"
                }
            }.addOnFailureListener {
                view_user_name.text = "エラー: データ取得失敗"
                Log.e("Firebase", "データ取得失敗", it)
            }

        view_user_id.text = "ID: $userId"
    }

    fun generateBarcodeBitmap(content: String, width: Int = 1000, height: Int = 250): Bitmap {
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
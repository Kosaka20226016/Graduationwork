package jp.ac.nkc_cta09.graduationwork

import android.content.Intent
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
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import android.graphics.Color
import android.view.View
import android.widget.Button


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

        // *** ここからマイページボタンのロジックを追加 ***
        // activity_main.xml にあるマイページボタンのIDに合わせて変更してください
        val myPageButton: Button = findViewById(R.id.button3) // 例: あなたのXMLのボタンID

        myPageButton.setOnClickListener {
            val intent = Intent(this, MypageActivity::class.java) // MyPageActivityへのIntent
            startActivity(intent) // MyPageActivityを開始
        }
        // *** ここまでマイページボタンのロジック ***


        val noticeList = mutableListOf<Notice>()

        database.child("notices").get().addOnSuccessListener { snapshot ->
            noticeList.clear()
            for (child in snapshot.children) {
                val notice = child.getValue(Notice::class.java)
                if (notice != null) {
                    noticeList.add(notice)
                }
            }

            // 表示処理（次のステップ）
            showNotices(noticeList)
        }



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

    fun showNotices(notices: List<Notice>) {
        val container = findViewById<LinearLayout>(R.id.noticeContainer)
        container.removeAllViews()

        if (notices.isEmpty()) {
            val emptyView = TextView(this)
            emptyView.text = "お知らせはありません"
            emptyView.textSize = 18f
            emptyView.setTextColor(Color.GRAY)
            emptyView.setPadding(16, 16, 16, 16)
            container.addView(emptyView)
            return
        }

        for (notice in notices) {
            val noticeLayout = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(16, 16, 16, 16)
            }

            val titleView = TextView(this).apply {
                text = notice.title
                textSize = 22f
                setTextColor(Color.BLACK)
            }

            val bodyView = TextView(this).apply {
                text = notice.body
                textSize = 16f
                setTextColor(Color.DKGRAY)
                visibility = View.GONE  // 最初は非表示
            }

            titleView.setOnClickListener {
                val intent = Intent(this, NoticeDetailActivity::class.java)
                intent.putExtra("title", notice.title)
                intent.putExtra("body", notice.body)
                intent.putExtra("date", notice.date)
                startActivity(intent)
            }

            noticeLayout.addView(titleView)
            noticeLayout.addView(bodyView)
            container.addView(noticeLayout)
        }
    }




}
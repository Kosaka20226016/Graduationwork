package jp.ac.nkc_cta09.graduationwork

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.Toast
import android.content.Intent

class MypageActivity : AppCompatActivity() {
    private lateinit var btnChangeReservation: Button
    private lateinit var btnAccountSettings: Button
    private lateinit var btnHome:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mypage)
        btnChangeReservation = findViewById(R.id.btnChangeReservation)
        btnAccountSettings = findViewById(R.id.btnAccountSettings)
        btnHome = findViewById(R.id.button4)

        btnChangeReservation.setOnClickListener {
            Toast.makeText(this, "予約変更・キャンセルボタンが押されました", Toast.LENGTH_SHORT).show()
        }
        btnAccountSettings.setOnClickListener {
            val intent = Intent(this, AccountSettingsActivity::class.java)
            startActivity(intent)
        }
        btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish() // 現在のMyPageActivityを終了する
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
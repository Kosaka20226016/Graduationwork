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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mypage)
        btnChangeReservation = findViewById(R.id.btnChangeReservation)
        btnAccountSettings = findViewById(R.id.btnAccountSettings)
        btnChangeReservation.setOnClickListener {
            Toast.makeText(this, "予約変更・キャンセルボタンが押されました", Toast.LENGTH_SHORT).show()
        }
        btnAccountSettings.setOnClickListener {
            val intent = Intent(this, AccountSettingsActivity::class.java)
            startActivity(intent)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
package jp.ac.nkc_cta09.graduationwork

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.appcompat.widget.Toolbar
import android.widget.Button
import android.widget.Toast
import android.content.Intent


class AccountSettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_account_settings)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 戻るボタンを有効にする
        supportActionBar?.title = "アカウント設定" // ツールバーのタイトルを設定
        val btnEditProfile: Button = findViewById(R.id.btnEditProfile)
        val btnChangePassword: Button = findViewById(R.id.btnChangePassword) // 新しいボタン
        val btnLogout: Button = findViewById(R.id.btnLogout)
        btnEditProfile.setOnClickListener {
            Toast.makeText(this, "プロフィール編集画面へ", Toast.LENGTH_SHORT).show()
        }
        btnChangePassword.setOnClickListener {
            Toast.makeText(this, "パスワード変更画面へ", Toast.LENGTH_SHORT).show()

        }

        btnLogout.setOnClickListener {
            Toast.makeText(this, "ログアウトしました", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java) // ログイン画面かメイン画面
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // 既存のタスクをクリア
            startActivity(intent)
            finish()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed() // 前の画面に戻る
        return true
    }

}

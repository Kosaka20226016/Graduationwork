package jp.ac.nkc_cta09.graduationwork // ← 自分のパッケージ名に変更！

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login) // activity_main ではなく activity_login に注意！

        // FirebaseAuth インスタンスを初期化
        auth = FirebaseAuth.getInstance()

        // View の取得
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val registerButton = findViewById<Button>(R.id.registerButton)
        // ログインボタンのクリック処理
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "メールアドレスとパスワードを入力してください", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Firebaseでログイン処理
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // 成功時
                        Toast.makeText(this, "ログイン成功", Toast.LENGTH_SHORT).show()

                        // 次の画面に遷移（例：MainActivity）
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // 失敗時
                        Toast.makeText(this, "ログイン失敗: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}

package jp.ac.nkc_cta09.graduationwork

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CheckUserInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_check_user_info)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

            checkUserInfo()

    }

    private fun checkUserInfo() {

        val uid = FirebaseAuth.getInstance().currentUser?.uid

        if (uid == null) {

            Toast.makeText(this, "ログインしていません", Toast.LENGTH_SHORT).show()
            finish()
            return

        }

        val database = FirebaseDatabase.getInstance().reference

        database.child("users").child(uid).get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {

                    val intent = Intent(this, UserinfoActivity::class.java)
                    startActivity(intent)
                    finish()

                }
            }

            .addOnFailureListener { e ->
                Toast.makeText(this, "ユーザー情報確認失敗: ${e.message}", Toast.LENGTH_SHORT).show()

            }
    }
}

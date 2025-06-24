package jp.ac.nkc_cta09.graduationwork

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import android.app.DatePickerDialog
import android.widget.DatePicker
import java.util.Calendar

class UserinfoActivity : AppCompatActivity() {
    private lateinit var editTextName: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var editTextDate: EditText
    private lateinit var buttonSave: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_userinfo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        editTextName = findViewById<EditText>(R.id.editTextName)
        editTextPhone = findViewById(R.id.editTextPhone)
        editTextDate = findViewById(R.id.editTextDate)
        buttonSave = findViewById(R.id.buttonSave)

        buttonSave.setOnClickListener {
            saveUserInfo()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        editTextDate.setOnClickListener {
            showDatePickerDialog()
        }



    }

    private fun saveUserInfo() {
        val name = editTextName.text.toString().trim()
        val phone = editTextPhone.text.toString().trim()
        val date = editTextDate.text.toString().trim()

        if (name.isEmpty() || phone.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "すべての項目を入力してください", Toast.LENGTH_SHORT).show()
            return
        }

        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid == null) {
            Toast.makeText(this, "ユーザーがログインしていません", Toast.LENGTH_SHORT).show()
            return
        }

        val userInfo = mapOf(
            "name" to name,
            "phone" to phone,
            "date" to date,
            "email" to FirebaseAuth.getInstance().currentUser?.email // メールアドレスも一緒に保存
        )

        val database = FirebaseDatabase.getInstance().reference
        database.child("users").child(uid).setValue(userInfo)
            .addOnSuccessListener {
                Toast.makeText(this, "ユーザー情報を保存しました", Toast.LENGTH_SHORT).show()
                finish() // 画面を閉じる（不要なら消してOK）
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "保存に失敗しました: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = String.format("%04d/%02d/%02d", selectedYear, selectedMonth + 1, selectedDay)
                editTextDate.setText(selectedDate)
            }, year, month, day)

        datePickerDialog.show()
    }
}



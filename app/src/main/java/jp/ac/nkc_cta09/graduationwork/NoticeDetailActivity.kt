package jp.ac.nkc_cta09.graduationwork

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class NoticeDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice_detail)

        val title = intent.getStringExtra("title")
        val body = intent.getStringExtra("body")
        val date = intent.getStringExtra("date")
        val backbutton: Button = findViewById(R.id.backbutton)

        findViewById<TextView>(R.id.textViewDetailTitle).text = title
        findViewById<TextView>(R.id.textViewDetailBody).text = body
        findViewById<TextView>(R.id.textViewDetailDate).text = date

        backbutton.setOnClickListener {
            finish()
        }

    }


}

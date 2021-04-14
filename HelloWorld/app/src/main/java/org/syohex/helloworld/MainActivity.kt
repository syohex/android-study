package org.syohex.helloworld

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonClickMe = findViewById<Button>(R.id.button)
        val myTextView = findViewById<TextView>(R.id.textView)
        var timeClicked = 0
        buttonClickMe.setOnClickListener {
            timeClicked += 1
            myTextView.text = timeClicked.toString()
            Toast.makeText(this@MainActivity, "Hi Bomber", Toast.LENGTH_LONG).show()
        }
    }
}
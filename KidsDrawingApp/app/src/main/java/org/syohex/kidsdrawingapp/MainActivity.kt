package org.syohex.kidsdrawingapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.syohex.kidsdrawingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        binding.drawingView.setSizeForBrush(20.0f)
    }
}
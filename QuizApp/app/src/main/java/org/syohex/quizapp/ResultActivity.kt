package org.syohex.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.syohex.quizapp.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        val root = binding.root
        setContentView(root)

        val userName = intent.getStringExtra(Constants.USER_NAME)
        binding.tvName.text = userName

        val correctAnswers = intent.getIntExtra(Constants.CORRECT_ANSWERS, 10)
        val totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)

        val scoreText = "${totalQuestions}中 ${correctAnswers}問 正解しました"
        binding.tvScore.text = scoreText

        binding.buttonFinish.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
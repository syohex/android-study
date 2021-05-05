package org.syohex.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import org.syohex.quizapp.databinding.ActivityQuizQuestionsBinding

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityQuizQuestionsBinding
    private var _currentPosition: Int = 1
    private var _questionsList: List<Question>? = null
    private var _selectedOptionPosition: Int = 0
    private var _correctAnswers: Int = 0
    private var _userName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        _userName = intent.getStringExtra(Constants.USER_NAME)

        _questionsList = Constants.getQuestions(this, "quiz.json")
        setQuestion()

        binding.tvOptionOne.setOnClickListener(this)
        binding.tvOptionTwo.setOnClickListener(this)
        binding.tvOptionThree.setOnClickListener(this)
        binding.tvOptionFour.setOnClickListener(this)

        binding.buttonSubmit.setOnClickListener(this)
    }

    private fun setQuestion() {
        val question = _questionsList!![_currentPosition - 1]

        defaultOptionsView()
        if (_currentPosition == _questionsList!!.size) {
            binding.buttonSubmit.text = "FINISH"
        } else {
            binding.buttonSubmit.text = "SUBMIT"
        }

        binding.progressBar.progress = _currentPosition
        val progressText = "${_currentPosition}/${binding.progressBar.max}"
        binding.tvProgress.text = progressText

        binding.tvQuestion.text = question.question
        binding.ivImage.setImageResource(question.imageId)
        binding.tvOptionOne.text = question.options[0]
        binding.tvOptionTwo.text = question.options[1]
        binding.tvOptionThree.text = question.options[2]
        binding.tvOptionFour.text = question.options[3]
    }

    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()
        options.add(0, binding.tvOptionOne)
        options.add(1, binding.tvOptionTwo)
        options.add(2, binding.tvOptionThree)
        options.add(3, binding.tvOptionFour)

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
            )
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_option_one -> {
                selectedOptionView(binding.tvOptionOne, 1)
            }
            R.id.tv_option_two -> {
                selectedOptionView(binding.tvOptionTwo, 2)
            }
            R.id.tv_option_three -> {
                selectedOptionView(binding.tvOptionThree, 3)
            }
            R.id.tv_option_four -> {
                selectedOptionView(binding.tvOptionFour, 4)
            }
            R.id.button_submit -> {
                if (_selectedOptionPosition == 0) {
                    _currentPosition += 1

                    when {
                        _currentPosition <= _questionsList!!.size -> {
                            setQuestion()
                        }
                        else -> {
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, _userName)
                            intent.putExtra(Constants.CORRECT_ANSWERS, _correctAnswers)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, _questionsList!!.size)
                            startActivity(intent)
                            finish()
                        }
                    }
                } else {
                    val question = _questionsList?.get(_currentPosition - 1)
                    if (question!!.answer != _selectedOptionPosition) {
                        answerView(_selectedOptionPosition, R.drawable.wrong_option_border_bg)
                    } else {
                        _correctAnswers += 1
                    }

                    answerView(question.answer, R.drawable.correct_option_border_bg)

                    if (_currentPosition == _questionsList!!.size) {
                        binding.buttonSubmit.text = "Finish"
                    } else {
                        binding.buttonSubmit.text = "Go to Next Question"
                    }

                    _selectedOptionPosition = 0
                }
            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> {
                binding.tvOptionOne.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            2 -> {
                binding.tvOptionTwo.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            3 -> {
                binding.tvOptionThree.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            4 -> {
                binding.tvOptionFour.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        defaultOptionsView()
        _selectedOptionPosition = selectedOptionNum

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border_bg
        )
    }
}
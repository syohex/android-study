package org.syohex.quizapp

import android.content.Context
import com.google.gson.GsonBuilder

object Constants {
    const val USER_NAME: String = "user_name"
    const val TOTAL_QUESTIONS: String = "total_question"
    const val CORRECT_ANSWERS: String = "correct_answers"

    fun getQuestions(context: Context, fileName: String): List<Question> {
        val inputStream = context.assets.open(fileName)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()

        val gson = GsonBuilder().create()
        val questions = gson.fromJson(String(buffer, Charsets.UTF_8), Array<Question>::class.java).toList()
        repeat(questions.size) {
            val id = context.resources.getIdentifier(
                questions[it].image,
                "drawable",
                context.packageName
            )
            questions[it].imageId = id
        }

        return questions.shuffled()
    }
}
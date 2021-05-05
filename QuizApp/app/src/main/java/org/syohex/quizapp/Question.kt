package org.syohex.quizapp

data class Question(
    val question: String,
    val image: String,
    val options: List<String>,
    val answer: Int,
) {
    var imageId: Int = 0
}
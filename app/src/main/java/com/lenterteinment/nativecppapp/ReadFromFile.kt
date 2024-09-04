package com.lenterteinment.nativecppapp

import android.content.Context
import kotlin.random.Random

class ReadFromFile(private val context: Context) {

    private var text: String = ""
    private lateinit var lines: Array<String>
    private var lineQuestion: String = ""
    private var lineCorrectAnswer: String = ""
    private lateinit var wordsCorrectAnswer: Array<String>
    private var count = 0
    private var chosen = 60
    private var minChosen = 60

    private val filesMinChosens = mapOf(
        "constructions_with_koto" to 60,
        "degrees_of_politeness" to 250,
        "homogeneous" to 220,
        "homogeneous_members_of_a_sentence" to 220,
        "loss_indicators" to 144,
        "modal_constructions" to 70,
        "modal_constructions2" to 180,
        "negative" to 188,
        "politeness" to 258,
        "pronouns2" to 140,
        "sentence_interrogative" to 148,
        "sentence_negative" to 182,
        "sentence_question" to 148
    )

    private val maxChosen: Int
        get() = lines.size

    private val randomChosen: Int
        get() = Random.nextInt(minChosen, maxChosen)

    private val randomOddChosen: Int
        get() {
            val randomChoosen = randomChosen
            return if (randomChoosen % 2 == 0) randomChoosen else randomChoosen + 1
        }

    private fun setRandomFileMinChoose() {
        val (file, minChoose) = filesMinChosens.entries.random()
        minChosen = minChoose
        readFile(file)
    }

    private fun readFile(fileName: String) {
        val resourceId = context.resources.getIdentifier(fileName, "raw", context.packageName)
        val inputStream = context.resources.openRawResource(resourceId)
        text = inputStream.bufferedReader().use { it.readText() }
    }

    fun getQuizData(): Pair<String, List<String>> {
        setRandomFileMinChoose()
        split()
        changeChosen()
        choose()
        splitOnWordsCorrectAnswer()

        return Pair(lineQuestion, listOf(lineCorrectAnswer))
    }

    private fun changeChosen() {
        chosen = randomOddChosen
    }

    private fun questionSet() {
        // Set the question text to your UI element
    }

    private fun correctAnswersSet() {
        val word = wordsCorrectAnswer[count]
        // Update the UI with the correct answer
        count++
    }

    private fun splitOnWordsCorrectAnswer() {
        wordsCorrectAnswer = lineCorrectAnswer.split(" ").toTypedArray()
    }

    private fun choose() {
        lineQuestion = lines[chosen]
        lineCorrectAnswer = lines[chosen - 1]
    }

    private fun split() {
        lines = text.split("\n").toTypedArray()
    }

    init {
        setRandomFileMinChoose()
        split()
        changeChosen()
        choose()
        splitOnWordsCorrectAnswer()
        questionSet()
        correctAnswersSet()
    }
}


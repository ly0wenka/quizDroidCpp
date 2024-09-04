package com.lenterteinment.nativecppapp

import android.os.Bundle
import android.view.View.generateViewId
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding

class MainActivity : AppCompatActivity() {

    private lateinit var questionTextView: TextView
    private lateinit var answersRadioGroup: RadioGroup
    private lateinit var submitAnswerButton: Button
    private lateinit var readFromFile: ReadFromFile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ReadFromFile
        readFromFile = ReadFromFile(this)

        // Create the root layout (LinearLayout)
        val rootLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(16)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        }

        // Create the TextView for the question
        questionTextView = TextView(this).apply {
            id = generateViewId()
            textSize = 18f
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = 16
            }
        }

        // Create the RadioGroup for the answer choices
        answersRadioGroup = RadioGroup(this).apply {
            id = generateViewId()
            orientation = RadioGroup.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        // Create the Button to submit the answer
        submitAnswerButton = Button(this).apply {
            id = generateViewId()
            text = "Submit Answer"
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = 16
            }
        }

        // Add the views to the root layout
        rootLayout.addView(questionTextView)
        rootLayout.addView(answersRadioGroup)
        rootLayout.addView(submitAnswerButton)

        // Set the root layout as the content view of the activity
        setContentView(rootLayout)

        // Display quiz
        displayQuiz()
    }

    private fun displayQuiz() {
        // Example quiz data for testing
        val quiz = readFromFile.getQuizData()

        // Display the question
        questionTextView.text = quiz.first

        // Clear previous options if any
        answersRadioGroup.removeAllViews()

        // Add answer choices
        for (answer in quiz.second) {
            val radioButton = RadioButton(this).apply {
                text = answer
            }
            answersRadioGroup.addView(radioButton)
        }

        // Handle answer submission
        submitAnswerButton.setOnClickListener {
            val selectedRadioButtonId = answersRadioGroup.checkedRadioButtonId
            if (selectedRadioButtonId != -1) {
                val selectedAnswer = findViewById<RadioButton>(selectedRadioButtonId).text
                Toast.makeText(this, "Selected Answer: $selectedAnswer", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

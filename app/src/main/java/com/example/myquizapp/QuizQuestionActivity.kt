package com.example.myquizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat


class QuizQuestionActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition: Int = 1
    private var mQuestionsList: ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = 0
    private var mUserName: String? = null
    private var mCorrectAnswers:Int = 0

    private var progressBar: ProgressBar? = null
    private var tvProgressBar: TextView? = null
    private var tvQuestion: TextView? = null
    private var ivImage: ImageView? = null

    private var optionOne: TextView? = null
    private var optionTwo: TextView? = null
    private var optionThree: TextView? = null
    private var optionFour: TextView? = null
    private var buttonSubmit: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)

        mUserName = intent.getStringExtra(Constants.USERNAME)

        progressBar = findViewById(R.id.progressBar)
        tvProgressBar = findViewById(R.id.tv_Progress)
        tvQuestion = findViewById(R.id.tv_Question)
        ivImage = findViewById(R.id.iv_flag)
        optionOne = findViewById(R.id.tv_optionOne)
        optionTwo = findViewById(R.id.tv_optionTwo)
        optionThree = findViewById(R.id.tv_optionThree)
        optionFour = findViewById(R.id.tv_optionFour)
        buttonSubmit = findViewById(R.id.buttonSubmit)

        optionOne?.setOnClickListener(this)
        optionTwo?.setOnClickListener(this)
        optionThree?.setOnClickListener(this)
        optionFour?.setOnClickListener(this)
        buttonSubmit?.setOnClickListener(this)

        mQuestionsList = Constants.getQuestions()

        SetQuestion()

    }

    private fun SetQuestion() {
        var question: Question = mQuestionsList!![mCurrentPosition - 1]
        ivImage?.setImageResource(question.image)
        progressBar?.progress = mCurrentPosition
        tvProgressBar?.text = "$mCurrentPosition/${progressBar?.max}"
        tvQuestion?.text = question.question
        optionOne?.text = question.option1
        optionTwo?.text = question.option2
        optionThree?.text = question.option3
        optionFour?.text = question.option4

        if (mCurrentPosition == mQuestionsList!!.size) {
            buttonSubmit?.text = "Finish"
        } else {
            buttonSubmit?.text = "Submit"
        }
    }

    private fun defaultOptionView() {
        val option = ArrayList<TextView>()
        optionOne?.let {
            option.add(0, it)
        }
        optionTwo?.let {
            option.add(1, it)
        }
        optionThree?.let {
            option.add(2, it)
        }
        optionFour?.let {
            option.add(3, it)
        }

        for (option in option) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_order_option)

        }
    }

    private fun selectedOptionView(tv: TextView, SelectedOptionNum: Int) {
        defaultOptionView()
        mSelectedOptionPosition = SelectedOptionNum
        tv.setTextColor(Color.parseColor("#363a43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border)

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.tv_optionOne -> {
                optionOne?.let {
                    selectedOptionView(it, 1)
                }
            }

            R.id.tv_optionTwo -> {
                optionTwo?.let {
                    selectedOptionView(it, 2)
                }
            }

            R.id.tv_optionThree -> {
                optionThree?.let {
                    selectedOptionView(it, 3)
                }
            }

            R.id.tv_optionFour -> {
                optionFour?.let {
                    selectedOptionView(it, 4)
                }
            }

            R.id.buttonSubmit -> {
                if (mSelectedOptionPosition == 0) {
                    mCurrentPosition++
                    when {

                        mCurrentPosition <= mQuestionsList!!.size -> {

                            SetQuestion()
                        }

                        else -> {

                            val intent = Intent(this,activity_result::class.java)
                            intent.putExtra(Constants.USERNAME,mUserName)
                            intent.putExtra(Constants.Correct_Answers,mCorrectAnswers)
                            intent.putExtra(Constants.TOTAL_QUESTIONS,mQuestionsList?.size)
                            startActivity(intent)
                            finish()

                            Toast.makeText(
                                this,
                                "You have successfully completed the quiz.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    val question = mQuestionsList?.get(mCurrentPosition - 1)

                    // This is to check if the answer is wrong
                    if (question!!.correctAns != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_order_option)
                    }else{
                        mCorrectAnswers++
                    }

                    // This is for correct answer
                    answerView(question.correctAns, R.drawable.correct_order_option)

                    if (mCurrentPosition == mQuestionsList!!.size) {
                        buttonSubmit?.text = "FINISH"
                    } else {
                        buttonSubmit?.text = "GO TO NEXT QUESTION"
                    }

                    mSelectedOptionPosition = 0
                }
            }
        }
    }

    private fun answerView(answer: Int, drwarableView: Int) {
        when (answer) {
            1 -> {
                optionOne?.background = ContextCompat.getDrawable(
                    this,
                    drwarableView
                )
            }

            2 -> {
                optionTwo?.background = ContextCompat.getDrawable(
                    this,
                    drwarableView
                )
            }

            3 -> {
                optionThree?.background = ContextCompat.getDrawable(
                    this,
                    drwarableView
                )
            }

            4 -> {
                optionFour?.background = ContextCompat.getDrawable(
                    this,
                    drwarableView
                )
            }
        }

    }
}
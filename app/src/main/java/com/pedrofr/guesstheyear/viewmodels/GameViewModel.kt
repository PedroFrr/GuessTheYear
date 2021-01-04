package com.pedrofr.guesstheyear.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pedrofr.guesstheyear.data.model.*
import kotlin.math.min

class GameViewModel @ViewModelInject constructor(
    repository: QuestionRepository
) : ViewModel() {

    private val questions: MutableList<Question> = mutableListOf(
        Question(text = "What is Android Jetpack?",
            answers = listOf("All of these", "Tools", "Documentation", "Libraries")),
        Question(text = "What is the base class for layouts?",
            answers = listOf("ViewGroup", "ViewSet", "ViewCollection", "ViewRoot")),
        Question(text = "What layout do you use for complex screens?",
            answers = listOf("ConstraintLayout", "GridLayout", "LinearLayout", "FrameLayout")),
        Question(text = "What do you use to push structured data into a layout?",
            answers = listOf("Data binding", "Data pushing", "Set text", "An OnClick method")),
        Question(text = "What method do you use to inflate layouts in fragments?",
            answers = listOf("onCreateView()", "onActivityCreated()", "onCreateLayout()", "onInflateLayout()")),
        Question(text = "What's the build system for Android?",
            answers = listOf("Gradle", "Graddle", "Grodle", "Groyle")),
        Question(text = "Which class do you use to create a vector drawable?",
            answers = listOf("VectorDrawable", "AndroidVectorDrawable", "DrawableVector", "AndroidVector")),
        Question(text = "Which one of these is an Android navigation component?",
            answers = listOf("NavController", "NavCentral", "NavMaster", "NavSwitcher")),
        Question(text = "Which XML element lets you register an activity with the launcher activity?",
            answers = listOf("intent-filter", "app-registry", "launcher-registry", "app-launcher")),
        Question(text = "What do you use to mark a layout for data binding?",
            answers = listOf("<layout>", "<binding>", "<data-binding>", "<dbinding>"))
    )
    private var questionIndex = 0

    private val _gameState = MutableLiveData<GameState>()
    fun getGameState(): LiveData<GameState> = _gameState

    private val _currentQuestion = MutableLiveData<Question>()
    fun getCurrentQuestion(): LiveData<Question> = _currentQuestion

    private val _answers = MutableLiveData<List<String>>()
    fun getAnswers(): LiveData<List<String>> = _answers

    private lateinit var answers: MutableList<String>

    private lateinit var currentQuestion: Question


    private var questionInxdex = 0
    //Minimum of 3 questions
    private val numQuestions = min((questions.size + 1) / 2, 3)

    init {
//        viewModelScope.launch {
//            questions = repository.getQuestions().toMutableList()
//        }
        randomizeQuestions();
    }

    // randomize the questions and set the first question
    private fun randomizeQuestions() {
        questions.shuffle()
        questionIndex = 0
        setQuestion()
    }

    // Sets the question and randomizes the answers.  This only changes the data, not the UI.
    // Calling invalidateAll on the FragmentGameBinding updates the data.
    private fun setQuestion() {

        currentQuestion = questions[questionIndex]
        // randomize the answers into a copy of the array
        answers = questions[questionIndex].answers.toMutableList()
        // and shuffle them
        answers.shuffle()

        //update list of answers and question
        _currentQuestion.value = currentQuestion
        _answers.value = answers
    }

    fun setAnswer(answerIndex: Int) {
        if (answers[answerIndex] == currentQuestion.answers[0]) {
            questionIndex++
            //Advance to next question
            if (questionIndex < numQuestions) {
                currentQuestion = questions[questionIndex]
                setQuestion()
            } else {
                // We've won!  Navigate to the gameWonFragment.
                _gameState.postValue(Won)

            }
        } else {
            // Game over! A wrong answer sends us to the gameOverFragment.
            _gameState.postValue(Lost)
        }
    }


}
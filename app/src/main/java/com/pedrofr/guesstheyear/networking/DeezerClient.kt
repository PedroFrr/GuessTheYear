package com.pedrofr.guesstheyear.networking

import com.fevziomurtekin.deezer.domain.network.DeezerService
import com.pedrofr.guesstheyear.data.model.Failure
import com.pedrofr.guesstheyear.data.model.Question
import com.pedrofr.guesstheyear.data.model.Success
import com.pedrofr.guesstheyear.networking.response.TrackResponse
import kotlinx.coroutines.delay
import javax.inject.Inject

class DeezerClient @Inject constructor(
    private val deezerService: DeezerService
) {

    suspend fun fetchQuestions() =
        try {
            Success(RemoteApi.fetchQuestions())
        } catch (error: Throwable) {
            //TODO change failure to also return error
            Failure
        }

    suspend fun fetchTracks() =
        try {
            //TODO replace with real API call
//            val response = deezerService.fetchTrack(trackId)
            val response = RemoteApi.fetchTracks()
            Success(response)
        } catch (error: Throwable) {
            Failure
        }

}

object RemoteApi {
    suspend fun fetchQuestions(): List<Question> {
        delay(1000)

        return listOf(
            Question(
                text = "What is Android Jetpack?",
                answers = listOf("All of these", "Tools", "Documentation", "Libraries")
            ),
            Question(
                text = "What is the base class for layouts?",
                answers = listOf("ViewGroup", "ViewSet", "ViewCollection", "ViewRoot")
            ),
            Question(
                text = "What layout do you use for complex screens?",
                answers = listOf("ConstraintLayout", "GridLayout", "LinearLayout", "FrameLayout")
            ),
            Question(
                text = "What do you use to push structured data into a layout?",
                answers = listOf("Data binding", "Data pushing", "Set text", "An OnClick method")
            ),
            Question(
                text = "What method do you use to inflate layouts in fragments?",
                answers = listOf(
                    "onCreateView()",
                    "onActivityCreated()",
                    "onCreateLayout()",
                    "onInflateLayout()"
                )
            ),
            Question(
                text = "What's the build system for Android?",
                answers = listOf("Gradle", "Graddle", "Grodle", "Groyle")
            ),
            Question(
                text = "Which class do you use to create a vector drawable?",
                answers = listOf(
                    "VectorDrawable",
                    "AndroidVectorDrawable",
                    "DrawableVector",
                    "AndroidVector"
                )
            ),
            Question(
                text = "Which one of these is an Android navigation component?",
                answers = listOf("NavController", "NavCentral", "NavMaster", "NavSwitcher")
            ),
            Question(
                text = "Which XML element lets you register an activity with the launcher activity?",
                answers = listOf(
                    "intent-filter",
                    "app-registry",
                    "launcher-registry",
                    "app-launcher"
                )
            ),
            Question(
                text = "What do you use to mark a layout for data binding?",
                answers = listOf("<layout>", "<binding>", "<data-binding>", "<dbinding>")
            )
        )
    }

    //TODO remove when I find an API that returns this data
    /*
    Mock network call for Track list
     */
    suspend fun fetchTracks(): List<TrackResponse> {
        delay(1000)

        return listOf(
            TrackResponse(
                title = "Slipping Away",
                releaseDate = "2001-03-07",
                preview = "https://cdns-preview-d.dzcdn.net/stream/c-deda7fa9316d9e9e880d2c6207e92260-8.mp3"
            ),
            TrackResponse(
                title = "Questioning my Mind",
                releaseDate = "2002-03-07",
                preview = "https://cdns-preview-d.dzcdn.net/stream/c-deda7fa9316d9e9e880d2c6207e92260-8.mp3"
            ),
            TrackResponse(
                title = "Blind Love",
                releaseDate = "2003-03-07",
                preview = "https://cdns-preview-d.dzcdn.net/stream/c-deda7fa9316d9e9e880d2c6207e92260-8.mp3"
            ),
            TrackResponse(
                title = "Left Side",
                releaseDate = "2004-03-07",
                preview = "https://cdns-preview-d.dzcdn.net/stream/c-deda7fa9316d9e9e880d2c6207e92260-8.mp3"
            ),
            TrackResponse(
                title = "What a Lovely Day",
                releaseDate = "2005-03-07",
                preview = "https://cdns-preview-d.dzcdn.net/stream/c-deda7fa9316d9e9e880d2c6207e92260-8.mp3"
            ),
        )
    }
}
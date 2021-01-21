package com.pedrofr.guesstheyear.ui

import com.pedrofr.guesstheyear.core.Success
import com.pedrofr.guesstheyear.data.db.entities.DbTracks
import com.pedrofr.guesstheyear.domain.repository.Repository
import javax.inject.Inject
import kotlin.random.Random

class GameFactoryImpl @Inject constructor(
    private val repository: Repository
) : GameFactory {

    override suspend fun buildGame(): Game? {
        val results = repository.getTracks()
        return if (results is Success) {
            val questions = buildQuestions(results.data.shuffled())
            return Game(questions)

        } else {
            //operation not successful
            null
        }
    }

    private fun buildQuestions(tracks: List<DbTracks>): List<Question> = tracks.map { track ->
        //TODO the correct option might not be selected (use scan)
        val options: List<String> = (1..4).map {
            Random.nextInt(
                track.releaseYear.minus(2),
                track.releaseYear.plus(3)
            ).toString()
        } //TODO review this answers assignment
        Question(
            trackTitle = track.title,
            options = options,
            correctOption = track.releaseYear.toString(),
            trackPreview = track.preview,
            albumCover = track.albumCover
        )
    }
}
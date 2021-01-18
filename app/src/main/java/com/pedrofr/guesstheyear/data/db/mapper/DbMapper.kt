package com.pedrofr.guesstheyear.data.db.mapper

import com.pedrofr.guesstheyear.data.db.entities.DbQuestions
import com.pedrofr.guesstheyear.domain.model.Questions

interface DbMapper {

    fun mapDbQuestionsToDomain (questions: List<DbQuestions>): List<Questions>

    fun mapDomainQuestionsToDb (questions: List<Questions>): List<DbQuestions>
}
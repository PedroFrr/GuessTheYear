package com.pedrofr.guesstheyear.data.db.mapper

import com.pedrofr.guesstheyear.data.db.entities.DbQuestions
import com.pedrofr.guesstheyear.domain.model.Questions

class DbMapperImpl: DbMapper {
    override fun mapDbQuestionsToDomain(questions: List<DbQuestions>): List<Questions> {
        TODO("Not yet implemented")
    }

    override fun mapDomainQuestionsToDb(questions: List<Questions>): List<DbQuestions> {
        TODO("Not yet implemented")
    }
}
package com.pedrofr.guesstheyear.data.model

/**
 * Represents the Success and Failure cases from the Remote API.
 */
sealed class Result<out T : Any>

data class Success<out T : Any>(val data: T) : Result<T>()

object Failure : Result<Nothing>()

object Loading: Result<Nothing>()
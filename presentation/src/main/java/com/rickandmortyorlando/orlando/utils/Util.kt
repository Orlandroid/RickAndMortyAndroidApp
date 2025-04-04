package com.rickandmortyorlando.orlando.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.rickandmortyorlando.orlando.R
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


fun getColorStatus(status: String? = "unknown", context: Context): Int {
    return when (status) {
        "Alive" -> {
            context.resources.getColor(R.color.alive)
        }

        "Dead" -> {
            context.resources.getColor(R.color.dead)
        }

        "unknown" -> {
            context.resources.getColor(R.color.unknown)
        }

        else -> {
            context.resources.getColor(R.color.unknown)
        }
    }
}

fun getColorStatusResource(status: String? = "unknown"): Int {
    return when (status) {
        "Alive" -> {
            R.color.alive
        }

        "Dead" -> {
            R.color.dead
        }

        "unknown" -> {
            R.color.unknown
        }

        else -> {
            R.color.unknown
        }
    }
}

@Composable
fun String.getColorStatus(): Color {
    when (this) {
        "Alive" -> {
            return colorResource(R.color.alive)
        }

        "Dead" -> {
            return colorResource(R.color.dead)
        }

        "unknown" -> {
            return colorResource(R.color.unknown)
        }

        else -> {
            return colorResource(R.color.unknown)
        }
    }
}

fun getNumberFromUrWithPrefix(urlWithNumberInTheFinalCharacter: String, prefix: String): Int {
    return urlWithNumberInTheFinalCharacter.split("$prefix/")[1].toInt()
}

fun getListOfNumbersFromUrlWithPrefix(
    episodesString: List<String>,
    prefix: String
): String {
    val episodes = arrayListOf<Int>()
    episodesString.forEach {
        episodes.add(it.split("$prefix/")[1].toInt())
    }
    return removeCharactersForEpisodesList(episodes.toString())
}

fun removeCharactersForEpisodesList(episodesList: String): String {
    return episodesList.replace("[", "").replace("]", "").replace(" ", "")
}

sealed class ErrorType {
    class HttpException(
        errorBody: ResponseBody?,
        messageError: String,
        val responseCode: Int
    ) :
        ErrorType()

    class SocketTimeoutException(messageError: String) : ErrorType()
    class UnknownHostException(messageError: String) : ErrorType()
    object Unknown : ErrorType()
}

fun getTypeOfError(exception: Exception): ErrorType {
    when (exception) {
        is HttpException -> {
            val errorBody = exception.response()?.errorBody()
            val errorCode = exception.response()?.code()
            return ErrorType.HttpException(errorBody, exception.message(), errorCode ?: -1)
        }

        is SocketTimeoutException -> {
            return ErrorType.SocketTimeoutException(exception.message ?: "")
        }

        is UnknownHostException -> {
            return ErrorType.UnknownHostException(exception.message ?: "")
        }

        else -> {
            return ErrorType.Unknown
        }
    }
}


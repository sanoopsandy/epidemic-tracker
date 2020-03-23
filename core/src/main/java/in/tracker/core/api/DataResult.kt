package `in`.tracker.core.api

sealed class DataResult<out T : Any> {

    data class Progress<out T : Any>(val loading: Boolean) : DataResult<T>()
    data class Success<out T : Any>(val data: T) : DataResult<T>()
    data class Failure<out T : Any>(val error: Throwable) : DataResult<T>()

    companion object {
        fun <T : Any> loading(progress: Boolean): DataResult<T> =
            Progress(progress)

        fun <T : Any> success(data: T): DataResult<T> =
            Success(data)

        fun <T : Any> failure(error: Throwable): DataResult<T> =
            Failure(error)
    }
}
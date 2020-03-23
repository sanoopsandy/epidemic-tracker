package `in`.tracker.core.api

import `in`.tracker.core.utils.loading
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Helper class to fetch data from network and save it to DB
 * Data is always fetched from DB. API calls are made to update entries in DB
 * */
abstract class FetchDataHelper<T : Any, R : Any> {

    // Always load the data from DB initially
    // Fetch the data from network and add it to the resource
    private val result = MediatorLiveData<DataResult<T>>()
    private var shouldRefresh = true

    init {
        result.loading(true)
        val dbSource = this.loadFromDb()
        result.addSource(dbSource) { newData ->
            newData?.let { result.value = DataResult.success(it) }
        }
        GlobalScope.launch { fetchFromNetwork(dbSource) }
    }

    /**
     *  Function that makes API calls and updates results in DB.
     * */
    private suspend fun fetchFromNetwork(dbSource: LiveData<T>) {
        try {
            val response = createCall()
            saveCallResult(response)
            withContext(Dispatchers.Main) {
                result.removeSource(dbSource)
                result.addSource(loadFromDb()) { newData ->
                    newData?.let { data ->
                        result.loading(false)
                        result.postValue(DataResult.success(data))
                    }
                }
            }
        } catch (ex: Exception) {
            withContext(Dispatchers.Main) {
                result.removeSource(dbSource)
                result.addSource(dbSource) { _ ->
                    result.loading(false)
                    result.value = DataResult.failure(ex)
                }
            }

        }
    }

    /**
     * Function to convert the response to model required by the view and save to the DB
     * @param model Data object that is received from api calls
     * */
    @WorkerThread
    protected abstract fun saveCallResult(model: R)

    /**
     * Function to load data from local DB
     * */
    @MainThread
    protected abstract fun loadFromDb(): LiveData<T>

    /**
     * Function to make api call and get results
     * */
    @MainThread
    protected abstract suspend fun createCall(): R

    /**
     * Function to convert MediatorLiveData to LiveData used by the viewmodel
     * */
    fun getAsLiveData(): LiveData<DataResult<T>> {
        return result
    }
}
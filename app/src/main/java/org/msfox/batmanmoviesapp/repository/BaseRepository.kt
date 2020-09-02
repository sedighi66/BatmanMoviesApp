package org.msfox.batmanmoviesapp.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.msfox.batmanmoviesapp.AppCoroutineDispatchers
import org.msfox.batmanmoviesapp.api.NetworkResponse
import org.msfox.batmanmoviesapp.db.AppDb
import org.msfox.batmanmoviesapp.utils.exhaustive
import org.msfox.batmanmoviesapp.utils.wasted

/**
 * Created by mohsen on 04,July,2020
 */


/**
 * A generic class that can provide a resource backed by the network.
 *
 *
 * You can read more about it in the [Architecture
 * Guide](https://developer.android.com/arch).
 * @param <ResultType>
 * @param <RequestType>
</RequestType></ResultType> */
abstract class BaseRepository<ResultType, RequestType: Any>
constructor(private val dispatchers: AppCoroutineDispatchers) {

    private val result = MutableLiveData<Resource<ResultType>>()

    private fun fetchNetwork(deleteDbIfSuccess: Boolean) {
        CoroutineScope(dispatchers.main).launch {

            result.value = (Resource.loading(null))

            //Due to project requirement, we provide data from network first,
            //when we don't have any data from network, then we should get
            //it from database
            val apiResponse = createCall()
            when (apiResponse) {
                is NetworkResponse.Success -> {

                    result.value = successRequestToResult(
                        Resource.success(processResponse(apiResponse)))

                            withContext(dispatchers.IO) {
                                if (deleteDbIfSuccess)
                                     deleteDb()

                                saveCallResult(result.value!!)
                                //we want to increase nextPage
                                loadFromDb().wasted()
                            }

                }
                is NetworkResponse.ApiError -> {
                    withContext(dispatchers.main) {
                        result.value = (Resource.success(loadFromDb()))
                    }
                }
                is NetworkResponse.NetworkError -> {
                    withContext(dispatchers.main) {
                        onFetchFailed()
                        result.value = (Resource.error(apiResponse.error.message, loadFromDb()))
                    }
                }
                is NetworkResponse.UnknownError ->{
                    withContext(dispatchers.main) {
                        onFetchFailed()
                        result.value = Resource.error(apiResponse.error?.message, loadFromDb())
                    }
                }
            }.exhaustive
        }

    }
    protected open fun onFetchFailed() {}

    protected fun liveDataResult() = result as LiveData<Resource<ResultType>>

    @WorkerThread
    protected open suspend fun processResponse(response: NetworkResponse.Success<RequestType>) = response.body

    @MainThread
    protected abstract suspend fun createCall(): NetworkResponse<RequestType, Any>

    @MainThread
    protected abstract fun successRequestToResult(requestType: Resource<RequestType>): Resource<ResultType>

    @WorkerThread
    protected abstract suspend fun saveCallResult(item: Resource<ResultType>)

    @MainThread
    protected abstract suspend fun loadFromDb(): ResultType

    @WorkerThread
    protected abstract suspend fun deleteDb(): Int

    @MainThread
    protected fun loadNextPage() = fetchNetwork(false)

    @MainThread
    protected fun fetchNetwork() = fetchNetwork(true)
}

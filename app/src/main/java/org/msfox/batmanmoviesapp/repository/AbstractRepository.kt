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
import org.msfox.batmanmoviesapp.utils.exhaustive

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
abstract class AbstractRepository<ResultType: Any, RequestType: Any>
constructor(private val dispatchers: AppCoroutineDispatchers) {

    private val result = MutableLiveData<Resource<ResultType>>()

    init {
        fetchNetwork()
    }

    private fun fetchNetwork(deleteDbIfSuccess: Boolean = true) {
        CoroutineScope(dispatchers.IO).launch {
            withContext(dispatchers.main) {
                result.value = (Resource.loading(null))
            }

            val apiResponse = createCall()
            when (apiResponse) {
                is NetworkResponse.Success -> {
                    withContext(dispatchers.main) {
                        successRequestToResult(
                            Resource.success(processResponse(apiResponse))
                        ).let {
                            withContext(dispatchers.IO) {
                                if(deleteDbIfSuccess)
                                    deleteDb()

                                saveCallResult(it)
                                result.value = Resource.success(loadFromDb())
                            }
                        }
                    }
                }
                is NetworkResponse.ApiError -> {
                    withContext(dispatchers.main) {
                        result.value = Resource.success(loadFromDb())
                    }
                }
                is NetworkResponse.NetworkError -> {
                    withContext(dispatchers.main) {
                        onFetchFailed()
                        result.value = Resource.error(apiResponse.error.message, loadFromDb())
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

    @MainThread
    protected fun liveDataResult() = result as LiveData<Resource<ResultType>>

    protected open suspend fun processResponse(response: NetworkResponse.Success<RequestType>) = response.body

    @WorkerThread
    protected abstract suspend fun createCall(): NetworkResponse<RequestType, Any>

    protected abstract fun successRequestToResult(requestType: Resource<RequestType>): Resource<ResultType>

    @WorkerThread
    protected abstract suspend fun saveCallResult(item: Resource<ResultType>)

    @WorkerThread
    protected abstract suspend fun loadFromDb(): ResultType

    @WorkerThread
    protected abstract suspend fun deleteDb()

    @WorkerThread
    protected suspend fun loadNextPage(){
        fetchNetwork(false)
    }



}

/*
 * This file is created by Mohsen Sedighi with code name MSFox
 *
 * Copyright 8/9/20 11:07 PM belongs to Mohsen Sedighi from project BaManChallenge
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.msfox.batmanmoviesapp.api


import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.*
import java.io.IOException
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit


class NetworkResponseAdapter<S : Any, E : Any>(
    private val successType: Type,
    private val errorBodyConverter: Converter<ResponseBody, E>
) : CallAdapter<S, Call<NetworkResponse<S, E>>> {

    override fun responseType(): Type = successType

    override fun adapt(call: Call<S>): Call<NetworkResponse<S, E>> {
        return NetworkResponseCall(call, errorBodyConverter)
    }
    internal class NetworkResponseCall<S : Any, E : Any>(
        private val delegate: Call<S>,
        private val errorConverter: Converter<ResponseBody, E>
    ) : Call<NetworkResponse<S, E>> {

        override fun enqueue(callback: Callback<NetworkResponse<S, E>>) {
            return delegate.enqueue(object : Callback<S> {
                override fun onResponse(call: Call<S>, response: Response<S>) {
                    val body = response.body()
                    val code = response.code()
                    val error = response.errorBody()

                    if (response.isSuccessful) {
                        if (body != null) {
                            callback.onResponse(
                                this@NetworkResponseCall,
                                Response.success(NetworkResponse.Success(body))
                            )
                        } else {
                            // Response is successful but the body is null
                            callback.onResponse(
                                this@NetworkResponseCall,
                                Response.success(NetworkResponse.UnknownError(null))
                            )
                        }
                    } else {
                        val errorBody = when {
                            error == null -> null
                            error.contentLength() == 0L -> null
                            else -> try {
                                errorConverter.convert(error)
                            } catch (ex: Exception) {
                                null
                            }
                        }
                        if (errorBody != null) {
                            callback.onResponse(
                                this@NetworkResponseCall,
                                Response.success(NetworkResponse.ApiError(errorBody, code))
                            )
                        } else {
                            callback.onResponse(
                                this@NetworkResponseCall,
                                Response.success(NetworkResponse.UnknownError(null))
                            )
                        }
                    }
                }

                override fun onFailure(call: Call<S>, throwable: Throwable) {
                    val networkResponse = when (throwable) {
                        is IOException -> NetworkResponse.NetworkError(throwable)
                        else -> NetworkResponse.UnknownError(throwable)
                    }
                    callback.onResponse(this@NetworkResponseCall, Response.success(networkResponse))
                }
            })
        }

        override fun isExecuted() = delegate.isExecuted

        override fun clone() = NetworkResponseCall(delegate.clone(), errorConverter)

        override fun isCanceled() = delegate.isCanceled

        override fun cancel() = delegate.cancel()

        override fun execute(): Response<NetworkResponse<S, E>> {
            throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
        }

        override fun request(): Request = delegate.request()

        override fun timeout(): Timeout {
            return Timeout().timeout(10, TimeUnit.SECONDS)
        }
    }
}
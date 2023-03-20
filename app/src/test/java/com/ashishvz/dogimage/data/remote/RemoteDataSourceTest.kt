package com.ashishvz.dogimage.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.ashishvz.dogimage.api.DogApi
import com.ashishvz.dogimage.data.remote.models.RemoteDogData
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class RemoteDataSourceTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var dogApi: DogApi
    private lateinit var fakeRemoteDataSource: FakeRemoteDataSource

    @Before
    fun setUp() {
        dogApi = spy(DogApi::class.java)
        fakeRemoteDataSource = FakeRemoteDataSource(dogApi)
    }

    @Test
    fun `remote api which returns failed api`() {
        runTest {
            `when`(dogApi.getRemoteDog()).thenReturn(Response.error(404, byteArrayOf().toResponseBody()))
            fakeRemoteDataSource.getRemoteDogImage().test {
                assertThat(this.awaitItem()).isNull()
                this.awaitComplete()
            }
        }
    }

    @Test
    fun `remote api returns data`() {
        runTest {
            val mockedResponse = RemoteDogData("https:\\/\\/images.dog.ceo\\/breeds\\/terrier-toy\\/n02087046_3211.jpg", "success")
            `when`(dogApi.getRemoteDog()).thenReturn(Response.success(mockedResponse))
            fakeRemoteDataSource.getRemoteDogImage().test {
                val itemToTest = this.awaitItem()
                assertThat(itemToTest).isNotNull()
                assertThat(itemToTest).isEqualTo(mockedResponse)
                this.awaitComplete()
            }
        }
    }


}
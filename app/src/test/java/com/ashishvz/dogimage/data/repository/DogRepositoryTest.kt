package com.ashishvz.dogimage.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.ashishvz.dogimage.data.local.LocalDataSource
import com.ashishvz.dogimage.data.local.models.CachedDogData
import com.ashishvz.dogimage.data.remote.RemoteDataSource
import com.ashishvz.dogimage.data.remote.models.RemoteDogData
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.spy
import org.mockito.Mockito.`when`
import org.robolectric.RobolectricTestRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class DogRepositoryTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var localDataSource: LocalDataSource

    @MockK
    private lateinit var remoteDataSource: RemoteDataSource

    private lateinit var dogRepository: DogRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        localDataSource = spy(LocalDataSource::class.java)
        remoteDataSource = spy(RemoteDataSource::class.java)
        dogRepository = DogRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Test
    fun `get remote dog data returns data`() {
        runTest {
            val mockedResponse = RemoteDogData(
                "https:\\/\\/images.dog.ceo\\/breeds\\/terrier-toy\\/n02087046_3211.jpg",
                "success"
            )
            `when`(remoteDataSource.getRemoteDogImage())
                .thenReturn(flow { emit(mockedResponse) })
            dogRepository.getRemoteDogData().test {
                val itemToTest = this.awaitItem()
                this.awaitComplete()
                assertThat(itemToTest).isNotNull()
                assertThat(itemToTest).isEqualTo(mockedResponse)
                assertThat(itemToTest?.message).isEqualTo(mockedResponse.message)
            }
        }
    }

    @Test
    fun `get cached dog data which return empty list`() {
        runTest {
            val mockedResponse = mutableListOf<CachedDogData>()
            `when`(localDataSource.getCacheDogImageData()).thenReturn(flow { emit(mockedResponse) })
            dogRepository.getCachedDogData().test {
                val itemToTest = this.awaitItem()
                this.awaitComplete()
                assertThat(itemToTest).isNotNull()
                assertThat(itemToTest).isEmpty()
            }
        }
    }

    @Test
    fun `get cached dog data which return items`() {
        runTest {
            val mockedResponse = mutableListOf(CachedDogData("url1"), CachedDogData("url2"))
            `when`(localDataSource.getCacheDogImageData()).thenReturn(flow { emit(mockedResponse) })
            dogRepository.getCachedDogData().test {
                val itemToTest = this.awaitItem()
                this.awaitComplete()
                assertThat(itemToTest).isNotNull()
                assertThat(itemToTest.size).isEqualTo(2)
                assertThat(itemToTest[1].imgUrl).isEqualTo("url2")
            }
        }
    }
}
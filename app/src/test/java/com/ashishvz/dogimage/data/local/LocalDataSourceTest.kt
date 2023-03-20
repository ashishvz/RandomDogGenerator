package com.ashishvz.dogimage.data.local

import app.cash.turbine.test
import com.ashishvz.dogimage.data.local.models.CachedDogData
import com.ashishvz.dogimage.utility.Constants
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class LocalDataSourceTest {

    private lateinit var fakeLruCache: HashMap<Long, String>
    private lateinit var fakeLocalDataSource: FakeLocalDataSource

    @Before
    fun setup() {
        fakeLruCache = HashMap(Constants.CACHE_SIZE)
        fakeLocalDataSource = FakeLocalDataSource(fakeLruCache)
    }

    @Test
    fun `get initial lruCache should return empty list`() {
        runTest {
            fakeLocalDataSource.getCacheDogImageData().test {
                val cachedDataToTest = this.awaitItem()
                this.awaitComplete()
                assertThat(cachedDataToTest).isEqualTo(emptyList<CachedDogData>())
            }
        }
    }

    @Test
    fun `save img url in lruCache and retrieve back`() {
        runTest {
            val imgUrl = "https://dogImage"
            fakeLocalDataSource.cacheDogImageData(imgUrl)
            fakeLocalDataSource.getCacheDogImageData().test {
                val cachedDataToTest = this.awaitItem()
                this.awaitComplete()
                println(cachedDataToTest)
                assertThat(cachedDataToTest[0].imgUrl).isEqualTo(imgUrl)
            }
        }
    }
}
package com.ashishvz.dogimage.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.LruCache
import com.ashishvz.dogimage.R
import com.ashishvz.dogimage.utility.Constants
import org.koin.android.ext.android.get
import java.io.File
import java.io.FileOutputStream
import java.io.ObjectOutputStream

class MainActivity : AppCompatActivity() {
    private val cache: LruCache<Long, String> = get()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStop() {
        super.onStop()
        val file = File(this.cacheDir, Constants.CACHE_FILE_NAME)
        val outputStream = ObjectOutputStream(FileOutputStream(file))
        outputStream.writeObject(cache.snapshot())
        outputStream.close()
    }
}
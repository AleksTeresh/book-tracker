package com.example.booktracker

import android.app.Application
import com.pacoworks.rxpaper2.RxPaperBook

class BookTrackerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        RxPaperBook.init(applicationContext)
    }
}
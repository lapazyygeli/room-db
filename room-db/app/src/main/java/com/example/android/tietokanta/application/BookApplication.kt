package com.example.android.tietokanta.application

import android.app.Application
import com.example.android.tietokanta.data.repository.BookRepository

/**
 * This is not re-created on configuration changes. It is created when
 * the app launches and destroyed when your app process is destroyed.
 */
class BookApplication: Application() {

    /**
     * "Called when the application is starting, before any activity, service, or receiver objects
     * (excluding content providers) have been created." HUOM: manifestissa tää luokka on erikseen
     * merkitty. Tää on siis sen takii, että noita database hommia voi lähteä heti tekemään.
     */
    override fun onCreate() {
        super.onCreate()
        BookRepository.initialize(this)
    }
}
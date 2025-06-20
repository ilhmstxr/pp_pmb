package com.example.peduli_pangan_versipemmob

import android.app.Application
import com.google.firebase.FirebaseApp

/**
 * Custom Application class to handle one-time initializations, such as Firebase.
 * This ensures Firebase is ready before any other component of the app needs it.
 */
class PeduliPanganApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize Firebase
        FirebaseApp.initializeApp(this)
    }
}

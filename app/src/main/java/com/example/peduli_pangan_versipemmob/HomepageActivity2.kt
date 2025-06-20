package com.example.peduli_pangan_versipemmob

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.peduli_pangan_versipemmob.ui.main.HomepageActivity2Fragment

class HomepageActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage_activity2)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomepageActivity2Fragment.newInstance())
                .commitNow()
        }
    }
}
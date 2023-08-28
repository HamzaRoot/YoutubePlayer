package com.example.gracetechnologiestest.simpleclasses

import com.google.android.gms.ads.MobileAds

class Application : android.app.Application() {

    override fun onCreate() {
        super.onCreate()
        initAdsView()
    }

    private fun initAdsView() {
        MobileAds.initialize(this) { }
    }
}

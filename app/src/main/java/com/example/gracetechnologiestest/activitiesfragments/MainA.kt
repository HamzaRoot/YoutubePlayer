package com.example.gracetechnologiestest.activitiesfragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import androidx.databinding.DataBindingUtil
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.gracetechnologiestest.Constants
import com.example.gracetechnologiestest.R
import com.example.gracetechnologiestest.adapters.ViewPagerStatAdapter
import com.example.gracetechnologiestest.api.ApiCallback
import com.example.gracetechnologiestest.api.RetrofitRequest
import com.example.gracetechnologiestest.api.Singleton
import com.example.gracetechnologiestest.databinding.ActivityMainBinding
import com.example.gracetechnologiestest.datamodel.VideoDM
import com.example.gracetechnologiestest.simpleclasses.DataParser
import com.example.gracetechnologiestest.simpleclasses.Helper
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import org.json.JSONObject

class MainA : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding
    val dataList= ArrayList<VideoDM>()
    lateinit var pagerSatetAdapter: ViewPagerStatAdapter

    // set the fragments for all the videos list
    var oldSwipeValue = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        oldSwipeValue=Constants.addShowAfterVideoCount

        initControl()
        setTabs()
        getTestingUniqueAPI()
    }

    private fun initControl() {
        binding.swiperefresh.setOnRefreshListener(OnRefreshListener {
            oldSwipeValue = Constants.addShowAfterVideoCount
            dataList.clear()
            getTestingUniqueAPI()
        })

       binding.ivClose.setOnClickListener{
           binding.tabBottom.visibility= View.GONE
       }
    }

    fun setTabs() {
        dataList.clear()
        pagerSatetAdapter = ViewPagerStatAdapter(supportFragmentManager)
        binding.viewpager.setAdapter(pagerSatetAdapter)
        binding.viewpager.setOffscreenPageLimit(1)
        binding.viewpager.setOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    binding.swiperefresh.setEnabled(true)
                } else {
                    binding.swiperefresh.setEnabled(false)
                }
                if (position == 0 && pagerSatetAdapter != null && pagerSatetAdapter.count > 0) {
                    val fragment =
                        pagerSatetAdapter.getItem(binding.viewpager.getCurrentItem()) as VideosListF
                    Handler(Looper.getMainLooper()).postDelayed({
                        fragment.setPlayer(
                            true
                        )
                    }, 200)
                }
                if (position + 1 == oldSwipeValue) {
                    oldSwipeValue = position + 1 + Constants.addShowAfterVideoCount
                    initGoogleAdd()
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }




    var mInterstitialAd: InterstitialAd? = null
    fun initGoogleAdd() {
        var adRequest = AdRequest.Builder().build()
        InterstitialAd.load(binding.root.context, binding.root.context.getString(R.string.my_Interstitial_Add), adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    // The mInterstitialAd reference will be null until
                    // an ad is loaded.
                    mInterstitialAd = interstitialAd
                    Log.d(Constants.tag, "onAdLoaded")
                    mInterstitialAd!!.setFullScreenContentCallback(object :
                        FullScreenContentCallback() {
                        override fun onAdClicked() {
                            // Called when a click is recorded for an ad.
                            Log.d(Constants.tag, "Ad was clicked.")
                        }

                        override fun onAdDismissedFullScreenContent() {
                            // Called when ad is dismissed.
                            // Set the ad reference to null so you don't show the ad a second time.
                            Log.d(Constants.tag, "Ad dismissed fullscreen content.")
                            mInterstitialAd = null
                            this@MainA.runOnUiThread(Runnable {
                                val fragment =
                                    pagerSatetAdapter.getItem(binding.viewpager.getCurrentItem()) as VideosListF
                                fragment. youTubePlayer?.play()
                            })
                        }

                        override fun onAdImpression() {
                            // Called when an impression is recorded for an ad.
                            Log.d(Constants.tag, "Ad recorded an impression.")
                        }

                        override fun onAdShowedFullScreenContent() {
                            // Called when ad is shown.
                            Log.d(Constants.tag, "Ad showed fullscreen content.")
                            Handler(Looper.getMainLooper()).postDelayed({
                               this@MainA.runOnUiThread(Runnable {
                                    try {
                                        val fragment =
                                            pagerSatetAdapter.getItem(binding.viewpager.getCurrentItem()) as VideosListF
                                        fragment. youTubePlayer?.pause()
                                    } catch (e: java.lang.Exception) {
                                        Log.d(Constants.tag, "Exception: $e")
                                    }
                                })
                            }, 1000)
                        }
                    })
                    if (mInterstitialAd != null) {
                        mInterstitialAd!!.show(this@MainA)
                    }
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    // Handle the error
                    Log.d(Constants.tag, loadAdError.toString())
                    mInterstitialAd = null
                }
            })
    }


    private fun getTestingUniqueAPI() {
        val params: JSONObject = JSONObject()
        try {

        }catch (e: Exception)
        {
            Log.d(Constants.tag,"Exception: ${e}")
        }

        if (dataList.isEmpty())
        {
            binding.swiperefresh.isRefreshing=true
        }
        RetrofitRequest.JsonPostRequest(
            params.toString(),
            Singleton.getApiCall(binding.root.context).getTestingUnique(),
            object: ApiCallback {
                override fun onResponce(resp: String, isSuccess: Boolean) {

                    binding.swiperefresh.isRefreshing=false
                    try {
                        val resp= JSONObject(resp)
                        if (resp.optString("success").equals("1"))
                        {
                            dataList.clear()
                            val dataArray=resp.optJSONArray("data")

                            for (i in 0 until dataArray.length())
                            {
                                val innerObj=dataArray.optJSONObject(i)
                                var videoModel= DataParser.videoModelParse(innerObj)

                                dataList.add(videoModel)
                            }

                            for (item in dataList) {
                                pagerSatetAdapter.addFragment(VideosListF(item))
                            }
                            pagerSatetAdapter.refreshStateSet(false)
                            pagerSatetAdapter.notifyDataSetChanged()
                            if (!binding.swiperefresh.isEnabled()) {
                                binding.swiperefresh.setEnabled(false)
                            }
                        }
                        else
                        {
                            Helper.showToast("${resp.optString("message")}",binding.root.context)
                        }
                    }
                    catch (e: Exception)
                    {
                        Log.d(Constants.tag,"Exception: ${e}")
                    }


                }
            })
    }


    override fun onResume() {
        super.onResume()
        if (pagerSatetAdapter != null && pagerSatetAdapter.count > 0) {
            val fragment =
                pagerSatetAdapter.getItem(binding.viewpager.getCurrentItem()) as VideosListF
            fragment.mainMenuVisibility(true)
        }
    }
}
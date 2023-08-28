package com.example.gracetechnologiestest.activitiesfragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.example.gracetechnologiestest.R
import com.example.gracetechnologiestest.databinding.FragmentVideosListBinding
import com.example.gracetechnologiestest.datamodel.VideoDM
import com.example.gracetechnologiestest.simpleclasses.Helper
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo

class VideosListF(private val item: VideoDM) : Fragment() {

    lateinit var binding:FragmentVideosListBinding
    public var youTubePlayer: YouTubePlayer? = null
    public var isVisibleToUser: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding =DataBindingUtil.inflate<FragmentVideosListBinding>(inflater,R.layout.fragment_videos_list, container, false)

        initializePlayer()
        setupScreenData()
        return binding.root
    }

    private fun setupScreenData() {
        if (item.ex_title!=null && !(item.ex_title.isEmpty()) && !(item.ex_title.equals("null",true)))
        {
            binding.tvTitle.visibility=View.VISIBLE
            binding.tvTitle.text=item.ex_title
        }
        else
        {
            binding.tvTitle.visibility=View.GONE
            binding.tvTitle.text=item.ex_title
        }

        if (item.ex_description!=null && !(item.ex_description.isEmpty()) && !(item.ex_description.equals("null",true)))
        {
            binding.tvDescription.visibility=View.VISIBLE
            binding.tvDescription.text=item.ex_description
        }
        else
        {
            binding.tvDescription.visibility=View.GONE
        }
    }


    private fun initializePlayer() {
        binding.youtubePlayerView.initialize(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                youTubePlayer.loadOrCueVideo(this@VideosListF.lifecycle, Helper.extractYouTubeVideoId(item.ex_url), 0f)
            }
        })
    }

    public fun setPlayer(isVisibleToUser: Boolean) {
        if (youTubePlayer != null) {
            if (isVisibleToUser) {
                youTubePlayer?.play()
            } else {
                youTubePlayer?.pause()
            }
        }
    }


    override fun setMenuVisibility(visible: Boolean) {
        super.setMenuVisibility(visible)
        isVisibleToUser = visible

        Handler(Looper.getMainLooper()).postDelayed({
            if (youTubePlayer != null && visible) {
                setPlayer(isVisibleToUser)
            }
        }, 200)
    }

    fun mainMenuVisibility(isVisible: Boolean) {
        if (youTubePlayer != null && isVisible) {
            youTubePlayer?.play()
        } else if (youTubePlayer != null && !isVisible) {
            youTubePlayer?.pause()
        }
    }

    private fun releasePreviousPlayer() {
        youTubePlayer = null
    }

    override fun onDestroy() {
        releasePreviousPlayer()
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        youTubePlayer?.pause()
    }

    override fun onStop() {
        super.onStop()
        youTubePlayer?.pause()
    }

}
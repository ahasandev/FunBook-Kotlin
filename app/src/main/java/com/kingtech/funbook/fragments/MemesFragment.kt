package com.kingtech.funbook.fragments

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2

import com.kingtech.funbook.adapter.MemesAdapter
import com.kingtech.funbook.databinding.FragmentMemesBinding
import com.kingtech.funbook.model.Memes
import com.kingtech.funbook.model.MemesData


class MemesFragment : BaseFragment<FragmentMemesBinding>(FragmentMemesBinding::inflate) {

    private val exoPlayerItems = ArrayList<ExoPlayerItem>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val videos: List<Memes> = MemesData.memes

           val adapter = MemesAdapter(videos,requireContext(), object : MemesAdapter.OnVideoPreparedListener {
                override fun onVideoPrepared(exoPlayerItem:ExoPlayerItem) {
                    exoPlayerItems.add(exoPlayerItem)
                }
            })

        binding.viewPager2.adapter = adapter

        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val previousIndex = exoPlayerItems.indexOfFirst { it.exoPlayer.isPlaying }
                if (previousIndex != -1) {
                    val player = exoPlayerItems[previousIndex].exoPlayer
                    player.pause()
                    player.playWhenReady = false
                }
                val newIndex = exoPlayerItems.indexOfFirst { it.position == position }
                if (newIndex != -1) {
                    val player = exoPlayerItems[newIndex].exoPlayer
                    player.playWhenReady = true
                    player.play()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Release ExoPlayer instances and perform cleanup when the fragment's view is destroyed
        releasePlayers()
    }

    private fun releasePlayers() {
        for (exoPlayerItem in exoPlayerItems) {
            exoPlayerItem.exoPlayer.release()
        }
        exoPlayerItems.clear()
    }

    override fun onPause() {
        super.onPause()

        val index = exoPlayerItems.indexOfFirst { it.position == binding.viewPager2.currentItem }
        if (index != -1) {
            val player = exoPlayerItems[index].exoPlayer
            player.pause()
            player.playWhenReady = false
        }
    }

    override fun onResume() {
        super.onResume()

        val index = exoPlayerItems.indexOfFirst { it.position == binding.viewPager2.currentItem }
        if (index != -1) {
            val player = exoPlayerItems[index].exoPlayer
            player.playWhenReady = true
            player.play()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (exoPlayerItems.isNotEmpty()) {
            for (item in exoPlayerItems) {
                val player = item.exoPlayer
                player.stop()
                player.clearMediaItems()
            }
        }
    }





}
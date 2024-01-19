package com.kingtech.funbook.adapter

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.kingtech.funbook.MainActivity
import com.kingtech.funbook.databinding.VideoItemBinding
import com.kingtech.funbook.fragments.ExoPlayerItem
import com.kingtech.funbook.model.Memes

class MemesAdapter(val memesList : List<Memes>, var context: Context, var videoPreparedListener: OnVideoPreparedListener): RecyclerView.Adapter<MemesAdapter.MemesViewHolder>() {
    interface OnVideoPreparedListener {
        fun onVideoPrepared(exoPlayerItem: ExoPlayerItem)
    }
    class MemesViewHolder(
        val binding: VideoItemBinding,
        var context: Context,
        var videoPreparedListener: OnVideoPreparedListener
       ):RecyclerView.ViewHolder(binding.root){
        private lateinit var exoPlayer: ExoPlayer
        private lateinit var mediaSource: MediaSource

        fun setVideoPath(url: String) {

            exoPlayer = ExoPlayer.Builder(context).build()
            exoPlayer.addListener(object : Player.Listener {
                override fun onPlayerError(error: PlaybackException) {
                    super.onPlayerError(error)
                    Toast.makeText(context, "Can't play this video", Toast.LENGTH_SHORT).show()
                }

                override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                    if (playbackState == Player.STATE_BUFFERING) {
                        binding.pbLoading.visibility = View.VISIBLE
                    } else if (playbackState == Player.STATE_READY) {
                        binding.pbLoading.visibility = View.GONE
                    }
                }
            })

            binding.playerView.player = exoPlayer

            exoPlayer.seekTo(0)
            exoPlayer.repeatMode = Player.REPEAT_MODE_ONE

            val dataSourceFactory = DefaultDataSource.Factory(context)

            mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(
                MediaItem.fromUri(
                Uri.parse(url)))

            exoPlayer.setMediaSource(mediaSource)
            exoPlayer.prepare()

            if (absoluteAdapterPosition == 0) {
                exoPlayer.playWhenReady = true
                exoPlayer.play()
            }

            videoPreparedListener.onVideoPrepared(
                ExoPlayerItem(
                    exoPlayer,
                    absoluteAdapterPosition
                )
            )
        }
       }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemesViewHolder {
        return MemesViewHolder(VideoItemBinding.inflate(LayoutInflater.from(parent.context),parent,false),context,videoPreparedListener)
    }

    override fun getItemCount(): Int {
       return memesList.size
    }

    override fun onBindViewHolder(holder: MemesViewHolder, position: Int) {
      val memes = memesList[position]
        holder.binding.postTitle.text=memes.title
        holder.setVideoPath(memes.video_url)
    }


}
package com.pedrofr.guesstheyear.ui

import android.content.Context
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import javax.inject.Inject

class MediaPlayerImpl @Inject constructor() : MediaPlayer {
    private lateinit var exoPlayer: SimpleExoPlayer
    private lateinit var context: Context //todo dependency inject
    private var currentWindow: Int = 0
    private var playbackPosition: Long = 0

    private fun initializePlayer() {

        exoPlayer = SimpleExoPlayer.Builder(context).build()

    }

    override fun play(url: String) {
        val mediaItem = MediaItem.fromUri(url)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.playWhenReady = true;
        exoPlayer.seekTo(currentWindow, playbackPosition);
        exoPlayer.prepare();
    }

    override fun getPlayerImpl(context: Context): ExoPlayer {
        this.context = context
        initializePlayer()
        return exoPlayer
    }

    override fun releasePlayer() {
        exoPlayer.stop()
        exoPlayer.release()
    }
}
package good.damn.hslstreamingandroid

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.appcompat.app.AppCompatActivity
import good.damn.hlsviewplayer.HLViewPlayer
import good.damn.hslstreamingandroid.extensions.toLocalPathUrl
import good.damn.hslstreamingandroid.http.HLClient
import good.damn.hslstreamingandroid.http.listeners.HLListenerM3U8OnGetPlaylist
import good.damn.hslstreamingandroid.http.listeners.HLListenerM3U8OnGetSequences
import good.damn.hslstreamingandroid.model.HLModelStreaming
import good.damn.hslstreamingandroid.model.m3u8.HLModelM3U8Playlist
import good.damn.hslstreamingandroid.model.m3u8.sequence.HLModelM3U8Sequences
import good.damn.hslstreamingandroid.stream.HLListenerOnGetFrame
import good.damn.hslstreamingandroid.stream.HLStream
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.net.URL

class HLActivityMain
: AppCompatActivity(),
HLListenerM3U8OnGetPlaylist,
HLListenerM3U8OnGetSequences, HLListenerOnGetFrame {

    companion object {
        private const val TAG = "HLActivityMain"
    }

    private var mViewBitmap: HLViewPlayer? = null

    private val mClient = HLClient(
        CoroutineScope(
            Dispatchers.IO
        )
    ).apply {
        onGetPlaylist = this@HLActivityMain
        onGetSequences = this@HLActivityMain
    }

    private val mStream = HLStream(
        scopeStream = CoroutineScope(
            Dispatchers.IO
        ),
        scopeFrame = CoroutineScope(
            Dispatchers.Default
        )
    ).apply {
        onGetFrame = this@HLActivityMain
    }

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(
            savedInstanceState
        )

        val url = "https://flipfit-cdn.akamaized.net/flip_hls/664d87dfe8e47500199ee49e-dbd56b/video_h1.m3u8"

        mClient.apply {
            localPath = url.toLocalPathUrl()
            this@apply.url = URL(url)
            getPlaylistAsync()
        }

        mViewBitmap = HLViewPlayer(
            this
        ).apply {
            setContentView(this)
        }
    }

    @WorkerThread
    override fun onGetM3U8Playlist(
        playlist: HLModelM3U8Playlist
    ) {
        val play = playlist.playlist
            .last()

        mClient.apply {
            val u = playlist.url + play.url
            localPath = u.toLocalPathUrl()
            url = URL(u)
            getSequencesAsync(
                play
            )
        }
    }

    @WorkerThread
    override fun onGetM3U8Sequences(
        streamConfig: HLModelStreaming,
        seq: HLModelM3U8Sequences
    ) {
        Log.d(TAG, "onGetM3U8Sequences: streamOptions: $streamConfig")
        Log.d(TAG, "onGetM3U8Sequences: $seq")

        mStream.apply {
            stream(seq)
            start(streamConfig)
        }
    }

    @WorkerThread
    override fun onGetFrame(
        b: Bitmap
    ) {
        HLApp.ui {
            mViewBitmap?.apply {
                bitmap = b
            }
        }
    }

}
package good.damn.hslstreamingandroid

import android.media.MediaMetadataRetriever
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import good.damn.hslstreamingandroid.http.HLClient
import good.damn.hslstreamingandroid.http.listeners.HLListenerM3U8OnGetPlaylist
import good.damn.hslstreamingandroid.http.listeners.HLListenerM3U8OnGetSequences
import good.damn.hslstreamingandroid.model.HLModelStreaming
import good.damn.hslstreamingandroid.model.m3u8.HLModelM3U8Playlist
import good.damn.hslstreamingandroid.model.m3u8.sequence.HLModelM3U8Sequences
import good.damn.hslstreamingandroid.stream.HLStream

class HLActivityMain
: AppCompatActivity(),
HLListenerM3U8OnGetPlaylist,
HLListenerM3U8OnGetSequences {

    companion object {
        private const val TAG = "HLActivityMain"
    }

    private var mImageView: ImageView? = null

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(
            savedInstanceState
        )

        HLClient(
            "https://flipfit-cdn.akamaized.net/flip_hls/664d87dfe8e47500199ee49e-dbd56b/video_h1.m3u8"
        ).apply {
            onGetPlaylist = this@HLActivityMain
            getPlaylistAsync()
        }

        mImageView = ImageView(
            this
        ).apply {
            setContentView(this)
        }
    }

    override fun onGetM3U8Playlist(
        playlist: HLModelM3U8Playlist
    ) {
        val play = playlist.playlist[0]
        HLClient(
            playlist.url + play.url
        ).apply {
            
            onGetSequences = this@HLActivityMain
            getSequencesAsync(
                play
            )
        }
    }

    override fun onGetM3U8Sequences(
        streamConfig: HLModelStreaming,
        sequences: HLModelM3U8Sequences
    ) {
        Log.d(TAG, "onGetM3U8Sequences: streamOptions: $streamConfig")
        Log.d(TAG, "onGetM3U8Sequences: $sequences")

        HLStream(
            sequences
        ).apply {
            onGetFrame = {
                HLApp.ui {
                    mImageView?.setImageBitmap(
                        it
                    )
                }
            }
            start()
        }
    }

}
package good.damn.hslstreamingandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import good.damn.hslstreamingandroid.http.HLClient
import good.damn.hslstreamingandroid.http.listeners.HLListenerM3U8OnGetPlaylist
import good.damn.hslstreamingandroid.model.m3u8.HLModelM3U8Playlist

class HLActivityMainOnGetPlaylist
: AppCompatActivity(),
HLListenerM3U8OnGetPlaylist {

    companion object {
        private const val TAG = "HLActivityMain"
    }

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(
            savedInstanceState
        )

        HLClient(
            "https://flipfit-cdn.akamaized.net/flip_hls/664d87dfe8e47500199ee49e-dbd56b/video_h1.m3u8"
        ).apply {
            onGetContentStreaming = this@HLActivityMainOnGetPlaylist
            getContentAsync()
        }

    }

    override fun onGetM3U8Playlist(
        playlist: HLModelM3U8Playlist
    ) {
        val play = playlist.playlist[0]
        HLClient(
            playlist.url + play.url
        ).apply {
            getContentAsync()
        }
    }

}
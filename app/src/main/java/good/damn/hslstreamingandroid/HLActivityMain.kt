package good.damn.hslstreamingandroid

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import good.damn.hslstreamingandroid.http.HLClient
import good.damn.hslstreamingandroid.http.listeners.HLListenerOnGetContentM3U8
import good.damn.hslstreamingandroid.model.HLModelStreaming

class HLActivityMain
: AppCompatActivity(),
HLListenerOnGetContentM3U8 {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(
            savedInstanceState
        )

        HLClient(
            "https://flipfit-cdn.akamaized.net/flip_hls/664d87dfe8e47500199ee49e-dbd56b/video_h1.m3u8"
        ).apply {
            onGetContentStreaming = this@HLActivityMain
            getContentAsync()
        }

    }

    override fun onGetM3U8(
        availableStreamers: Array<HLModelStreaming>
    ) {

    }

}
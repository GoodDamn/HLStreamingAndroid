package good.damn.hslstreamingandroid.http

import android.util.Log
import good.damn.hslstreamingandroid.HLApp
import good.damn.hslstreamingandroid.decoder.HLDecoderM3U8
import good.damn.hslstreamingandroid.extensions.readAll
import good.damn.hslstreamingandroid.extensions.toLocalPathUrl
import good.damn.hslstreamingandroid.http.listeners.HLListenerM3U8OnGetPlaylist
import good.damn.hslstreamingandroid.model.m3u8.HLModelM3U8Playlist
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL
import java.net.URLConnection
import java.nio.charset.StandardCharsets

class HLClient(
    url: String
) {

    companion object {
        private const val TAG = "HLClient"
    }

    private val mUrl = URL(url)
    private val mLocalPath = url.toLocalPathUrl()
    private var mUrlConnection: URLConnection? = null

    var onGetContentStreaming: HLListenerM3U8OnGetPlaylist? = null

    private val mScope = CoroutineScope(
        Dispatchers.IO
    )


    fun getContentAsync() = mScope.launch {
        mUrlConnection = mUrl.openConnection().apply {
            connect()
        }

        val connection = mUrlConnection
            ?: return@launch

        val data = connection.getInputStream()
            .readAll(8192)

        val st = String(
            data,
            StandardCharsets.UTF_8
        )

        Log.d(TAG, "getContentAsync: $st")
        val decoder = HLDecoderM3U8(st)
        val playlist = decoder.decode()
        HLApp.ui {
            onGetContentStreaming?.onGetM3U8Playlist(
                HLModelM3U8Playlist(
                    mLocalPath,
                    playlist
                )
            )
        }
    }

}
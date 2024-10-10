package good.damn.hslstreamingandroid.http

import android.util.Log
import good.damn.hslstreamingandroid.HLApp
import good.damn.hslstreamingandroid.decoder.HLDecoderM3U8
import good.damn.hslstreamingandroid.extensions.readAll
import good.damn.hslstreamingandroid.http.listeners.HLListenerOnGetContentM3U8
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
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
    private var mUrlConnection: URLConnection? = null

    var onGetContentStreaming: HLListenerOnGetContentM3U8? = null

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
            onGetContentStreaming?.onGetM3U8(
                playlist
            )
        }
    }

}
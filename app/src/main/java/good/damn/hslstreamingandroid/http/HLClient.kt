package good.damn.hslstreamingandroid.http

import android.util.Log
import good.damn.hslstreamingandroid.HLApp
import good.damn.hslstreamingandroid.decoder.HLDecoderM3U8
import good.damn.hslstreamingandroid.extensions.readAll
import good.damn.hslstreamingandroid.extensions.toLocalPathUrl
import good.damn.hslstreamingandroid.http.listeners.HLListenerM3U8OnGetPlaylist
import good.damn.hslstreamingandroid.http.listeners.HLListenerM3U8OnGetSequences
import good.damn.hslstreamingandroid.model.HLModelStreaming
import good.damn.hslstreamingandroid.model.m3u8.HLModelM3U8Playlist
import good.damn.hslstreamingandroid.model.m3u8.sequence.HLModelM3U8Sequences
import good.damn.hslstreamingandroid.model.m3u8.sequence.HLModelTSSequence
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL
import java.net.URLConnection
import java.nio.charset.StandardCharsets

class HLClient(
    private val mScope: CoroutineScope
) {

    companion object {
        private const val TAG = "HLClient"
    }

    var url: URL? = null
    var localPath: String? = null

    private var mUrlConnection: URLConnection? = null

    var onGetPlaylist: HLListenerM3U8OnGetPlaylist? = null
    var onGetSequences: HLListenerM3U8OnGetSequences? = null

    fun getPlaylistAsync() = mScope.launch {
        val st = getInputData()
            ?: return@launch
        
        val decoder = HLDecoderM3U8(st)
        val playlist = decoder.decode<HLModelStreaming>()

        localPath?.let {
            onGetPlaylist?.onGetM3U8Playlist(
                HLModelM3U8Playlist(
                    it,
                    playlist
                )
            )
        }
    }

    fun getSequencesAsync(
        streamConfig: HLModelStreaming
    ) = mScope.launch {
        val st = getInputData()
            ?: return@launch

        val decoder = HLDecoderM3U8(st)
        val sequences = decoder.decode<HLModelTSSequence>()

        localPath?.let {
            onGetSequences?.onGetM3U8Sequences(
                streamConfig,
                HLModelM3U8Sequences(
                    it,
                    sequences
                )
            )
        }
    }

    private fun getInputData(): String? {
        mUrlConnection = url?.openConnection()?.apply {
            connect()
        }

        val data = mUrlConnection
            ?.getInputStream()
            ?.readAll(8192)
            ?: return null

        val content = String(
            data,
            StandardCharsets.UTF_8
        )

        Log.d(TAG, "getInputData: $content")
        
        return content
    }
}
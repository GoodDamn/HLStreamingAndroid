package good.damn.hslstreamingandroid.stream

import android.media.MediaCodec
import android.util.Log
import good.damn.hslstreamingandroid.extensions.readAll
import good.damn.hslstreamingandroid.model.m3u8.sequence.HLModelM3U8Sequences
import good.damn.hslstreamingandroid.model.m3u8.sequence.HLModelTSSequence
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL
import java.nio.charset.StandardCharsets

class HLStream(
    private val mSequences: HLModelM3U8Sequences
) {

    companion object {
        private const val TAG = "HLStream"
    }

    private val mScope = CoroutineScope(
        Dispatchers.IO
    )

    fun start() = mScope.launch {
        mSequences.sequences.forEach {
            val data = URL(
                mSequences.url + it.url
            ).openStream().readAll(
                8192
            )
            Log.d(
                TAG,
                "start: ${it.url} DATA_SIZE: ${data.size} data: ${
                    String(
                        data,
                        StandardCharsets.UTF_8
                    ).substring(0, 200)
                }"
            )
        }
    }

}
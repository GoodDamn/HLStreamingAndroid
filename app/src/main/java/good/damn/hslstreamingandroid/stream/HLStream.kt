package good.damn.hslstreamingandroid.stream

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaCodec
import android.media.MediaMetadataRetriever
import android.util.Log
import good.damn.hslstreamingandroid.extensions.readAll
import good.damn.hslstreamingandroid.extensions.write
import good.damn.hslstreamingandroid.files.HLFile
import good.damn.hslstreamingandroid.model.m3u8.sequence.HLModelM3U8Sequences
import good.damn.hslstreamingandroid.model.m3u8.sequence.HLModelTSSequence
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.net.URL
import java.nio.charset.StandardCharsets

class HLStream(
    private val mSequences: HLModelM3U8Sequences
) {

    companion object {
        private const val TAG = "HLStream"
    }

    var onGetFrame: ((Bitmap) -> Unit)? = null

    private val mScope = CoroutineScope(
        Dispatchers.IO
    )

    fun start() = mScope.launch {
        val media = MediaMetadataRetriever()

        mSequences.sequences.forEach {
            val data = URL(
                mSequences.url + it.url
            ).openStream().readAll(
                8192
            )

            val file = HLFile(
                it.url
            ).apply {
                createFile()
                write(data)
            }

            media.setDataSource(
                file.path
            )

            var prevTime = System.currentTimeMillis()
            var duration = 0L

            while (true) {
                val currentTime = System.currentTimeMillis()
                duration += currentTime - prevTime
                prevTime = currentTime
                Log.d(TAG, "start: TIMEEEEE: $currentTime $prevTime $duration")
                if (duration > 2000) {
                    break
                }

                val bitmap = media.getFrameAtTime(
                    duration
                )

                if (bitmap == null) {
                    break
                }

                onGetFrame?.invoke(
                    bitmap
                )
            }

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
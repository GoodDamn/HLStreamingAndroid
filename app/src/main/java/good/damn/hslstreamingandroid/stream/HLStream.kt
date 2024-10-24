package good.damn.hslstreamingandroid.stream

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaCodec
import android.media.MediaMetadataRetriever
import android.util.Log
import good.damn.hslstreamingandroid.extensions.readAll
import good.damn.hslstreamingandroid.extensions.write
import good.damn.hslstreamingandroid.files.HLFile
import good.damn.hslstreamingandroid.model.HLModelStreaming
import good.damn.hslstreamingandroid.model.m3u8.sequence.HLModelM3U8Sequences
import good.damn.hslstreamingandroid.model.m3u8.sequence.HLModelTSSequence
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import wseemann.media.FFmpegMediaMetadataRetriever
import java.io.File
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.concurrent.ConcurrentLinkedDeque
import java.util.concurrent.ConcurrentLinkedQueue

class HLStream {

    companion object {
        private const val TAG = "HLStream"
    }

    var onGetFrame: HLListenerOnGetFrame? = null

    private val mSequencer = ConcurrentLinkedDeque<
        HLModelTSSequence
    >()

    private var mIsReachEnd = false

    var sequences: HLModelM3U8Sequences? = null

    private val mScope = CoroutineScope(
        Dispatchers.IO
    )

    fun stream() = mScope.run {
        mIsReachEnd = false

        val seq = sequences
            ?: return@run

        launch {
            seq.sequences.forEach {
                val data = URL(
                    seq.url + it.url
                ).openStream().readAll(
                    8192
                )

                HLFile(
                    it.url
                ).apply {
                    createFile()
                    write(data)
                }

                mSequencer.add(it)

                Log.d(TAG, "stream: ${it.url}")
            }
            mIsReachEnd = true
        }
    }

    fun start() = mScope.launch {
        while (
            !mIsReachEnd || mSequencer.isNotEmpty()
        ) {
            if (mSequencer.isEmpty()) {
                continue
            }

            val sequence = mSequencer.removeFirst()
            val media = FFmpegMediaMetadataRetriever()
            
            HLFile(
                sequence.url
            ).apply {
                media.setDataSource(
                    absolutePath
                )
            }

            var bitmap: Bitmap?

            var currentTime: Long
            var prevTime = System.currentTimeMillis()
            var seek = 0L

            val duration = sequence.durationSec * 1000L

            while (true) {
                if (seek > duration) {
                    Log.d(TAG, "start: SEQUENCE_END")
                    break
                }

                bitmap = media.getFrameAtTime(
                    seek,
                    FFmpegMediaMetadataRetriever.OPTION_CLOSEST_SYNC
                ) ?: break

                onGetFrame?.onGetFrame(
                    bitmap
                )

                currentTime = System.currentTimeMillis()
                seek += (currentTime - prevTime) * 500L
                prevTime = currentTime
            }
        }
    }

}
package good.damn.hslstreamingandroid.decoder

import android.util.Log
import good.damn.hslstreamingandroid.decoder.tags.HLTagStreamInfo
import good.damn.hslstreamingandroid.decoder.tags.HLTagTsInfo
import good.damn.hslstreamingandroid.model.HLModelStreaming
import java.util.LinkedList
import kotlin.math.log

class HLDecoderM3U8(
    private val mInputData: String
) {

    companion object {
        private const val TAG = "HLDecoderM3U8"

        private val availableTags = hashMapOf(
            "EXT-X-STREAM-INF" to HLTagStreamInfo(),
            "EXTINF" to HLTagTsInfo()
        )
    }

    fun <T: Any> decode(): List<T> {
        val lines = mInputData.split(
            "#".toRegex()
        )

        val list = LinkedList<T>()

        for (line in lines) {
            val tt = line.indexOf(":")
            if (tt == -1) {
                continue
            }

            val tag = line.substring(0,tt)
            val params = line.substring(tt+1)

            Log.d(TAG, "decode: $tag $params")

            val model = availableTags[tag]?.getInfoTag(
                line.substring(tt+1)
            ) as? T ?: continue

            list.add(model)
        }

        return list
    }
}
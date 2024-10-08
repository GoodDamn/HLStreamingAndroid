package good.damn.hslstreamingandroid.decoder

import android.util.Log
import good.damn.hslstreamingandroid.decoder.tags.HLTagStreamInfo
import good.damn.hslstreamingandroid.model.HLModelStreaming
import java.util.LinkedList
import kotlin.math.log

class HLDecoderM3U8(
    private val mInputData: String
) {

    companion object {
        private val availableTags = hashMapOf(
            "EXT-X-STREAM-INF" to HLTagStreamInfo()
        )
    }

    fun decode(): List<HLModelStreaming> {
        val lines = mInputData.split(
            "#".toRegex()
        )

        val list = LinkedList<HLModelStreaming>()

        for (line in lines) {
            val tt = line.indexOf(":")
            if (tt == -1) {
                continue
            }

            val tag = line.substring(0,tt)

            availableTags[tag]?.getInfoTag(
                line.substring(tt+1)
            )
        }

        return list
    }
}
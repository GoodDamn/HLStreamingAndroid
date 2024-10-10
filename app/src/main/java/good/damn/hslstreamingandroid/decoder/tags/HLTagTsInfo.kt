package good.damn.hslstreamingandroid.decoder.tags

import android.util.Log
import good.damn.hslstreamingandroid.extensions.splitChar
import good.damn.hslstreamingandroid.model.m3u8.sequence.HLModelTSSequence

class HLTagTsInfo
: HLTaggable<HLModelTSSequence> {

    companion object {
        private const val TAG = "HLTagTsInfo"
    }

    override fun getInfoTag(
        line: String
    ): HLModelTSSequence {
        Log.d(TAG, "getInfoTag: $line")

        val split = line.split(",\n")

        return HLModelTSSequence(
            split[1],
            split[0].toFloat()
        )
    }
}
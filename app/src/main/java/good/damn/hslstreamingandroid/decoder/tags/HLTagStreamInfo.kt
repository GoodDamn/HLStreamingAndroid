package good.damn.hslstreamingandroid.decoder.tags

import android.util.Log
import android.util.Size
import good.damn.hslstreamingandroid.extensions.getProperties
import good.damn.hslstreamingandroid.extensions.toSize
import good.damn.hslstreamingandroid.model.HLModelStreaming

class HLTagStreamInfo
: HLTaggable<HLModelStreaming> {

    companion object {
        private const val TAG = "HLTagStreamInfo"
    }

    override fun getInfoTag(
        line: String
    ): HLModelStreaming {
        Log.d(TAG, "getInfoTag: $line")
        val properties = line.getProperties(0x2c)
        Log.d(TAG, "getInfoTag: PROPS: $properties")
        
        val bandwidth = properties[
            "BANDWIDTH"
        ]?.toInt() ?: 0
        
        val resolution = properties[
            "RESOLUTION"
        ]?.toSize() ?: Size(0,0)

        return HLModelStreaming(
            bandwidth,
            resolution,
            ""
        )
    }
}
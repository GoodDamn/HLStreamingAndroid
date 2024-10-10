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

        private const val KEY_BANDWIDTH = "BANDWIDTH"
        private const val KEY_RESOLUTION = "RESOLUTION"
        private const val KEY_FRAME_RATE = "FRAME-RATE"
        private const val KEY_URL = "URL"
    }

    override fun getInfoTag(
        line: String
    ): HLModelStreaming {
        val properties = line.getProperties(
            0x2c,
            "="
        )
        Log.d(TAG, "getInfoTag: PROPS: $properties")
        
        val bandwidth = properties[
            KEY_BANDWIDTH
        ]?.toInt() ?: 0
        
        val resolution = properties[
            KEY_RESOLUTION
        ]?.toSize() ?: Size(0,0)

        val frameRate = properties[
            KEY_RESOLUTION
        ]?.toFloatOrNull() ?: 30.0f

        val url = properties[
            KEY_URL
        ]

        return HLModelStreaming(
            bandwidth,
            resolution,
            frameRate,
            url
        )
    }
}
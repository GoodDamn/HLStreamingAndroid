package good.damn.hslstreamingandroid.model

import android.util.Size

data class HLModelStreaming(
    val bandwidth: Int,
    val resolution: Size,
    val url: String
)
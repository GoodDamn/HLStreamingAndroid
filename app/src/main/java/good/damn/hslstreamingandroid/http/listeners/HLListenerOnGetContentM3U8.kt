package good.damn.hslstreamingandroid.http.listeners

import good.damn.hslstreamingandroid.model.HLModelStreaming

interface HLListenerOnGetContentM3U8 {
    fun onGetM3U8(
        availableStreamers: List<HLModelStreaming>
    )
}
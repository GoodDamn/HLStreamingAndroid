package good.damn.hslstreamingandroid.http.listeners

import good.damn.hslstreamingandroid.model.HLModelStreaming
import good.damn.hslstreamingandroid.model.m3u8.sequence.HLModelM3U8Sequences

interface HLListenerM3U8OnGetSequences {
    fun onGetM3U8Sequences(
        streamConfig: HLModelStreaming,
        sequences: HLModelM3U8Sequences
    )
}
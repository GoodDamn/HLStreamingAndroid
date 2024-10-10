package good.damn.hslstreamingandroid.model.m3u8

import good.damn.hslstreamingandroid.model.HLModelStreaming

data class HLModelM3U8Playlist(
    val url: String,
    val playlist: List<HLModelStreaming>
)
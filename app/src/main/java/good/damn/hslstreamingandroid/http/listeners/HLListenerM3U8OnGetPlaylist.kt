package good.damn.hslstreamingandroid.http.listeners

import good.damn.hslstreamingandroid.model.m3u8.HLModelM3U8Playlist

interface HLListenerM3U8OnGetPlaylist {
    fun onGetM3U8Playlist(
        playlist: HLModelM3U8Playlist
    )
}
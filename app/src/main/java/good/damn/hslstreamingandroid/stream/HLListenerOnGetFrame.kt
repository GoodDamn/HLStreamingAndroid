package good.damn.hslstreamingandroid.stream

import android.graphics.Bitmap

interface HLListenerOnGetFrame {

    fun onGetFrame(
        bitmap: Bitmap
    )
}
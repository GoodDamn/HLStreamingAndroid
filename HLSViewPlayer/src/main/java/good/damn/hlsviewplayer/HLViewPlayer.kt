package good.damn.hlsviewplayer

import android.content.Context
import android.graphics.Bitmap
import android.opengl.GLSurfaceView
import android.view.SurfaceView
import good.damn.hlsviewplayer.gl.renderers.HLRendererFrame

class HLViewPlayer(
    context: Context
): GLSurfaceView(
    context
) {

    var bitmap: Bitmap?
        get() = mRenderer.bitmap
        set(v) {
            mRenderer.bitmap = v
            requestRender()
        }

    private val mRenderer = HLRendererFrame()

    init {
        setEGLContextClientVersion(2)

        setRenderer(
            mRenderer
        )
        renderMode = RENDERMODE_WHEN_DIRTY

    }
}
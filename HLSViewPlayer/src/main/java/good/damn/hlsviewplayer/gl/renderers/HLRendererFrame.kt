package good.damn.hlsviewplayer.gl.renderers

import android.graphics.Bitmap
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import android.opengl.GLES20.*
import good.damn.hlsviewplayer.gl.quad.HLRenderQuad

class HLRendererFrame
: GLSurfaceView.Renderer {

    var bitmap: Bitmap? = null

    private lateinit var mQuad: HLRenderQuad

    private var mProgram = 0

    override fun onSurfaceCreated(
        gl: GL10?,
        config: EGLConfig?
    ) {

        mProgram = glCreateProgram()
        glLinkProgram(
            mProgram
        )

        mQuad = HLRenderQuad(
            mProgram
        )

    }

    override fun onSurfaceChanged(
        gl: GL10?,
        width: Int,
        height: Int
    ) {

    }

    override fun onDrawFrame(
        gl: GL10?
    ) {
        glClear(
            GL_COLOR_BUFFER_BIT or
            GL_DEPTH_BUFFER_BIT
        )

        glClearColor(
            1.0f,
            0.0f,
            0.0f,
            1.0f
        )


    }


}
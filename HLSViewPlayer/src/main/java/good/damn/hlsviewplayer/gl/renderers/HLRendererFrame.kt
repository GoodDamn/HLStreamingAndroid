package good.damn.hlsviewplayer.gl.renderers

import android.graphics.Bitmap
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import android.opengl.GLES20.*
import good.damn.hlsviewplayer.gl.quad.HLRenderQuad
import good.damn.hlsviewplayer.gl.textures.HLRenderTexture

class HLRendererFrame
: GLSurfaceView.Renderer {

    var bitmap: Bitmap?
        get() = mTexture.bitmap
        set(v) {
            mTexture.bitmap = v
        }

    private lateinit var mQuad: HLRenderQuad
    private lateinit var mTexture: HLRenderTexture

    private var mProgram = 0

    override fun onSurfaceCreated(
        gl: GL10?,
        config: EGLConfig?
    ) {
        mProgram = glCreateProgram()

        mQuad = HLRenderQuad(
            mProgram
        )

        mTexture = HLRenderTexture(
            mProgram
        )

        glLinkProgram(
            mProgram
        )

        glUseProgram(
            mProgram
        )
    }

    override fun onSurfaceChanged(
        gl: GL10?,
        width: Int,
        height: Int
    ) {
        mQuad.layout(
            width,
            height,
            mProgram
        )

        mTexture.layout(
            width,
            height,
            mProgram
        )
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

        mTexture.draw(
            mProgram
        )

        mQuad.draw(
            mProgram
        )

    }


}
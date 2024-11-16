package good.damn.hlsviewplayer.gl.textures

import android.graphics.Bitmap
import android.opengl.GLUtils
import good.damn.hlsviewplayer.gl.interfaces.HLDrawable
import good.damn.hlsviewplayer.gl.interfaces.HLLayoutable
import good.damn.hlsviewplayer.gl.GL.*
import good.damn.hlsviewplayer.utils.gl.HLUtilsGL

class HLRenderTexture(
    program: Int
): HLLayoutable,
HLDrawable {

    var bitmap: Bitmap? = null

    var width = 0f
    var height = 0f

    private var mUniTexture = 0
    private var mUniResolution = 0
    private var mTextures = intArrayOf(1)

    init {
        glAttachShader(
            program,
            HLUtilsGL.loadShader(
                GL_FRAGMENT_SHADER,
                "precision mediump float;" +
                    "uniform vec2 u_res;" +
                    "uniform sampler2D u_tex;" +
                    "void main () {" +
                        "vec2 n = vec2(" +
                            "gl_FragCoord.x / u_res.x," +
                            "(u_res.y - gl_FragCoord.y) / u_res.y" +
                        ");" +
                        "gl_FragColor = texture2D(" +
                            "u_tex, n" +
                        ");" +
                    "}"
            )
        )

        glGenTextures(
            1,
            mTextures,
            0
        )
    }

    override fun layout(
        width: Int,
        height: Int,
        program: Int
    ) {
        this.width = width.toFloat()
        this.height = height.toFloat()

        mUniTexture = glGetUniformLocation(
            program,
            "u_tex"
        )

        mUniResolution = glGetUniformLocation(
            program,
            "u_res"
        )

        glBindTexture(
            GL_TEXTURE_2D,
            mTextures[0]
        )

        glTexParameteri(
            GL_TEXTURE_2D,
            GL_TEXTURE_MAG_FILTER,
            GL_LINEAR
        )

        glTexParameteri(
            GL_TEXTURE_2D,
            GL_TEXTURE_MIN_FILTER,
            GL_LINEAR
        )

        glTexImage2D(
            GL_TEXTURE_2D,
            0,
            GL_RGB,
            width,
            height,
            0,
            GL_RGB,
            GL_UNSIGNED_BYTE,
            null
        )

        glBindTexture(
            GL_TEXTURE_2D,
            0
        )
    }

    override fun draw(
        program: Int
    ) {
        bitmap?.apply {
            GLUtils.texImage2D(
                GL_TEXTURE_2D,
                0,
                this,
                0
            )
        }

        glActiveTexture(
            GL_TEXTURE0
        )

        glBindTexture(
            GL_TEXTURE_2D,
            mTextures[0]
        )

        glUniform1i(
            mUniTexture,
            0
        )

        glUniform2f(
            mUniResolution,
            width,
            height
        )
    }
}
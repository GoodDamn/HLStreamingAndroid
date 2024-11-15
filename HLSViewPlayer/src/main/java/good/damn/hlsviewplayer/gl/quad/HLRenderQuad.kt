package good.damn.hlsviewplayer.gl.quad

import good.damn.hlsviewplayer.extensions.createFloatBuffer
import good.damn.hlsviewplayer.extensions.createIndicesBuffer
import good.damn.hlsviewplayer.gl.GL.*
import good.damn.hlsviewplayer.gl.interfaces.HLDrawable
import good.damn.hlsviewplayer.gl.interfaces.HLLayoutable
import good.damn.hlsviewplayer.utils.gl.HLUtilsGL

class HLRenderQuad(
    program: Int
): HLDrawable,
    HLLayoutable {

    companion object {
        private const val STRIDE = 8
        private const val SIZE = 2
    }

    private val mIndices = byteArrayOf(
        0, 1, 2,
        0, 2, 3
    ).createIndicesBuffer()

    private val mQuadCoords = floatArrayOf(
        -1.0f, 1.0f,  // top left
        -1.0f, -1.0f, // bottom left
        1.0f, -1.0f,  // bottom right
        1.0f, 1.0f,   // top right
    ).createFloatBuffer()

    private var mAttrPosition = 0
    private var mUniTexture = 0
    private var mUniResolution = 0

    init {
        val vertexShader = HLUtilsGL.loadShader(
            GL_VERTEX_SHADER,
            "attribute vec4 position;" +
                    "void main() {" +
                    "gl_Position = position;" +
                    "}"
        )

        glAttachShader(
            program,
            vertexShader
        )

        mAttrPosition = glGetAttribLocation(
            program,
            "position"
        )

        mUniTexture = glGetUniformLocation(
            program,
            "u_tex"
        )

        mUniResolution = glGetUniformLocation(
            program,
            "u_res"
        )

    }


    override fun layout(
        width: Int,
        height: Int,
        program: Int
    ) {

    }

    override fun draw(
        program: Int
    ) {
        glUseProgram(
            program
        )

        glEnableVertexAttribArray(
            mAttrPosition
        )

        glVertexAttribPointer(
            mAttrPosition,
            SIZE,
            GL_FLOAT,
            false,
            STRIDE,
            mQuadCoords
        )

        glDrawElements(
            GL_TRIANGLES,
            mIndices.capacity(),
            GL_UNSIGNED_BYTE,
            mIndices
        )

        glDisableVertexAttribArray(
            mAttrPosition
        )
    }

}
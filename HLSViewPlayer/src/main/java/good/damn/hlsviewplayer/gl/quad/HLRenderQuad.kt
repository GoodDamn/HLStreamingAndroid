package good.damn.hlsviewplayer.gl.quad

import android.content.Context
import good.damn.hlsviewplayer.R
import good.damn.hlsviewplayer.extensions.createFloatBuffer
import good.damn.hlsviewplayer.extensions.createIndicesBuffer
import good.damn.hlsviewplayer.extensions.rawText
import good.damn.hlsviewplayer.gl.GL.*
import good.damn.hlsviewplayer.gl.interfaces.HLDrawable
import good.damn.hlsviewplayer.gl.interfaces.HLLayoutable
import good.damn.hlsviewplayer.utils.gl.HLUtilsGL

class HLRenderQuad(
    program: Int,
    context: Context
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

    init {

        val vertexShader = HLUtilsGL.loadShader(
            GL_VERTEX_SHADER,
            context.rawText(
                R.raw.vertex
            )
        )

        glAttachShader(
            program,
            vertexShader
        )

    }


    override fun layout(
        width: Int,
        height: Int,
        program: Int
    ) {
        mAttrPosition = glGetAttribLocation(
            program,
            "position"
        )
    }

    override fun draw(
        program: Int
    ) {

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
package good.damn.hslstreamingandroid.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import android.view.View
import kotlin.math.log

class SKViewBitmap(
    context: Context
): View(
    context
) {

    companion object {
        private const val TAG = "SKViewBitmap"
    }

    var bitmap: Bitmap? = null

    private val mPaint = Paint()

    override fun onDraw(
        canvas: Canvas
    ) {
        super.onDraw(
            canvas
        )

        bitmap?.apply {
            canvas.drawBitmap(
                this,
                0f,
                0f,
                mPaint
            )
        }


        invalidate()
    }

}
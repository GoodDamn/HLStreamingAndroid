package good.damn.hslstreamingandroid.extensions

import android.graphics.Bitmap
import good.damn.hslstreamingandroid.files.HLFile
import java.io.File
import java.io.FileOutputStream

fun Bitmap.write(
    file: HLFile
) {
    file.createFile()
    val fos = FileOutputStream(
        file
    )
    compress(
        Bitmap.CompressFormat.JPEG,
        95,
        fos
    )
    fos.close()
}
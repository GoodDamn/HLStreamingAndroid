package good.damn.hslstreamingandroid.files

import android.os.Environment
import good.damn.hslstreamingandroid.HLApp
import good.damn.hslstreamingandroid.extensions.readAll
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class HLFile(
    fileName: String
): File(
    HLApp.appDir,
    fileName
) {

    fun createFile() = !exists()
       && createNewFile()

    fun write(
        data: ByteArray
    ) = FileOutputStream(
        this
    ).run {
        write(data)
        close()
    }

    fun readData() = FileInputStream(
        this
    ).readAll(
        8192
    )

}
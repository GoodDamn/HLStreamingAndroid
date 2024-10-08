package good.damn.hslstreamingandroid.extensions

import java.io.ByteArrayOutputStream
import java.io.InputStream

fun InputStream.readAll(
    bufferSize: Int
): ByteArray {
    val buffer = ByteArray(
        bufferSize
    )
    val baos = ByteArrayOutputStream()

    var n: Int
    while (true) {
        n = read(buffer)

        if (n == -1) {
            break
        }

        baos.write(
            buffer,
            0,
            n
        )
    }
    close()
    val data = baos.toByteArray()
    baos.close()

    return data
}
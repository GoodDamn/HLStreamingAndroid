package good.damn.hslstreamingandroid.extensions

import android.util.Log
import android.util.Size
import java.nio.charset.StandardCharsets
import java.util.LinkedList

fun String.toSize(): Size? {
    val xId = indexOf("x")
    if (xId == -1) {
        return null
    }

    val width = substring(
        0, xId
    ).toInt()

    val height = substring(
        xId+1
    ).toInt()

    return Size(
        width,
        height
    )
}

fun String.splitChar(
    splitChar: Int
): List<String> {
    val sepList = LinkedList<Byte>()
    val list = LinkedList<String>()

    var isQuote = false

    chars().forEach {
        if (it == 0x22) {
            isQuote = !isQuote
        } else if (it == splitChar && !isQuote) {
            val bytes = sepList.toByteArray()
            val str = String(
                bytes,
                StandardCharsets.UTF_8
            )

            list.add(str)
            sepList.clear()
            return@forEach
        }

        sepList.add(
            it.toByte()
        )
    }

    if (sepList.isNotEmpty()) {
        val bytes = sepList.toByteArray()
        val str = String(
            bytes,
            StandardCharsets.UTF_8
        )

        list.add(str)
    }

    return list
}

fun String.getProperties(
    splitChar: Int
) = HashMap<String,String>().apply {
    val split = splitChar(splitChar)
    Log.d("String", "getProperties: $split")
//    split(
//        ",".toRegex()
//    ).forEach {
//        val eqId = it.indexOf("=")
//        if (eqId == -1) {
//            return@forEach
//        }
//        val key = it.substring(0,eqId)
//        val value = it.substring(eqId+1)
//        set(key,value)
//    }
}

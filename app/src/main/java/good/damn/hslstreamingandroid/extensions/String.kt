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
            return@forEach
        } else if ((it == splitChar || it == 0xA) && !isQuote) {
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
    splitChar: Int,
    splitKeyValue: String
) = HashMap<String,String>().apply {
    val split = splitChar(splitChar)
    for (it in split) {
        val eqId = it.indexOf(splitKeyValue)
        if (eqId == -1) {
            set("URL", it)
            continue
        }
        val key = it.substring(0,eqId)
        val value = it.substring(eqId+1)
        set(key,value)
    }
}

fun String.toLocalPathUrl(): String {
    val indexSub = lastIndexOf("/")
    return substring(
        0,
        indexSub+1
    )
}
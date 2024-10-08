package good.damn.hslstreamingandroid.extensions

import android.util.Size

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

fun String.getProperties() = HashMap<String,String>().apply {
    split(
        ",".toRegex()
    ).forEach {
        val eqId = it.indexOf("=")
        if (eqId == -1) {
            return@forEach
        }
        val key = it.substring(0,eqId)
        val value = it.substring(eqId+1)
        set(key,value)
    }
}

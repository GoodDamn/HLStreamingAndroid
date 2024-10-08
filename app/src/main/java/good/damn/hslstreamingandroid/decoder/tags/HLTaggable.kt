package good.damn.hslstreamingandroid.decoder.tags

interface HLTaggable<T> {
    fun getInfoTag(
        line: String
    ): T
}
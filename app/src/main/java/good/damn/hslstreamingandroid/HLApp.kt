package good.damn.hslstreamingandroid

import android.app.Application
import android.os.Looper
import android.os.Handler
import java.io.File

class HLApp: Application() {

    companion object {

        lateinit var appDir: File

        val mainHandler = Handler(
            Looper.getMainLooper()
        )

        fun ui(
            run: Runnable
        ) = mainHandler.post(
            run
        )

    }

    override fun onCreate() {
        super.onCreate()

        appDir = applicationContext.cacheDir
    }

}
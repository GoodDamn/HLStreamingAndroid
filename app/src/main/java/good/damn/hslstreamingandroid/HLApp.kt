package good.damn.hslstreamingandroid

import android.app.Application
import android.os.Looper
import android.os.Handler

class HLApp: Application() {

    companion object {

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



    }

}
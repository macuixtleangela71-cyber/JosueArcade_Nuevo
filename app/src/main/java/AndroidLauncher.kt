package com.josue.game

import android.os.Bundle
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration

class AndroidLauncher : AndroidApplication() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Install before initialize(): crashes during LibGDX startup are recorded too.
        CrashHandler.install(applicationContext)
        val config = AndroidApplicationConfiguration()
        initialize(MainGame(), config)
    }
}

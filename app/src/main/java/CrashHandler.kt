package com.josue.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import java.io.PrintWriter
import java.io.StringWriter

class CrashHandler : Thread.UncaughtExceptionHandler {
    private val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        val stackTrace = StringWriter()
        throwable.printStackTrace(PrintWriter(stackTrace))
        
        // Guarda el archivo en la memoria del teléfono
        val file: FileHandle = Gdx.files.external("crash_log.txt")
        file.writeString(stackTrace.toString(), false)

        // Cierra la app normalmente
        defaultHandler?.uncaughtException(thread, throwable)
    }
}
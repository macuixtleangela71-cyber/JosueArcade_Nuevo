package com.josue.game

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/** Installs a process-wide crash logger before LibGDX starts. */
object CrashHandler {
    @Volatile private var installed = false

    @Synchronized
    fun install(context: Context) {
        if (installed) return

        val appContext = context.applicationContext
        val previousHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, error ->
            try {
                val trace = StringWriter().also { error.printStackTrace(PrintWriter(it)) }.toString()
                val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z", Locale.US).format(Date())
                val report = """
                    JosueArcade crash report
                    Time: $timestamp
                    Thread: ${thread.name} (${thread.id})
                    Android: ${android.os.Build.VERSION.RELEASE} (SDK ${android.os.Build.VERSION.SDK_INT})
                    Device: ${android.os.Build.MANUFACTURER} ${android.os.Build.MODEL}

                    $trace
                """.trimIndent() + "\n"

                // App-specific external storage needs no storage permission. Fall back to private storage.
                val directory = appContext.getExternalFilesDir(null) ?: appContext.filesDir
                val logFile = File(directory, "crash_log.txt")
                FileOutputStream(logFile, true).use { stream ->
                    stream.write(("\n--- Crash ---\n" + report).toByteArray(Charsets.UTF_8))
                    stream.flush()
                    stream.fd.sync()
                }
            } catch (_: Throwable) {
                // Never let a logging failure prevent Android's normal crash handling.
            } finally {
                previousHandler?.uncaughtException(thread, error)
            }
        }
        installed = true
    }
}

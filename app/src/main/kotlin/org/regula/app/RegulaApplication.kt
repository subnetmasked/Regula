package org.regula.app

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.regula.app.data.ContentRepository
import org.regula.app.data.RegulaDatabase

class RegulaApplication : Application() {
    val database: RegulaDatabase by lazy { RegulaDatabase.getInstance(this) }

    private val contentRepository: ContentRepository by lazy {
        ContentRepository(this, database)
    }

    override fun onCreate() {
        super.onCreate()
        applicationScope.launch {
            contentRepository.syncIfNeeded()
        }
    }

    companion object {
        private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }
}

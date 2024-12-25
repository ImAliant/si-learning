package fr.diamant.silearning

import android.app.Application
import fr.diamant.silearning.data.container.SIContainer
import fr.diamant.silearning.data.container.SIDataContainer
import fr.diamant.silearning.data.database.SIDatabase

class SILearningApplication: Application() {
    val database: SIDatabase by lazy { SIDatabase.getDatabase(this) }

    lateinit var container: SIContainer

    override fun onCreate() {
        super.onCreate()
        container = SIDataContainer(this)
    }
}
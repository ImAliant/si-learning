package fr.diamant.silearning

import android.app.Application
import fr.diamant.silearning.data.container.SIContainer
import fr.diamant.silearning.data.container.SIDataContainer

class SILearningApplication: Application() {
    lateinit var container: SIContainer

    override fun onCreate() {
        super.onCreate()
        container = SIDataContainer(this)
    }
}
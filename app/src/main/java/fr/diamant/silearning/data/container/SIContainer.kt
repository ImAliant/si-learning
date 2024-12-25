package fr.diamant.silearning.data.container

import android.content.Context
import fr.diamant.silearning.data.database.SIDatabase
import fr.diamant.silearning.data.repository.OfflineRepository
import fr.diamant.silearning.data.repository.Repository

interface SIContainer {
    val Repository: Repository
}

class SIDataContainer(private val context: Context): SIContainer {
    private val database: SIDatabase by lazy { SIDatabase.getDatabase(context) }
    override val Repository: Repository by lazy { OfflineRepository(database.SIDao()) }
}
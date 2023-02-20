package managers

import objects.Village
import java.util.*
import kotlin.collections.HashMap

object VillageManager {
    private val villageMap = HashMap<UUID, Village>()

    fun getPlayer(uuid: UUID): Village? = villageMap[uuid]

}
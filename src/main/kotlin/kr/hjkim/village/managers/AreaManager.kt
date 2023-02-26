package kr.hjkim.village.managers

object AreaManager {

    private val areaMap = HashMap<Long,String>()

    fun getCurrentVillageName(chunkKey: Long): String? = areaMap[chunkKey]

    fun registerArea(chunkKey: Long, villageName: String) {
        areaMap[chunkKey] = villageName
    }

}
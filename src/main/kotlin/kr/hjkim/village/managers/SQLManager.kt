package kr.hjkim.village.managers

import kr.hjkim.library.managers.DataBaseManager

object SQLManager {
    private val source = DataBaseManager.source

    private const val SELECT_VILLAGER_SQL = ""
    private const val INSERT_VILLAGER_SQL = ""
    private const val UPDATE_VILLAGER_SQL = ""

    fun selectVillager(): Boolean {
        return try {
            source.connection.use { conn ->
                conn.prepareStatement(SELECT_VILLAGER_SQL).use { state ->
                    state.setString(1, "")
                    state.setString(2, "")
                    state.setString(3, "")
                }
            }
            true
        } catch (e: Exception) {
            println("selectVillager Error : $e")
            false
        }
    }

    fun insertVillager() {

    }

    fun updateVillager() {

    }

}
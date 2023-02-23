package kr.hjkim.village.listeners

import kr.hjkim.village.main
import kr.hjkim.village.managers.VillageBlockManager
import kr.hjkim.village.managers.VillagerManager
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerJoinEvent
import java.io.File

class VillageListener: Listener {

    /**
     * 플레이어가 [마을 생성 블럭]을 설치한 경우
     * @param event BlockPlaceEvent
     */
    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        val player = event.player
        val uuid = player.uniqueId
        val block = event.blockPlaced

        if (block.type != Material.STONE) return
        if (!VillageBlockManager.isVillageBlock(event.itemInHand)) return

        val villager = VillagerManager.getVillager(uuid)
        if (villager == null) { player.sendMessage("당신은 아직 마을에 속해있지 않습니다."); return }
        player.sendMessage("마을 생성 블록을 설치하였습니다.")
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        val uuid = player.uniqueId
    }

}
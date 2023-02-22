package kr.hjkim.village.listeners

import kr.hjkim.village.managers.VillageBlockManager
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent

class VillageListener: Listener {

    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        val player = event.player
        val uuid = player.uniqueId
        val block = event.blockPlaced

        if (block.type != Material.STONE) return
        if(!VillageBlockManager.isVillageBlock(event.itemInHand)) return




        /*if (lore.contains("마을 생성 블록")) {
            val villager = VillagerManager.getVillager(uuid)
            if (villager == null) { player.sendMessage("당신은 아직 마을에 속해있지 않습니다."); return }
            player.sendMessage("마을 생성 블록을 설치하였습니다.")
        }*/
    }
}
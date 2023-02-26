package kr.hjkim.village.listeners

import kr.hjkim.village.managers.AreaManager
import kr.hjkim.village.managers.VillageBlockManager
import kr.hjkim.village.managers.VillagerManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.world.ChunkLoadEvent

class VillageListener: Listener {

    /**
     * 플레이어가 [마을 생성 블럭]을 설치한 경우
     * @param event BlockPlaceEvent
     */
    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        val player = event.player
        val uuid = player.uniqueId
        val block = event.block

        val chunk = block.world.getChunkAt(block)
        val villageName = AreaManager.getCurrentVillageName(chunk.chunkKey)
        if(villageName == null) {
            if (!VillageBlockManager.isVillageBlock(event.itemInHand)) return
            val villager = VillagerManager.getVillager(uuid) ?: return
            event.isCancelled = true
            val village = villager.getVillage()
            if(village.addArea(chunk.chunkKey)) {
                village.save()
                player.sendMessage("추가되었습니다.")
            } else {
                player.sendMessage("더 이상 확장할 수 없습니다.")
            }
            return
        } else {
            if (VillageBlockManager.isVillageBlock(event.itemInHand)) {
                event.isCancelled = true
                player.sendMessage("이미 등록 된 땅입니다.")
                return
            }
            val villager = VillagerManager.getVillager(uuid)
            if(villager?.villageName != villageName) {
                event.isCancelled = true
                player.sendMessage("권한이 없습니다.")
                return
            }
        }
    }

    @EventHandler
    fun onChunkLoad(event: ChunkLoadEvent) {
        event.chunk.chunkKey
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        val uuid = player.uniqueId
    }

    /**
     * 마을에 등록돼 있는 플레이어가 서버에서 나갈 경우
     * @param event PlayerQuitEvent
     */
    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val player = event.player
        val uuid = player.uniqueId
        val villager = VillagerManager.getVillager(uuid) ?: return
        villager.quit()
    }

}
package kr.hjkim.village.commands

import kr.hjkim.village.exceptions.VillageCreateException
import kr.hjkim.village.main
import kr.hjkim.village.managers.RequestManager
import kr.hjkim.village.managers.VillageBlockManager
import kr.hjkim.village.managers.VillageManager
import kr.hjkim.village.managers.VillagerManager
import kr.hjkim.village.objects.VillageInviteRequest
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class VillageCommands: CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(args.isEmpty()) { usage(sender); return true }
        when(args[0]) {
            "생성","create" -> createVillage(sender,args)
            "초대" -> {
                sender as Player
                val villager = VillagerManager.getVillager(sender.uniqueId) ?: return true
                val village = villager.getVillage()
                village.addVillager(main.server.getPlayer("_MonkeyMagic_") ?: return true)
                sender.sendMessage("추가되었습니다.")
            }
            "수락" -> inviteAccept(sender)
            "test" -> {
                sender as Player
                sender.inventory.addItem(VillageBlockManager.getVillageBlock())
            }
            "test1" -> {
                sender as Player
                val villager = VillagerManager.getVillager(sender.uniqueId) ?: return true
                val village = villager.getVillage()
                sender.openInventory(village.chest)
            }
        }
        return true
    }

    private fun usage(sender: CommandSender) {
        if (sender.isOp) {
            sender.sendMessage(
                "=== 명령어 사용법 ===\n" +
                "/마을 최대확장설정 [숫자] : 최대확장 가능횟수를 설정합니다.\n" +
                "/마을 관리 : 마을 관리 GUI 를 엽니다."
            )
        } else {
            sender.sendMessage(
                "=== 명령어 사용법 ===\n" +
                "/마을 생성 [마을명] : 마을을 생성합니다.\n" +
                "/마을 초대 [닉네임] : 마을에 초대합니다.\n" +
                "/마을 수락 : 초대를 수락합니다.\n" +
                "/마을 거절 : 초대를 거절합니다.\n" +
                "/마을 추방 [닉네임] : 마을에서 추방합니다.\n" +
                "/마을 공용상자 : 마을의 공용상자를 엽니다.\n" +
                "/마을 멤버: 마을원 정보 GUI 를 엽니다.\n" + //VillagerRole.OWNER 라면 마을원 관리 GUI 열기
                "/마을 세금: 마을의 세금을 설정합니다.\n" +
                "/마을 정보: 마을의 정보를 봅니다.\n" +
                "/마을 나가기: 마을에서 나갑니다.\n"
            )
        }
    }

    /**
     * 마을을 생성합니다.
     * @param sender CommandSender
     * @param args Array<out String>
     */
    private fun createVillage(sender: CommandSender, args: Array<out String>) {
        if(sender !is Player) { sender.sendMessage("플레이어만 입력 가능한 명령어입니다."); return }
        if(args.size != 2) { sender.sendMessage("/마을 생성 [마을명] : 마을을 생성합니다."); return }
        try {
            VillageManager.createVillage(sender,args[1])
            sender.sendMessage("성공적으로: \"${args[1]}\" 마을을 생성하였습니다.")
        }
        catch (e: VillageCreateException) { sender.sendMessage("마을 생성에 실패하였습니다. ( ${e.message} )") }
    }

    private fun inviteVillager(sender: CommandSender, args: Array<out String>) {
        if(sender !is Player) { sender.sendMessage("플레이어만 입력 가능한 명령어입니다."); return }
        if(args.size != 2) { sender.sendMessage("/마을 초대 [닉네임] : 마을에 초대합니다."); return }
        val villager = VillagerManager.getVillager(sender.uniqueId)
        if(villager == null) {
            sender.sendMessage("당신은 마을이 없습니다.")
            return
        }
        val village = villager.getVillage()
        val targetNick = args[1]
        val target = main.server.getPlayer(targetNick)
        if(target == null) {
            sender.sendMessage("$targetNick 님을 찾을 수 없습니다.")
            return
        }
        VillageInviteRequest(sender,target,village).request()
    }

    private fun inviteAccept(sender: CommandSender) {
        if(sender !is Player) { sender.sendMessage("플레이어만 입력 가능한 명령어입니다."); return }
        val request = RequestManager.getRequest(sender.uniqueId)
        if(request == null) {
            sender.sendMessage("받은 초대 요청이 없습니다.")
            return
        }
        request.accept()
    }
}
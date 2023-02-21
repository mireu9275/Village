package kr.hjkim.village.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class VillageCommands: CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(sender !is Player) { sender.sendMessage("플레이어만 입력 가능한 명령어입니다."); return true }

        if(args.isEmpty()) {
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
            return true
        }

        if (sender.isOp) {
            if (args.size == 1) {
                when(args[0]) {
                    "test" -> TODO()
                }
            } else if (args.size == 2) {
                when(args[0]) {
                    "test" -> TODO()
                }
            }
        } else {
            if (args.size == 1) {
                when (args[0]) {
                    "test" -> TODO()
                }
            }
        }
        return true
    }
}
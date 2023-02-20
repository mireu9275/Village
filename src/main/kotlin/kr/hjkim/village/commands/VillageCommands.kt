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
                "=== 명령어 사용법 ==="
                )
            } else {
                sender.sendMessage(
                    "=== 명령어 사용법 ==="
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
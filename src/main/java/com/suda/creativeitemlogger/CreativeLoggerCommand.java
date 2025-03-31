package com.suda.creativeitemlogger;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreativeLoggerCommand implements CommandExecutor, TabCompleter {

    private final CreativeItemLogger plugin;

    public CreativeLoggerCommand(CreativeItemLogger plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("creativeitemlogger.admin")) {
            sender.sendMessage(Component.text("이 명령어를 사용할 권한이 없습니다.").color(NamedTextColor.RED));
            return true;
        }

        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "toggle":
                boolean newState = !plugin.isLoggingEnabled();
                plugin.setLoggingEnabled(newState);
                
                if (newState) {
                    sender.sendMessage(
                        Component.text("크리에이티브 모드 아이템 복사 로깅이 ")
                            .color(NamedTextColor.GREEN)
                            .append(Component.text("활성화").color(NamedTextColor.GREEN).decorate(TextDecoration.BOLD))
                            .append(Component.text("되었습니다.").color(NamedTextColor.GREEN))
                    );
                } else {
                    sender.sendMessage(
                        Component.text("크리에이티브 모드 아이템 복사 로깅이 ")
                            .color(NamedTextColor.GREEN)
                            .append(Component.text("비활성화").color(NamedTextColor.RED).decorate(TextDecoration.BOLD))
                            .append(Component.text("되었습니다.").color(NamedTextColor.GREEN))
                    );
                }
                break;
            case "reload":
                plugin.loadConfig();
                sender.sendMessage(Component.text("CreativeItemLogger 설정이 리로드되었습니다.").color(NamedTextColor.GREEN));
                break;
            case "status":
                Component statusComponent = Component.text("로깅 상태: ").color(NamedTextColor.GREEN);
                if (plugin.isLoggingEnabled()) {
                    statusComponent = statusComponent.append(Component.text("활성화").color(NamedTextColor.GREEN).decorate(TextDecoration.BOLD));
                } else {
                    statusComponent = statusComponent.append(Component.text("비활성화").color(NamedTextColor.RED).decorate(TextDecoration.BOLD));
                }
                sender.sendMessage(statusComponent);
                break;
            default:
                sendHelp(sender);
                break;
        }
        return true;
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage(Component.text("===== CreativeItemLogger 명령어 =====").color(NamedTextColor.GREEN));
        sender.sendMessage(
            Component.text("/creativelogger toggle").color(NamedTextColor.YELLOW)
                .append(Component.text(" - 로깅 기능 활성화/비활성화").color(NamedTextColor.WHITE))
        );
        sender.sendMessage(
            Component.text("/creativelogger reload").color(NamedTextColor.YELLOW)
                .append(Component.text(" - 설정 리로드").color(NamedTextColor.WHITE))
        );
        sender.sendMessage(
            Component.text("/creativelogger status").color(NamedTextColor.YELLOW)
                .append(Component.text(" - 현재 상태 확인").color(NamedTextColor.WHITE))
        );
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> completions = new ArrayList<>(Arrays.asList("toggle", "reload", "status"));
            return completions;
        }
        return new ArrayList<>();
    }
}
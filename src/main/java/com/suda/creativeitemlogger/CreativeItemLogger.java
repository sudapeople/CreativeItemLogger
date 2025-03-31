/*
 * CreativeItemLogger - 크리에이티브 모드 아이템 복사 로깅 플러그인
 * 마인크래프트 서버에서 크리에이티브 모드 중 마우스 휠 클릭으로 아이템을 복사할 때
 * 이벤트를 감지하고 설정된 명령어를 실행합니다.
 */

package com.suda.creativeitemlogger;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CreativeItemLogger extends JavaPlugin implements Listener {

    private boolean enableLogging;
    private List<String> commandsToExecute;
    private SimpleDateFormat dateFormat;

    @Override
    public void onEnable() {
        // 설정 파일 저장
        saveDefaultConfig();
        
        // 설정 로드
        loadConfig();
        
        // 이벤트 리스너 등록
        getServer().getPluginManager().registerEvents(this, this);
        
        // 명령어 등록
        getCommand("creativelogger").setExecutor(new CreativeLoggerCommand(this));
        
        getLogger().info("CreativeItemLogger가 활성화되었습니다.");
    }

    @Override
    public void onDisable() {
        getLogger().info("CreativeItemLogger가 비활성화되었습니다.");
    }
    
    public void loadConfig() {
        reloadConfig();
        enableLogging = getConfig().getBoolean("enable-logging", true);
        commandsToExecute = getConfig().getStringList("commands-to-execute");
        String dateFormatPattern = getConfig().getString("date-format", "yyyy-MM-dd HH:mm:ss");
        dateFormat = new SimpleDateFormat(dateFormatPattern);
    }
    
    public boolean isLoggingEnabled() {
        return enableLogging;
    }
    
    public void setLoggingEnabled(boolean enabled) {
        enableLogging = enabled;
        getConfig().set("enable-logging", enabled);
        saveConfig();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInventoryClick(InventoryClickEvent event) {
        if (!enableLogging) return;
        
        if (!(event.getWhoClicked() instanceof Player)) return;
        
        Player player = (Player) event.getWhoClicked();
        if (player.getGameMode() != GameMode.CREATIVE) return;
        
        // 마우스 휠 클릭 확인
        if (event.getClick() == ClickType.MIDDLE) {
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem != null && !clickedItem.getType().isAir()) {
                logItemCopy(player, clickedItem);
            }
        }
    }
    
    private void logItemCopy(Player player, ItemStack item) {
        String timestamp = dateFormat.format(new Date());
        String playerName = player.getName();
        String itemName = item.getType().toString();
        int amount = item.getAmount();
        
        // 콘솔에 로그 남기기
        getLogger().info(String.format("[%s] %s가 크리에이티브 모드에서 %s x%d을(를) 복사했습니다.", 
                timestamp, playerName, itemName, amount));
        
        // 설정된 명령어 실행
        for (String command : commandsToExecute) {
            String formattedCommand = command
                .replace("%player%", playerName)
                .replace("%item%", itemName)
                .replace("%amount%", String.valueOf(amount))
                .replace("%time%", timestamp);
            
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), formattedCommand);
        }
    }
}
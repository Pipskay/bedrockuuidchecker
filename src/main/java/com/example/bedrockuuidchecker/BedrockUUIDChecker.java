package com.example.bedrockuuidchecker;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class BedrockUUIDChecker extends JavaPlugin implements Listener {

    private static final String BEDROCK_PREFIX = ".";

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("=================================");
        getLogger().info(" BedrockUUIDChecker v1.2.0 aktif!");
        getLogger().info("=================================");
    }

    @Override
    public void onDisable() {
        getLogger().info("BedrockUUIDChecker dinonaktifkan.");
    }

    // Cek LEBIH AWAL sebelum player masuk server
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        String playerName = event.getName();
        String uuidStr = event.getUniqueId().toString();

        getLogger().info("[UUIDChecker] PreLogin - Nama: " + playerName + " | UUID: " + uuidStr);

        // CASE 1: Nama ada prefix "." tapi UUID bukan format Bedrock → BUG
        if (playerName.startsWith(BEDROCK_PREFIX) && !uuidStr.startsWith("00000000-0000-0000-")) {
            getLogger().warning("[UUIDChecker] BLOCKED! Nama Bedrock tapi UUID tidak valid!");
            getLogger().warning("  Nama : " + playerName);
            getLogger().warning("  UUID : " + uuidStr);
            event.disallow(
                AsyncPlayerPreLoginEvent.Result.KICK_OTHER,
                "§c§l[ UUID BERMASALAH ]\n\n" +
                "§eUUID kamu tidak valid untuk player Bedrock.\n\n" +
                "§7UUID: §f" + uuidStr + "\n\n" +
                "§a§lCara memperbaiki:\n" +
                "§71. Keluar dari server\n" +
                "§72. Tutup Minecraft sepenuhnya\n" +
                "§73. Buka kembali & konek ulang\n\n" +
                "§8Jika masalah berlanjut, hubungi admin."
            );
            return;
        }

        // CASE 2: Nama TIDAK ada prefix "." tapi UUID format Bedrock → anomali
        if (!playerName.startsWith(BEDROCK_PREFIX) && uuidStr.startsWith("00000000-0000-0000-")) {
            getLogger().warning("[UUIDChecker] BLOCKED! UUID Bedrock tapi nama tidak ada prefix!");
            getLogger().warning("  Nama : " + playerName);
            getLogger().warning("  UUID : " + uuidStr);
            event.disallow(
                AsyncPlayerPreLoginEvent.Result.KICK_OTHER,
                "§c§l[ UUID BERMASALAH ]\n\n" +
                "§eUUID kamu tidak valid.\n\n" +
                "§7UUID: §f" + uuidStr + "\n\n" +
                "§a§lCara memperbaiki:\n" +
                "§71. Keluar dari server\n" +
                "§72. Tutup Minecraft sepenuhnya\n" +
                "§73. Buka kembali & konek ulang\n\n" +
                "§8Jika masalah berlanjut, hubungi admin."
            );
            return;
        }

        // CASE 3: Java player normal
        if (!playerName.startsWith(BEDROCK_PREFIX) && !uuidStr.startsWith("00000000-0000-0000-")) {
            getLogger().info("[UUIDChecker] Java player normal: " + playerName);
            return;
        }

        // CASE 4: Bedrock player valid
        if (playerName.startsWith(BEDROCK_PREFIX) && uuidStr.startsWith("00000000-0000-0000-")) {
            getLogger().info("[UUIDChecker] Bedrock player valid: " + playerName);
        }
    }
}

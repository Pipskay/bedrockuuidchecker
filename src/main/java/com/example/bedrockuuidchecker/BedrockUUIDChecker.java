package com.example.bedrockuuidchecker;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.geysermc.floodgate.api.FloodgateApi;

public class BedrockUUIDChecker extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("=================================");
        getLogger().info(" BedrockUUIDChecker v1.0.0 aktif!");
        getLogger().info("=================================");
    }

    @Override
    public void onDisable() {
        getLogger().info("BedrockUUIDChecker dinonaktifkan.");
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Cek apakah Floodgate tersedia di server
        if (getServer().getPluginManager().getPlugin("floodgate") == null) {
            getLogger().warning("Floodgate tidak ditemukan! Plugin tidak bisa berjalan optimal.");
            return;
        }

        // Cek apakah player adalah Bedrock via Floodgate API
        boolean isBedrock = FloodgateApi.getInstance().isFloodgatePlayer(player.getUniqueId());

        // Jika bukan player Bedrock, skip
        if (!isBedrock) {
            return;
        }

        String uuidStr = player.getUniqueId().toString();
        String playerName = player.getName();

        getLogger().info("[Bedrock] Player login: " + playerName + " | UUID: " + uuidStr);

        // UUID Bedrock yang valid SELALU diawali dengan "00000000-0000-0000-"
        if (!uuidStr.startsWith("00000000-0000-0000-")) {
            getLogger().warning("[Bedrock] UUID BUG terdeteksi!");
            getLogger().warning("  Nama  : " + playerName);
            getLogger().warning("  UUID  : " + uuidStr);

            // Kick harus dijadwalkan agar tidak crash saat join event
            getServer().getScheduler().runTask(this, () -> {
                player.kickPlayer(
                    "§c§l[ UUID BERMASALAH ]\n\n" +
                    "§eUUID kamu tidak valid untuk player Bedrock.\n\n" +
                    "§7UUID kamu: §f" + uuidStr + "\n\n" +
                    "§a§lCara memperbaiki:\n" +
                    "§71. Keluar dari server\n" +
                    "§72. Tutup Minecraft sepenuhnya\n" +
                    "§73. Buka kembali & konek ulang\n\n" +
                    "§8Jika masalah berlanjut, hubungi admin server."
                );
            });
        } else {
            getLogger().info("[Bedrock] UUID valid untuk: " + playerName);
        }
    }
}

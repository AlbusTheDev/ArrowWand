package com.knockturnmc.arrowwand.Main;


import com.knockturnmc.arrowwand.Commands.Commands;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    public FileConfiguration config;

    @Override
    public void onEnable() {
        this.getCommand("arrow").setExecutor(new Commands());
        getServer().getPluginManager().registerEvents(this, this);
        saveDefaultConfig();
        config = getConfig();

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!config.contains(player.getUniqueId() + "")) {
            config.set(player.getUniqueId()+ ".mobs", 0);
            config.set(player.getUniqueId()+ ".monsters", 0);
            config.set(player.getUniqueId()+ ".players", 0);
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            player.sendMessage(ChatColor.GREEN + "You have shot an arrow!");
            if (player.getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_HOE)) {
                Location loc = player.getLocation();
                loc.add(0, 1, 0);
                Entity entity = player.launchProjectile(Arrow.class);
                Arrow a = (Arrow) entity;
                a.setCustomName(player.getName());
                a.setShooter(player);
                a.setCritical(true);
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Player player;
        if (event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
            if (event.getDamager() instanceof Arrow) {
                Arrow a = (Arrow) event.getDamager();
                event.setDamage(10000);
                if (a.getShooter() instanceof Player) {
                    player = (Player) a.getShooter();
                } else {
                    return;
                }

                if (a.getCustomName().equals(player.getName())) {

                    if (entity instanceof Monster) {
                        if (config.contains(player.getUniqueId() + ".monsters")) {
                            config.set(player.getUniqueId() + ".monsters", config.getInt(player.getUniqueId() + ".monsters") + 1);
                        } else {
                            config.set(player.getUniqueId() + ".monsters", 1);
                        }
                        saveConfig();

                    } else if (entity.getType().equals(EntityType.PLAYER)) {
                        if (config.contains(player.getUniqueId() + ".players")) {
                            config.set(player.getUniqueId() + ".players", config.getInt(player.getUniqueId() + ".players") + 1);
                        } else {
                            config.set(player.getUniqueId() + ".players", 1);
                        }
                        saveConfig();

                    } else if (entity instanceof Animals) {
                        if (config.contains(player.getUniqueId() + ".mobs")) {
                            config.set(player.getUniqueId() + ".mobs", config.getInt(player.getUniqueId() + ".mobs") + 1);
                        } else {
                            config.set(player.getUniqueId() + ".mobs", 1);
                        }
                        saveConfig();
                    }
                }

            }

        }
    }


    @Override
    public void onDisable() {
        saveConfig();
    }
}
package com.knockturnmc.arrowwand.Commands;

import com.knockturnmc.arrowwand.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Commands implements CommandExecutor {

    Main plugin;

    public void setPlugin(Main instance) {
        this.plugin = instance;
    }

    public void msg(Player player, String string) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', string));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        Player player;
        Player target;

        if (sender instanceof Player) {
            player = (Player) sender;
        } else {
            sender.sendMessage("Sorry, You cannot use this plugin in the console");
            return false;
        }

        if (cmd.getName().equalsIgnoreCase("arrow")) {

            if (args.length == 0) {

                msg(player, "");
                msg(player, "&aList of commands: ");
                msg(player, "");
                msg(player, "&e1./arrow give (Player): Gives you the arrow wand.");
                msg(player, "&e2./arrow status (Player) : Shows you the stats.");
                msg(player, "&e3./arrow help: Shows you this list.");
                msg(player, "");


            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("give")) {


                    msg(player, "&cPlease specify a player!");


                } else if (args[0].equalsIgnoreCase("status")) {

                    msg(player, "");
                    msg(player, "&aStatus: ");
                    msg(player, "");
                    for (String string : plugin.config.getConfigurationSection(player.getUniqueId().toString()).getKeys(false)) {
                        msg(player, "&a" + string + ": &b" + plugin.config.getInt(player.getUniqueId() + "." + string) + "");
                    }
                    msg(player, "");


                } else if (args[0].equalsIgnoreCase("help")) {


                    msg(player, "");
                    msg(player, "&aList of commands: ");
                    msg(player, "");
                    msg(player, "&e1./arrow give (Player): Gives you the arrow wand.");
                    msg(player, "&e2./arrow status (Player) : Shows you the stats.");
                    msg(player, "&e3./arrow help: Shows you this list.");
                    msg(player, "");


                } else {
                    msg(player, "&cCommand not recognized! do /arrow help for the list of commands.");
                }
            } else if (args.length == 2) {

                try {
                    target = Bukkit.getPlayer(args[1]);
                } catch (Exception e) {
                    msg(player, "&cPlayer not found!");
                    return true;
                }

                if (args[0].equalsIgnoreCase("give")) {
                    ItemStack diamondHoe = new ItemStack(Material.DIAMOND_HOE);

                    target.getInventory().addItem(diamondHoe);
                } else if (args[0].equalsIgnoreCase("status")) {

                    msg(player, "");
                    msg(player, "&aStatus: ");
                    msg(player, "");
                    for (String string : plugin.config.getConfigurationSection(target.getUniqueId().toString()).getKeys(false)) {
                        msg(player, "&a" + string + ": &b" + plugin.config.getInt(target.getUniqueId() + "." + string) + "");
                    }
                    msg(player, "");

                } else if (args[0].equalsIgnoreCase("help")){
                    msg(player, "&cToo many arguements!");
                } else {
                    msg(player, "&cCommand not recognized! do /arrow help for yhe list of commands");
                }
            } else if (args.length > 2) {
                msg(player, "&cToo many arguements.");
            }
        }
        return true;
    }
}

package BetterTeleports.commands;

import BetterTeleports.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class betterteleports implements CommandExecutor{
    Main plugin;

    public betterteleports(Main plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(this.plugin.getCommand("teleport")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("teleport")).setDescription(Objects.requireNonNull(plugin.getMessage("teleportCommandDescription")));
        Objects.requireNonNull(this.plugin.getCommand("teleport")).setUsage(Objects.requireNonNull(plugin.getMessage("teleportCommandUsage")));
        Objects.requireNonNull(this.plugin.getCommand("tpall")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("tpall")).setDescription(Objects.requireNonNull(plugin.getMessage("tpallCommandDescription")));
        Objects.requireNonNull(this.plugin.getCommand("tpall")).setUsage(Objects.requireNonNull(plugin.getMessage("tpallCommandUsage")));
        Objects.requireNonNull(this.plugin.getCommand("tphere")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("tphere")).setDescription(Objects.requireNonNull(plugin.getMessage("tphereCommandDescription")));
        Objects.requireNonNull(this.plugin.getCommand("tphere")).setUsage(Objects.requireNonNull(plugin.getMessage("tphereCommandUsage")));
    }

    @Override
    public boolean onCommand(CommandSender sender,Command command,String label,String[] args) {

        if (sender instanceof Player player) {
            if ((label.equals("tpall") || (label.equals("bringall")))) {
                Location location = player.getLocation();
                for (Player player2 : player.getServer().getOnlinePlayers()) {
                    if (player2 != player) player2.teleport(location);
                }
                sender.sendMessage(Objects.requireNonNull(plugin.getMessage("tpallCommandTeleportedAll")));

            }
            if (args.length != 0) {
                Player target = Bukkit.getServer().getPlayerExact(args[0]);
                if (target != null) {
                    switch (label) {
                        case "teleport","tp" -> {
                            if (args.length < 2) {
                                if (target == player) {
                                    sender.sendMessage(Objects.requireNonNull(plugin.getMessage("teleportCommandTeleportSelf")));
                                    break;
                                }
                                player.teleport(target.getLocation());
                                sender.sendMessage(Objects.requireNonNull(plugin.getMessage("teleportCommandTeleportOther")));
                                break;
                            }
                            Player target2 = Bukkit.getServer().getPlayerExact(args[1]);
                            if (target2 == null) {
                                sender.sendMessage(Objects.requireNonNull(plugin.getMessage("cannotFindPlayer")).replace("{0}",args[0]));
                                break;
                            }
                            if (target2 == player && target == player) {
                                sender.sendMessage(Objects.requireNonNull(plugin.getMessage("teleportCommandTeleportSelf")));
                                break;
                            }
                            target.teleport(target2.getLocation());
                            sender.sendMessage(Objects.requireNonNull(plugin.getMessage("teleportCommandTeleportOtherToOther")).replace("{0}",args[0]).replace("{1}",args[1]));
                            return true;


                        }
                        case "tphere" -> {
                            target.teleport(player.getLocation());
                            if (target != player) {
                                player.sendMessage(Objects.requireNonNull(plugin.getMessage("teleportCommandTeleportOtherToOther")).replace("{0}",target.getName()).replace("{1}",player.getName()));
                            } else {
                                player.sendMessage(Objects.requireNonNull(plugin.getMessage("teleportCommandTeleportSelf")));
                            }
                        }
                    }
                } else {
                    sender.sendMessage(Objects.requireNonNull(plugin.getMessage("cannotFindPlayer")).replace("{0}",args[0]));
                }

            } else {
                sender.sendMessage(Objects.requireNonNull(plugin.getMessage("pleaseSpecifyPlayer")));
            }
        } else {
            sender.sendMessage(Objects.requireNonNull(plugin.getMessage("notAPlayer")));
        }
        return true;
    }
}

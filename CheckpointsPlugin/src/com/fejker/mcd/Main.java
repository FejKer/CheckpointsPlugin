package com.fejker.mcd;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collection;

public class Main extends JavaPlugin implements Listener{

    Location location = null;
    boolean bool = false;

    @Override
    public void onEnable() {
        System.out.println("Checkpoints zaladowane.");
        super.onEnable();
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @EventHandler
    public void onWarp(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        player.getInventory().clear();
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        Location location = new Location(world, 751, 75, 1330);
        if(event.getClickedBlock().getLocation().equals(location)){
            //player.sendMessage("1");
            if(bool){
                player.sendMessage("Zaczekaj zanim znowu zglosisz sie na wyscig");
                return;
            } else {
                bool = true;
                BukkitTask bukkitRunnable = new BukkitRunnable() {
                    @Override
                    public void run() {
                        //player.sendMessage("3");
                        bool = false;
                    }
                }.runTaskTimer(this, 200, 200);
            }
            //player.sendMessage("4");
        }
    }

    @EventHandler
    public void onCheckpoint(PlayerInteractEvent event) {
        World world = Bukkit.getWorld("world");
        Location buttonLocation1 = new Location(world,838 ,95 ,1055);
        Location buttonLocation2 = new Location(world,813 ,96 ,1035);
        Location buttonLocation3 = new Location(world,819 ,87 ,1035);
        Player player = event.getPlayer();
        if(event.getClickedBlock().getType().name().equals("OAK_BUTTON") && (event.getClickedBlock().getLocation().equals(buttonLocation1) || event.getClickedBlock().getLocation().equals(buttonLocation2) || event.getClickedBlock().getLocation().equals(buttonLocation3))) {
            player.sendMessage("" + ChatColor.BOLD + ChatColor.GREEN + "Checkpoint ustawiony! Jesli spadniesz wpisz /checkpoint");
            location = player.getLocation();
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(label.equalsIgnoreCase("checkpoint")){
            if(sender instanceof Player && location != null) {
                Player player = (Player) sender;
                player.getInventory().clear();
                player.teleport(location);
                player.sendMessage("" + ChatColor.BOLD + ChatColor.GREEN + "Powrot do checkpointu!");
                return true;
            }
            else {
                System.out.println("error");
                return true;
            }
        }
      /*  if(label.contains("warp")){
            Player player = (Player) sender;
            player.sendMessage("tu");
            player.getInventory().clear();
            //player.getInventory().getChestplate().setAmount(0);
            return true;
        }
        if(label.equalsIgnoreCase("back")){
            Player player = (Player) sender;
            player.sendMessage("tu");
            player.getInventory().clear();
            //player.getInventory().getChestplate().setAmount(0);
            return true;
        }*/
        return false;
    }

}

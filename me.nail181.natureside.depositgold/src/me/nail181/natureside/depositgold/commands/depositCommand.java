package me.nail181.natureside.depositgold.commands;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.Main;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.fusesource.jansi.Ansi.Color;

import me.nail181.natureside.depositgold.MainClass;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public class depositCommand implements CommandExecutor{

	public String cmd1 = "deposit";
	private int amt;
	int gold = 0;
	int diamond = 0;
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		Inventory inv = player.getInventory();
		
			if(sender instanceof Player) {
			
				Economy economy = MainClass.getEconomy();
			
				if(cmd.getName().equalsIgnoreCase(cmd1)) {
					
					if(args.length != 2) {
						sender.sendMessage(ChatColor.RED + "Correct Syntax: /deposit [amount] [gold/diamond]");
						return true;
					}
					
					else {
					
						int amt = Integer.parseInt(args[0]);
						String type = args[1];
						ItemStack goldStacks = new ItemStack(Material.GOLD_INGOT, amt);
						ItemStack diamondStacks = new ItemStack(Material.DIAMOND, amt);
						gold = getAmount(player, goldStacks);
						diamond = getAmount(player, diamondStacks);
						
						if(amt>0) {							
							if(type.toLowerCase().equals("gold")) {
								if(amt<=gold) {
									MainClass.econ.depositPlayer(player.getName(), amt);
									inv.removeItem(goldStacks);
									sender.sendMessage(ChatColor.GREEN + "Transaction complete!");
									sender.sendMessage(ChatColor.GREEN + "New Balance: $" + economy.getBalance(player.getName()));
								}
								else {
									sender.sendMessage(ChatColor.RED + "You need more gold in your inventory!");
								}
							}
							else if(type.toLowerCase().equals("diamond")){
								if(amt<=diamond) {
									MainClass.econ.depositPlayer(player.getName(), amt*9);
									inv.removeItem(diamondStacks);
									sender.sendMessage(ChatColor.GREEN + "Transaction complete!");
									sender.sendMessage(ChatColor.GREEN + "New Balance: $" + economy.getBalance(player.getName()));
								}
								else {
									sender.sendMessage(ChatColor.RED + "You need more diamond in your inventory!");
								}
							}
						}
						else {
							sender.sendMessage(ChatColor.RED + "Please enter a positive integer!");
						}
					}
				}
				else {
					sender.sendMessage(ChatColor.RED + "Correct Syntax: /deposit [amount] [gold/diamond]");
				}
			}
			return true;
		}
		
	
	public static int getAmount(Player player, ItemStack item) {
        if (item == null)
            return 0;
        int amount = 0;
        for (int i = 0; i < 36; i++) {
            ItemStack slot = player.getInventory().getItem(i);
            if (slot == null || !slot.isSimilar(item))
                continue;
            amount += slot.getAmount();
        }
        return amount;
    }
}



//run command
//check for amount of gold in player inventory
//check if amount is valid for the amount of gold in player inventory
//keep track of gold ore mined 
//this will allow people to create farms pigman
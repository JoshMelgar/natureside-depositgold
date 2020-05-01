package me.nail181.natureside.depositgold.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.nail181.natureside.depositgold.MainClass;
import net.milkbowl.vault.economy.Economy;

public class withdrawCommand implements CommandExecutor{

	public String cmd1 = "withdraw";
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		Inventory inv = player.getInventory();
			
		if(sender instanceof Player) {
			
			Economy economy = MainClass.getEconomy();
			
			if(cmd.getName().equalsIgnoreCase(cmd1)) {
				if(args.length != 2) {
					sender.sendMessage(ChatColor.RED + "Correct Syntax: /withdraw [amount] [gold/diamond]");
					return true;
				}
				else {
					
					int amt = Integer.parseInt(args[0]);
					String type = args[1];
					
					ItemStack goldStacks = new ItemStack(Material.GOLD_INGOT, amt);
					ItemStack diamondStacks = new ItemStack(Material.DIAMOND, amt);
					
					if(amt>0) {
						if(amt <= economy.getBalance(player.getName())) {
							if(type.toLowerCase().equals("gold")) {
								MainClass.econ.withdrawPlayer(player.getName(), amt);
								inv.addItem(goldStacks);
								sender.sendMessage(ChatColor.GREEN + "Transaction complete!");
								sender.sendMessage(ChatColor.GREEN + "New Balance: $" + economy.getBalance(player.getName()));
							}
							
							else if(type.toLowerCase().equals("diamond")){
								if(amt*9 <= economy.getBalance(player.getName())) {
									MainClass.econ.withdrawPlayer(player.getName(), amt*9);
									inv.addItem(diamondStacks);
									sender.sendMessage(ChatColor.GREEN + "Transaction complete!");
									sender.sendMessage(ChatColor.GREEN + "New Balance: $" + economy.getBalance(player.getName()));
								}
								else {
									sender.sendMessage(ChatColor.RED + "You do not have enough money to withdraw a diamond!");
								}
							}
							else {
								sender.sendMessage(ChatColor.RED + "You can only withdraw gold and diamond!");
							}
						}
						else {
							sender.sendMessage(ChatColor.RED + "You do not have enough money!!");
						}
					}
					else {
						sender.sendMessage(ChatColor.RED + "Please enter a positive number!");
					}
				}			
			}
		}			
	return true;
	}
}

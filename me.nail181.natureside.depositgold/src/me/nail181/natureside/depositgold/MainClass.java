package me.nail181.natureside.depositgold;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.nail181.natureside.depositgold.commands.depositCommand;
import me.nail181.natureside.depositgold.commands.withdrawCommand;
import net.milkbowl.vault.economy.Economy;

public class MainClass extends JavaPlugin implements Listener{
	public static void main(String[] args) {
	}
		public static Economy econ = null;

		@Override
		public void onEnable() {
			System.out.println("(!) DepositGold enabled!");
			this.getCommand("deposit").setExecutor(new depositCommand());
			this.getCommand("withdraw").setExecutor(new withdrawCommand());
			
		      if (!setupEconomy() ) {
		            System.out.println("VAULT NOT WORKING!");
		            getServer().getPluginManager().disablePlugin(this);
		            return;
		        }
		}
		
		@Override
		public void onDisable() {
			System.out.println("(!) DepositGold disabled");
			
		}
		
		
	    private boolean setupEconomy() {
	        if (getServer().getPluginManager().getPlugin("Vault") == null) {
	            return false;
	        }
	        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
	        if (rsp == null) {
	            return false;
	        }
	        econ = rsp.getProvider();
	        return econ != null;
	    }
	    
	    public static Economy getEconomy() {
	        return econ;
	    }
	}





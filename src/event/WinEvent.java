package event;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import manager.AdvManager;
import manager.Holo;
import util.AMath;

public class WinEvent extends Event{
	private static final HandlerList HANDLERS = new HandlerList();
	private boolean isCancelled;
	private Player player;
	
	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
	@Override
	public HandlerList getHandlers() {
		// TODO Auto-generated method stub
		return HANDLERS;
	}
	
	public WinEvent(Player player) {
        this.player = player;
        this.isCancelled = false;
    }
	
	public boolean isCancelled() {
	    return this.isCancelled;
	}
	
	public void setCancelled(boolean isCancelled) {
	    this.isCancelled = isCancelled;
	}
	
	public Player getPlayer() {
		return player;
	}
}

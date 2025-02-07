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
import util.Holo;

public class FixedDealEvent extends Event{
	private static final HandlerList HANDLERS = new HandlerList();
	private boolean isCancelled;
	private float damage;
	private float fristdamage;
	private Entity caster;
	private Entity target;
	public boolean isDeath = false;
	
	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
	@Override
	public HandlerList getHandlers() {
		// TODO Auto-generated method stub
		return HANDLERS;
	}
	
	public FixedDealEvent(Entity caster,Entity target, float damage) {
        this.caster = caster;
        this.target = target;
        this.isCancelled = false;
        this.damage = damage;
        this.fristdamage = damage;
    }
	
	public boolean isCancelled() {
	    return this.isCancelled;
	}
	
	public void setCancelled(boolean isCancelled) {
	    this.isCancelled = isCancelled;
	}
	
	public Entity getCaster() { return caster; }
	public Entity getTarget() { return target; }
	public void setDamage(float damage) { this.damage = damage; }
	public float getDamage() { return damage; }
	public float getFristDamage() { return fristdamage; }
	
	
}

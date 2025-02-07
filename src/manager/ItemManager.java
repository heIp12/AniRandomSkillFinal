package manager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import ars.Rule;
import item.list1.itemBase;
import types.ItemList;

public class ItemManager {
	public List<itemBase> items;
	public List<itemBase> removes;
	Player player;
	public ItemManager(Player p){
		player = p;
		items = new ArrayList<itemBase>();
		removes = new ArrayList<itemBase>();
	}
	
	public void remove(itemBase i) {
		if(i.isBreaking()) {
			removes.add(i);
		}
	}
	
	public void onAddItem(itemBase i) {
		if(player == null) return;
		for(itemBase item : items) if(!removes.contains(item)) item.onAddItem(i);
	}
	
	public void onTicks() {
		if(removes.size() > 0) {
			for(itemBase it : removes) {
				it.itemRemove();
				items.remove(it);
			}
			removes.clear();
		}
		if(player == null || player.getGameMode() == GameMode.SPECTATOR || !Rule.c.containsKey(player)) return;
		for(itemBase item : items) if(!removes.contains(item)) item.tick();
	}
	
	public void onHit(EntityDamageByEntityEvent e) { 
		if(player == null || player.getGameMode() == GameMode.SPECTATOR || !Rule.c.containsKey(player)) return;
		for(itemBase item : items) item.onHit(e);
	}
	public void onAttack(EntityDamageByEntityEvent e) { 
		if(player == null || player.getGameMode() == GameMode.SPECTATOR || !Rule.c.containsKey(player)) return;
		for(itemBase item : items) item.onAttack(e);
	}
	
	
	public void onDeath(Player p,Entity e){ 
		if(player == null || !Rule.c.containsKey(player)) return;
		for(itemBase item : items) item.onDeath(p, e);
	}
	public boolean onRemove(Entity caster) { 
		if(player == null || player.getGameMode() == GameMode.SPECTATOR || !Rule.c.containsKey(player)) return true;
		boolean rt = true;
		for(itemBase item : items) {
			if(rt) rt = item.onRemove(caster);
		}
		
		return rt;
	}
	public boolean onSkill(PlayerItemHeldEvent e) { 
		if(player == null || player.getGameMode() == GameMode.SPECTATOR || !Rule.c.containsKey(player)) return true;
		boolean rt = true;
		for(itemBase item : items) {
			if(rt) rt = item.onSkill(e);
		}
		
		return rt;
	}
	public boolean onMove(PlayerMoveEvent e) { 
		if(player == null || player.getGameMode() == GameMode.SPECTATOR || !Rule.c.containsKey(player)) return true;
		boolean rt = true;
		for(itemBase item : items) {
			if(rt) rt = item.onMove(e);
		}
		
		return rt;
	}
	
	public void Remove() {
		if(player == null || player.getGameMode() == GameMode.SPECTATOR || !Rule.c.containsKey(player)) return;
		for(itemBase item : items) {
			try {
				item.itemRemove();
			} catch (Exception e) {
				System.out.println("[Error] Remove Error : " + item.getCode());
			}
		}
		items.clear();
	}

	public void onDamageEvent(EntityDamageEvent e) {
		if(player == null || player.getGameMode() == GameMode.SPECTATOR || !Rule.c.containsKey(player)) return;
		for(itemBase item : items) item.onDamageEvent(e);
		
	}

	public void onKey_F(PlayerSwapHandItemsEvent e) {
		if(player == null || player.getGameMode() == GameMode.SPECTATOR || !Rule.c.containsKey(player)) return;
		for(itemBase item : items) {
			if(item.onKey_F(e)) break;
		}
		
	}
}

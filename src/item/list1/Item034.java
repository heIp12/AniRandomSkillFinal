package item.list1;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import ars.ARSystem;
import ars.Rule;
import buff.Ice;
import buff.NoHeal;
import manager.AdvManager;
import types.box;
import util.AMath;
import util.Holo;
import util.ItemCreate;
import util.Text;

public class Item034 extends itemBase{
	int kill = 0;
	float damage = 0.75f;
	boolean on = false;
	
	public Item034(Player p){
		super(p);
		itemCode = 34;
	}
	double hp = 0;
	
	@Override
	public void onDeath(Player p, Entity e) {
		if(e == player) {
			kill++;
			if(kill >= 2 && !on) {
				on = true;
				damage = 2;
				Rule.c.get(player).skillmult += 2;
				hp = player.getMaxHealth()*0.5;
				player.setMaxHealth(player.getMaxHealth()+ hp);
				if(Rule.c.get(player) != null) {
					Rule.c.get(player).hp = (float)player.getMaxHealth();
				}
				ARSystem.heal(player,hp);
				questComplete();
			}
		}
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		e.setDamage(e.getDamage() * damage);
		return super.onAttack(e);
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		if(kill >= 2) {
			e.setDamage(e.getDamage()*0.7);
		}
		return super.onHit(e);
	}
	
	
	@Override
	public void itemRemove() {
		if(player.getHealth() > player.getMaxHealth() - hp) {
			player.setHealth(player.getMaxHealth() - hp);
		}
		player.setMaxHealth(player.getMaxHealth() - hp);
	}
}

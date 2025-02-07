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
import buff.Buff;
import buff.Dancing;
import buff.Nodamage;
import buff.Panic;
import buff.TimeStop;
import types.BuffType;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Text;
import util.ULocal;

public class Item054 extends itemBase{
	boolean istarget = true;
	
	public Item054(Player p){
		super(p);
		itemCode = 54;
	}
	
	@Override
	public void tick() {
		Entity e = null;
		for(Entity en : ARSystem.box(player, new Vector(30,30,30), box.TARGET)) {
			if(en instanceof Player) e = en;
		}
		
		if(e != null) {
			if(!istarget) {
				Rule.c.get(player).skillmult -= 2;
			}
			istarget = true;
			
			ARSystem.giveBuff(player, new Panic(player), 2);
		} else {
			if(istarget) {
				Rule.c.get(player).skillmult += 2;
			}
			istarget = false;
		}
		
		super.tick();
	}
	
	@Override
	public boolean onSkill(PlayerItemHeldEvent e) {
		if(istarget) {
			e.setCancelled(true);
		}
		return super.onSkill(e);
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		if(!istarget) {
			e.setDamage(e.getDamage() * 2);
		}
		return super.onAttack(e);
	}
	
	@Override
	public void itemRemove() {
		if(!istarget) {
			Rule.c.get(player).skillmult -= 2;
		}
		super.itemRemove();
	}
}

package item.list1;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import ars.ARSystem;
import ars.Rule;
import chars.c.c000humen;
import chars.c2.c50sayaka;
import chars.ca.c5000sayaka;
import event.Skill;
import mode.MQb;
import util.AMath;
import util.ItemCreate;
import util.Map;
import util.Text;

public class Item027 extends itemBase{
	boolean on = false;
	public Item027(Player p){
		super(p);
		itemCode = 27;
	}
	
	@Override
	protected void onStart() {
		if(Rule.c.get(player).number == 23) {
			Rule.c.get(player).skillmult += 0.5;
		}
		if(Rule.c.get(player).number == 50) {
			timer = 1200;
			Rule.c.get(player).setStack(40);
		}
		super.onStart();
	}
	
	@Override
	protected void onTick() {
		if(timer%20 == 0) {
			if(Rule.c.get(player).number == 1023) {
				ARSystem.playerItem.get(player).remove(this);
			}
			ARSystem.heal(player, 0.6);
		}
		if(timer > 2000 && !on) {
			on = true;
			if(Rule.c.get(player).number == 50 && !ARSystem.isGameMode("lobotomy")) {
				((c50sayaka)Rule.c.get(player)).sk0();
			} else {
				ARSystem.spellCast(player, "item27");
				Rule.c.put(player,new c000humen(player, Rule.gamerule, null));
				delay(()->{
					Skill.quit(player);
				},280);
			}
		}
		if(timer%20 == 0) {
			String s[] = Text.get("item:27_t").split(",");
			int i = 0;
			for(Entity e : Rule.c.keySet()) {
				for(String st : s) {
					if(st.equals("" + (Rule.c.get(e).number%1000))) {
						i++;
						break;
					}
				}

			}
			if(i >= 4&& !ARSystem.isGameMode("qb")) {
				ARSystem.addGameMode(new MQb());
			}
		}
	}
	
	@Override
	public void onDeath(Player p, Entity e) {
		if(e == player) {
			timer = 0;
		}
		if(p == player) {
			int j = 0;
			for(Entity en : Rule.c.keySet()) {
				for(itemBase item : ARSystem.playerItem.get(en).items) {
					if(item.itemCode == 27) j++;
				}
			}
			if(j >= 5 && !ARSystem.isGameMode("qb")) {
				ARSystem.addGameMode(new MQb());
				return;
			}
		}
	}
	
	@Override
	public String getActionbar() {
		if(timer > 2000) return "";
		return "§c§l<§6"+itemName+" : §a" +AMath.round((2000 - timer)*0.05,1) +"§c§l>";
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		if(timer > 2000) {
			e.setCancelled(true);
		} else {
			e.setDamage(e.getDamage() * 0.9f);
		}
		return super.onHit(e);
	}
	
	@Override
	public boolean onRemove(Entity caster) {
		if(timer > 2000) return false;
		return super.onRemove(caster);
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		if(timer > 2000) {
			e.setCancelled(true);
		} else {
			e.setDamage(e.getDamage() * 1.2f);
		}
		return super.onAttack(e);
	}
	
	@Override
	public boolean onSkill(PlayerItemHeldEvent e) {
		if(timer > 2000) e.setCancelled(true);
		return super.onSkill(e);
	}
}

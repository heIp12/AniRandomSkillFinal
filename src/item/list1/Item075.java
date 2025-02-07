package item.list1;

import java.util.ArrayList;
import java.util.List;

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
import ars.gui.G_Item;
import buff.Buff;
import buff.Dancing;
import buff.Nodamage;
import buff.Panic;
import buff.Rampage;
import buff.TimeStop;
import event.Skill;
import types.BuffType;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Text;
import util.ULocal;

public class Item075 extends itemBase{
	int timer = 0;
	Entity target;
	public Item075(Player p){
		super(p);
		itemCode = 75;
	}
	
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		target = e.getEntity();
		timer = 100;
		if(Rule.buffmanager.GetBuffTime(player, "rampage") > 0) {
			e.setDamage(e.getDamage() * 2);
		}
		return super.onAttack(e);
	}
	
	@Override
	protected void onTick() {
		if(timer > 0) timer--;
	}
	
	@Override
	public void onDeath(Player p, Entity e) {
		if(e == player) {
			ARSystem.heal(player, p.getMaxHealth() * 0.5);
		} else if(target == p && timer > 0){
			if(Rule.c.get(player).number != 23) {
				ARSystem.giveBuff(player, new Rampage(player), 600, 2);
			}

			ARSystem.playSound((Entity)player, "minecraft:entity.ghast.hurt", 1.4f);
		}
	}
}

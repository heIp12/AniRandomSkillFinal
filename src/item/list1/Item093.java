package item.list1;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
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
import buff.Silence;
import buff.TimeStop;
import chars.c.c000humen;
import chars.c.c00main;
import event.Skill;
import types.BuffType;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Map;
import util.Text;
import util.ULocal;

public class Item093 extends itemBase{
	double hp = 0;
	public Item093(Player p){
		super(p);
		itemCode = 93;
	}
	
	@Override
	protected void onStart() {
		hp = player.getMaxHealth();
		player.setMaxHealth(hp*2);
		ARSystem.heal(player, hp);
		Rule.c.get(player).skillmult += 1;
	}
	
	@Override
	protected void onTick() {
		for(Buff b : Rule.buffmanager.getBuffs(player).getBuff()) {
			if(b.istype(BuffType.DEBUFF)) {
				b.addTime(-2);
			}
		}
		super.onTick();
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		e.setDamage(e.getDamage() * 0.7f);
		return super.onHit(e);
	}

	@Override
	public void itemRemove() {
		player.setHealth(player.getHealth() - hp);
		player.setMaxHealth(player.getMaxHealth() - hp);
		Rule.c.get(player).hp -= hp;
		Rule.c.get(player).skillmult -= 1;
	}
}

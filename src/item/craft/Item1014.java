package item.craft;

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
import ars.gui.G_Supply;
import ars.gui.solo.G_ItemSelect;
import buff.Buff;
import buff.Dancing;
import buff.Nodamage;
import buff.Panic;
import buff.Rampage;
import buff.Silence;
import buff.TimeStop;
import chars.c.c000humen;
import event.Skill;
import item.list1.itemBase;
import manager.AdvManager;
import types.BuffType;
import types.ItemList;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Map;
import util.NpcPlayer;
import util.Text;
import util.ULocal;

public class Item1014 extends itemBase{
	public Item1014(Player p){
		super(p);
		itemCode = 1014;
	}
	double hp = 0;
	
	
	@Override
	protected void onStart() {
		new G_ItemSelect(player, 10000);
		Rule.c.get(player).skillmult += 1f;
		
		hp = player.getMaxHealth();
		player.setMaxHealth(player.getMaxHealth()+ hp);
		if(Rule.c.get(player) != null) {
			Rule.c.get(player).hp = (float)player.getMaxHealth();
		}
		ARSystem.giveBuff(player, new Nodamage(player), 100);
		ARSystem.heal(player,hp);
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		e.setDamage(e.getDamage() * 0.3f);
		return super.onHit(e);
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		e.setDamage(e.getDamage() * 2f);
		return super.onAttack(e);
	}
	
	@Override
	public void itemRemove() {
		if(player.getHealth() > player.getMaxHealth() - hp) {
			player.setHealth(player.getMaxHealth() - hp);
		}
		player.setMaxHealth(player.getMaxHealth() - hp);
		Rule.c.get(player).skillmult -= 1f;
	}
}

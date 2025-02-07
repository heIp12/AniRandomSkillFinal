package item.list2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftInventoryCustom;
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
import ars.gui.G_HeddenSelect;
import ars.gui.G_Item;
import ars.gui.G_Supply;
import ars.gui.solo.G_ItemSelect;
import buff.Buff;
import buff.Dancing;
import buff.Nodamage;
import buff.Panic;
import buff.PowerUp;
import buff.Rampage;
import buff.Silence;
import buff.TimeStop;
import chars.c.c000humen;
import chars.c.c00main;
import event.Skill;
import item.list1.itemBase;
import manager.AdvManager;
import types.BuffType;
import types.ItemList;
import types.box;
import util.AMath;
import util.GetChar;
import util.InvSkill;
import util.Inventory;
import util.ItemCreate;
import util.Map;
import util.NpcPlayer;
import util.Text;
import util.ULocal;

public class Item146 extends itemBase{
	public Item146(Player p){
		super(p);
		itemCode = 146;
		setcooldown = 30;
	}
	
	@Override
	public boolean skillCast() {
		List<Entity> l =  ARSystem.PlayerBeamV(player, 2, 10, box.ALL);
		boolean on = true;
		for(Entity e : l) {
			if(!player.getPassengers().contains(e)) {
				player.addPassenger(e);
				on = false;
				break;
			}
		}
		return on;
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		if(player.getPassengers().contains(e.getEntity())) {
			e.setDamage(0);
			e.setCancelled(true);
		}
		return super.onAttack(e);
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		if(player.getPassengers().contains(e.getDamager())) {
			e.setDamage(0);
			e.setCancelled(true);
		}
		return super.onAttack(e);
	}
}
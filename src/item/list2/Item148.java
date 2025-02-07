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
import types.TargetMap;
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

public class Item148 extends itemBase{
	TargetMap<Double, Double> time = new TargetMap<>();
	Location loc;
	double speed = 0;
	double damage = 0;
	
	public Item148(Player p){
		super(p);
		itemCode = 148;
		loc = player.getLocation();
	}
	
	
	@Override
	protected void onTick() {
		time.add(loc.distance(player.getLocation()), 1);
		speed = 0;
		for(Double db : time.get().keySet()) speed+=db*0.4;
		
		loc = player.getLocation();
		

		for(Double db : time.get().keySet()) {
			time.add(db, -0.05);
			if(time.get(db) <= 0) {
				time.removeAdd(db);
			}
		}
		damage = (speed*0.1);
		time.removes();
	}

	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		e.setDamage(e.getDamage() * (1+damage));
		return super.onAttack(e);
	}
	
	@Override
	public String getActionbar() {
		String n = itemName.split(" ")[0];
		if(n.length() > 3) n = n.substring(0,2);
		
		return "§c§l<§6"+n+" : §e" + (int)(damage*100) + "%"+ "§c§l>";
	}
}
package item.etc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftInventoryCustom;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import ars.ARSystem;
import ars.Rule;
import ars.gui.G_Item;
import ars.gui.G_Select;
import buff.Buff;
import buff.Dancing;
import buff.Nodamage;
import buff.Nodie;
import buff.Panic;
import buff.PowerUp;
import buff.Rampage;
import buff.Silence;
import buff.TimeStop;
import chars.c.c000humen;
import chars.c.c00main;
import event.Skill;
import item.list1.itemBase;
import types.BuffType;
import types.box;
import util.AMath;
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.ItemCreate;
import util.Map;
import util.Text;
import util.ULocal;

public class Item10010 extends itemBase{
	boolean passive = true;
	Entity target;
	int timer = 0;
	
	public Item10010(Player p){
		super(p);
		itemCode = 100010;
		itemtable = false;
		
	}
	
	@Override
	protected void onTick() {
		if(timer > 0) {
			timer--;
			if(timer%2==0) {
				List<Entity> entity = ARSystem.box(player,new Vector(4,4,4),box.TARGET);
				if(entity.isEmpty()) {
					ARSystem.spellCast(player, ARSystem.boxSOne(player, new Vector(200,200,200),box.TARGET), "c31_p10");
				} else {
					ARSystem.spellCast(player,"c31_p2");
				}
				if(timer == 0) {
					Rule.buffmanager.selectBuffTime(player, "nodamage",0);
					Skill.death(player, target);
				}
			}
		}
	}
	
	@Override
	public boolean skillCast() {
		if(passive) {
			timer = 100;
			target = player;
			passive = false;
			Rule.c.put(player, new c000humen(player, Rule.gamerule, null));
			ARSystem.giveBuff(player, new TimeStop(player), 20);
			ARSystem.giveBuff(player, new Silence(player), 100);
			ARSystem.giveBuff(player, new Nodamage(player), 100);
			ARSystem.spellCast(player,"c31_p0");
			ARSystem.potion(player, 18, 60, 10);
		}
		return super.skillCast();
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		if(player.getHealth() - e.getDamage() < 1 && passive) {
			timer = 100;
			target = e.getDamager();
			passive = false;
			Rule.c.put(player, new c000humen(player, Rule.gamerule, null));
			ARSystem.giveBuff(player, new TimeStop(player), 20);
			ARSystem.giveBuff(player, new Silence(player), 100);
			ARSystem.giveBuff(player, new Nodamage(player), 100);
			ARSystem.spellCast(player,"c31_p0");
			ARSystem.potion(player, 18, 60, 10);
			e.setDamage(0);
			e.setCancelled(true);
			return false;
		}
		return super.onHit(e);
	}
	
	@Override
	public void kill(LivingEntity death, LivingEntity killer) {
		if(!passive && killer == player) {
			timer = 100;
			ARSystem.giveBuff(player, new Silence(player), 100);
			ARSystem.giveBuff(player, new Nodamage(player), 100);
			ARSystem.potion(player, 18, 60, 10);
		}
	}
	

	@Override
	public ItemStack getItem() {
		item = super.getItem();
		item.setTypeId(276);
		item.setDurability((short)1400);
		item = ItemCreate.Name(item, "§c§l【§e§l⸎§c§l】§a"+Text.get("item:"+itemCode));
		return item;
	}
}

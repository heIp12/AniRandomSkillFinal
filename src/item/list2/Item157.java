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
import buff.NoCC;
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

public class Item157 extends itemBase{
	boolean on = false;
	int tick = 0;
	public Item157(Player p){
		super(p);
		itemCode = 157;
	}
	
	@Override
	public boolean skillCast() {
		on = true;
		return false;
	}
	
	@Override
	protected void onTick() {
		if(tick > 0) tick--;
	}
	
	@Override
	public boolean onSkill(PlayerItemHeldEvent e) {
		int slot = (e.getNewSlot()+1);
		if(on && Rule.c.get(player).cooldown[slot] >= 0 &&  Rule.c.get(player).setcooldown[slot] >= 0 && Text.get("c"+Rule.c.get(player).number+":sk"+slot+"_cooldown") != null) {
			on = false;
			Rule.c.get(player).setcooldown[slot] = -10000;
			tick = 160;
			ARSystem.playSound(player, "entity.generic.burn",0.2f);
		}
		return super.onSkill(e);
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		if(tick > 0) {
			ARSystem.spellCast(player, e.getEntity(), "item157");
			ARSystem.playSound((Entity)player, "0boom3");
			e.setDamage(e.getDamage()*3);
		}
		return super.onAttack(e);
	}
	
	@Override
	public String getActionbar() {
		String n = itemName.split(" ")[0];
		if(n.length() > 3) n = n.substring(0,2);

		if(on) return "§c§l<§e"+Text.get("item:157")+"§c§l>";
		if(tick > 0) return "§c§l<§e"+Text.get("item:157")+" : " + AMath.round(tick*0.05, 2)+"§c§l>";
		
		
		return super.getActionbar();
	}
}
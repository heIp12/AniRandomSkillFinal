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
import ars.gui.G_Select;
import ars.gui.G_Supply;
import ars.gui.solo.G_ItemSelect;
import buff.Buff;
import buff.Dancing;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.PowerUp;
import buff.Rampage;
import buff.Silence;
import buff.TimeStop;
import buff.Wound;
import chars.c.c000humen;
import chars.c.c00main;
import event.FixedDealEvent;
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

public class Item141 extends itemBase{
	double hp = 0;
	int code = 0;
	public Item141(Player p){
		super(p);
		itemCode = 141;
	}
	
	@Override
	protected void onStart() {
		ARSystem.playSound((Entity)player, "item141");
		ARSystem.giveBuff(player, new TimeStop(player), 200);
		List<Integer> list = new ArrayList<>();
		if(Rule.c.size() <= 2) list.add(12);
		new G_Select(player, list);
		hp = player.getHealth();
		code = Rule.c.get(player).number;
	}
	
	 @Override
	protected void onTick() {
		 if(hp > 0 && code != Rule.c.get(player).number) {
			 if(player.getMaxHealth() > hp) {
				 player.setHealth(hp);
			 }
			 hp = 0;
		 }
	}
}
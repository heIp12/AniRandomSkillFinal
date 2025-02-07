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
import buff.Stun;
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

public class Item150 extends itemBase{
	double yaw = 1000;
	int zero = 0;
	int last = 0;
	boolean left = false;
	
	public Item150(Player p){
		super(p);
		itemCode = 150;
	}
	
	@Override
	protected void onTick() {
		float yw = player.getLocation().getYaw();
		if(Math.abs(Math.abs(yw) - Math.abs(yaw)) > 45 && player.isSneaking()) {
			if(yw - yaw < 0 && left) return; 
			if(yw - yaw > 0 && !left) return;
			left = !left;
			last = 0;
			Rule.playerinfo.get(player).gold += 10;
			player.setSneaking(false);
			yaw = yw;
			zero++;
			ARSystem.playSound((Entity)player, "0zero"+zero);
			if(zero > 7) zero = 0;
		} else {
			last++;
			if(last > 60) {
				zero = 0;
			}
		}
	}
	
}
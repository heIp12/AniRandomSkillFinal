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

public class Item144 extends itemBase{
	Location lc;
	public Item144(Player p){
		super(p);
		itemCode = 144;
		setcooldown = 30;
	}
	
	@Override
	protected void onTick() {
		if(lc != null) {
			if(timer%20 == 0) {
				ARSystem.spellLocCast(player,lc, "item144");
			}
			for(Entity e : ARSystem.locEntity(lc, new Vector(2,6,2), player)) {
				if(ARSystem.isTarget(e, player, box.TARGET)) {
					ARSystem.playSound((Entity)e, "item144");
					e.teleport(ULocal.offset(player.getLocation(), new Vector(3,0,0)));
					ARSystem.playSound(e, "item144");
					ARSystem.giveBuff((LivingEntity)e, new Stun((LivingEntity)e), 40);
					ARSystem.giveBuff((LivingEntity)e, new Silence((LivingEntity)e), 40);
					lc = null;
				}
			}
		}
	}
	
	@Override
	public boolean skillCast() {
		ARSystem.spellCast(player, "item144");
		lc = player.getLocation();
		return false;
	}
	
	
}
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
import org.bukkit.potion.PotionEffectType;
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
import event.WinEvent;
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

public class Item158 extends itemBase{
	int time = 0;
	Location lc;
	
	public Item158(Player p){
		super(p);
		itemCode = 158;
		lc = player.getLocation();
	}

	@Override
	public boolean onSkill(PlayerItemHeldEvent e) {
		time = 0;
		return super.onSkill(e);
	}
	
	@Override
	protected void onTick() {
		if(player.getLocation().distance(lc) <= 0.5 && player.isSneaking()) {
			time++;
		} else {
			player.removePotionEffect(PotionEffectType.BLINDNESS);
			lc = player.getLocation().clone();
			time = 0;
		}
		if(time > 60) {
			ARSystem.potion(player, 15, 1000, 1000);
			if(time%20 == 0) {
				Rule.c.get(player).sskillmult += 0.02;
				ARSystem.heal(player, 0.3);
			}
		}
		super.onTick();
	}
	
}
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

public class Item138 extends itemBase{
	public Item138(Player p){
		super(p);
		itemCode = 138;
		setcooldown = 3;
	}
	
	@Override
	protected void onStart() {
		player.setHealth(1);
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		if(isCooldown()) {
			for(itemBase it : ARSystem.playerItem.get(player).items) {
				if(it.itemCode != 138) {
					it.notcooldown = true;
					it.onAttack(e);
					it.notcooldown = false;
				}
			}
			Rule.c.get(player).entitydamage(e, true);
			Rule.c.get(player).entitylastdamage(e);
		}
		return super.onAttack(e);
	}
}
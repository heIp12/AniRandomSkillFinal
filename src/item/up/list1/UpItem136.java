package item.up.list1;

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

public class UpItem136 extends upitemBase{
	public UpItem136(Player p){
		super(p);
		itemCode = 136;
		setcooldown = 0;
	}
	
	@Override
	public boolean skillCast() {
		Player pl = null;
		double i = 10000;
		for(Player p : Rule.c.keySet()) {
			if(p.getGameMode() != GameMode.SPECTATOR && p != player) {
				if(p.getLocation().distance(player.getLocation()) < i) {
					i = p.getLocation().distance(player.getLocation());
					pl = p;
				}
			}
		}
		if(pl != null) {
			player.teleport(ULocal.lookAt(player.getLocation().clone(), pl.getLocation()));
		}
		return false;
	}
	
	
}
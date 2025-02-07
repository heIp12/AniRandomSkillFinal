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
import manager.Bgm;
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

public class Item151 extends itemBase{
	boolean bgm = false;
	
	public Item151(Player p){
		super(p);
		itemCode = 151;
	}
	
	@Override
	protected void onTick() {
		if(Bgm.bgmcode.equals("b"+Rule.c.get(player).getBgm()) && !bgm) {
			bgm = true;
		} else if(!Bgm.bgmcode.equals("b"+Rule.c.get(player).getBgm()) && bgm) {
			bgm = false;
		}
	}
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		if(bgm) {
			e.setDamage(e.getDamage() * 1.5);
		}
		return super.onAttack(e);
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		if(bgm) {
			e.setDamage(e.getDamage() * 0.5);
		}
		return super.onHit(e);
	}
	
	@Override
	public String getActionbar() {
		if(!bgm) return super.getActionbar();
		String n = itemName.split(" ")[0];
		if(n.length() > 3) n = n.substring(0,2);
		return "§c§l<§4MVP§c§l>";
	}
}
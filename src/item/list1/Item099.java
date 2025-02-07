package item.list1;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
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
import ars.gui.G_Item;
import buff.Buff;
import buff.Dancing;
import buff.Nodamage;
import buff.Panic;
import buff.Rampage;
import buff.Silence;
import buff.TimeStop;
import event.Skill;
import types.BuffType;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Map;
import util.Text;
import util.ULocal;

public class Item099 extends itemBase{
	Entity e;
	public Item099(Player p){
		super(p);
		itemCode = 99;
		setcooldown = 5;
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		this.e = e.getEntity();
		return super.onAttack(e);
	}
	
	@Override
	protected void onTick() {
		if(e != null && e.getLocation().distance(player.getLocation()) > 25) {
			e = null;
		}
	}

	@Override
	public String getActionbar() {
		if(cooldown > 0) {
			return super.getActionbar();
		}
		if(e == null) return "§c§l<§6"+itemName+" : §e?§c§l>";
		return "§c§l<§6"+itemName+" : §e" + e.getName()+"("+ AMath.round(e.getLocation().distance(player.getLocation()), 1) +")§c§l>";
	}

	@Override
	public boolean skillCast(){
		if(e == null) return true;
		ARSystem.spellCast(player, e, "item99");
		ARSystem.playSound(player, "0attack", 2f);
		this.e.setVelocity(ULocal.lookAt(e.getLocation().clone(), player.getLocation()).getDirection().multiply(3));
		return false;
	}
}

package item.list1;

import java.util.ArrayList;
import java.util.List;

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
import util.Text;
import util.ULocal;

public class Item085 extends itemBase{
	Entity target;
	int tick = 0;
	
	public Item085(Player p){
		super(p);
		itemCode = 85;
		setcooldown = 15;
	}
	
	@Override
	protected void onTick() {
		if(tick > 0) tick--;
		super.onTick();
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		target = e.getEntity();
		return super.onAttack(e);
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		if(tick > 0 && target != null) {
			ARSystem.spellLocCast(player, target.getLocation(), "item85");
			ARSystem.playSound((Entity)player, "item85");
			ARSystem.playSound((Entity)target, "item85");
			tick = 0;
			((LivingEntity)target).setNoDamageTicks(0);
			((LivingEntity)target).damage(e.getDamage(),e.getDamager());
			e.setDamage(0);
			e.setCancelled(true);
		}
		return super.onHit(e);
	}
	


	@Override
	public String getActionbar() {
		if(cooldown > 0) return super.getActionbar();
		String n = itemName.split(" ")[0];
		if(n.length() > 3) n = n.substring(0,2);
		if(target == null) {
			return "§c§l<§6"+n+" : §4§l?§c§l>";
		}
		return "§c§l<§6"+n+" : §4§l" +target.getName() +"§c§l>";
	}
	
	@Override
	public boolean skillCast(){
		tick = 60;
		return false;
	}
}

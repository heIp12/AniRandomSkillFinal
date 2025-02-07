package item.list2;

import java.util.ArrayList;
import java.util.HashMap;
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
import buff.PowerUp;
import buff.Rampage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import event.Skill;
import event.WinEvent;
import item.list1.itemBase;
import types.BuffType;
import types.box;
import util.AMath;
import util.BlockUtil;
import util.ItemCreate;
import util.Map;
import util.Pair;
import util.Text;
import util.ULocal;

public class item130 extends itemBase{
	Location loc = null;
	HashMap<Entity,Double> damages = new HashMap<>();
	
	public item130(Player p){
		super(p);
		itemCode = 130;
		setcooldown = 50;
	}
	
	@Override
	public boolean skillCast() {
		damages.clear();
		loc = player.getLocation();
		return false;
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		if(loc != null) {
			if(damages.get(e.getEntity()) == null) {
				damages.put(e.getEntity(), 0.0);
			}
			damages.put(e.getEntity(), damages.get(e.getEntity()) + e.getDamage());
		}
		return super.onAttack(e);
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		if(loc != null) {
			cooldown = setcooldown;
			e.setDamage(e.getDamage()*0.001);
			player.teleport(loc);
			ARSystem.playSound(player, "0miss");
			for(Entity p : damages.keySet()) {
				LivingEntity en = (LivingEntity)p;
				en.setNoDamageTicks(0);
				en.damage(damages.get(p) * 0.5,player);
				ARSystem.playSound(en, "0boom7");
			}
			loc = null;
		}
		return super.onHit(e);
	}
}

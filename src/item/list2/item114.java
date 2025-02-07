package item.list2;

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
import buff.PowerUp;
import buff.Rampage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import event.Skill;
import item.list1.itemBase;
import types.BuffType;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Map;
import util.Text;
import util.ULocal;

public class item114 extends itemBase{
	
	public item114(Player p){
		super(p);
		itemCode = 114;
		setcooldown = 8;
	}

	@Override
	public boolean skillCast() {
		if(AMath.random(3) == 1) {
			ARSystem.playSound((Entity)player, "item114");
		} else if(AMath.random(2) == 1) {
			ARSystem.playSound((Entity)player, "item114b");
		} else {
			ARSystem.playSound((Entity)player, "item114c");
		}
		
		for(Entity e : ARSystem.box(player, new Vector(15,15,15),box.TARGET)) {
			e.teleport(ULocal.lookAt(e.getLocation().clone(), player.getLocation()));
		}
		return false;
	}
	
}

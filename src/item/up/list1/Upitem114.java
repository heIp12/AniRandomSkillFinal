package item.up.list1;

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

public class Upitem114 extends upitemBase{
	
	public Upitem114(Player p){
		super(p);
		itemCode = 114;
		setcooldown = 2;
	}

	@Override
	public boolean skillCast() {
		String t = "";
		if(AMath.random(3) == 1) {
			t = "b";
		} else if(AMath.random(2) == 1){
			t = "c";
		}

		ARSystem.playSound((Entity)player, "item114"+t);
		
		for(Entity e : ARSystem.box(player, new Vector(25,25,25),box.TARGET)) {
			if(e instanceof Player)ARSystem.playSound((Player)e, "item114"+t);
			e.teleport(ULocal.lookAt(e.getLocation().clone(), player.getLocation()));
		}
		return false;
	}
	
}

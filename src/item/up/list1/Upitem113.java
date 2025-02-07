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
import buff.Airborne;
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

public class Upitem113 extends upitemBase{
	
	public Upitem113(Player p){
		super(p);
		itemCode = 113;
		setcooldown = 15;
	}

	@Override
	public boolean skillCast() {
		ARSystem.playSound((Entity)player, "item113");
		
		for(Entity e : ARSystem.PlayerBeamBox(player, 15, 4, box.TARGET)) {
			delay(()->{
				e.setVelocity(new Vector(0,2,0));
				delay(()->{ARSystem.giveBuff((LivingEntity)e, new Airborne((LivingEntity)e), 100);},20);
			},20);
		}
		return false;
	}
	
}

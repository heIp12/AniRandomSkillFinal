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

public class Item096 extends itemBase{
	int tick = 0;
	public Item096(Player p){
		super(p);
		itemCode = 96;
		setcooldown = 20;
	}
	
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		Entity ey = null;
		for(Entity et : ARSystem.box(e.getEntity(), new Vector(20,20,20), box.ALL)){
			if(ARSystem.isTarget(et, player, box.TARGET)) {
				if(e.getEntity() != et) {
					ey = et;
					break;
				}
			}
		}
		if(ey != null && isCooldown()) {
			ARSystem.playSound((Entity)player, "item96");
			Location lc = ey.getLocation().clone().subtract(e.getEntity().getLocation());
			for(int i =0; i<11; i++) {
				Location l = e.getEntity().getLocation().clone().add(lc.clone().multiply(0.1*i));
				delay(()->{
					e.getEntity().teleport(l);
				},i);
			}
			
		}
		return super.onAttack(e);
	}
}

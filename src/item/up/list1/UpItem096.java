package item.up.list1;

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
import buff.Stun;
import buff.TimeStop;
import event.Skill;
import types.BuffType;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Map;
import util.Text;
import util.ULocal;

public class UpItem096 extends upitemBase{
	int tick = 0;
	public UpItem096(Player p){
		super(p);
		itemCode = 96;
		setcooldown = 10;
	}
	
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		Entity ey = null;
		for(Entity et : ARSystem.box(e.getEntity(), new Vector(30,30,30), box.ALL)){
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
			delay(()->{
				ARSystem.giveBuff(((LivingEntity)e.getEntity()), new Stun(((LivingEntity)e.getEntity())), 40);
			},11);
			
		}
		return super.onAttack(e);
	}
}

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

public class item108 extends itemBase{
	boolean key = false;
	Player e;
	public item108(Player p){
		super(p);
		itemCode = 108;
		setcooldown = 50;
	}
	
	@Override
	public boolean skillCast(){
		try {
		e = ARSystem.PlayerOnlyBeamBox(player, 30, 2, box.TARGET).get(0);
		} catch(Exception e) {
			cooldown = 0;
			return true;
		}
		if(e == null) {
			cooldown = 0;
			return true;
		} else {
			delay(()->{
				key = true;
			},10);
		}
		return false;
	}
	
	@Override
	public boolean onSkill(PlayerItemHeldEvent e) {
		if(key) {
			key = false;
			Rule.c.get(player).setcooldown[e.getNewSlot()+1] = -10000;
			Rule.c.get(this.e).setcooldown[e.getNewSlot()+1] = -10000;
			ARSystem.giveBuff(player, new Silence(player), 20);
			ARSystem.playSound((Entity)player, "item108");
			ARSystem.playSound(this.e, "item108");
			ARSystem.playerItem.get(player).removes.add(this);
			player.sendTitle("" + this.e.getCustomName(), "Skill : " + (e.getNewSlot()+1),0,60,0);
			
			player.getInventory().setHeldItemSlot(7);
			e.setCancelled(true);
			return false;
		}
		return super.onSkill(e);
	}
	
}

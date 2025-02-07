package item.list2;

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
import util.Text;
import util.ULocal;

public class item126 extends itemBase{
	boolean next = false;
	double range = 0;
	Entity target;
	public item126(Player p){
		super(p);
		itemCode = 126;
		setcooldown = 20;
	}
	
	@Override
	public boolean skillCast() {
		next = true;
		return false;
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		if(next) {
			next = false;
			target = e.getEntity();
			range = target.getLocation().distance(player.getLocation());
			delay(()->{
				Location lc = target.getLocation();
				ARSystem.spellLocCast(player, lc, "item126");
				delay(()->{
					for(Entity et : ARSystem.locEntity(lc, new Vector(6,3,6), player)) {
						if(ARSystem.isTarget(et, player, box.TARGET)) {
							LivingEntity en = (LivingEntity)et;
							double power = player.getLocation().distance(target.getLocation()) - range;
							power*=0.025;
							if(power < 0) power = 0;
							power +=1;
							en.setNoDamageTicks(0);
							en.damage(10*power,player);
						}
					}
				},8);
			},60);
		}
		return super.onAttack(e);
	}
	
}

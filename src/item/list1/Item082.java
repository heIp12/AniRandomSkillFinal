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

public class Item082 extends itemBase{
	int tick = 0;
	Location lc;
	List<Entity> target;
	
	public Item082(Player p){
		super(p);
		itemCode = 82;
		setcooldown = 90;
		target = new ArrayList<>();
	}
	
	@Override
	public void onDeath(Player p, Entity e) {
		if(!quest && e == player && tick > 0 &&target.contains(p)) {
			questComplete();
			setcooldown -= 60;
			cooldown -= 60;
		}
		super.onDeath(p, e);
	}
	
	@Override
	protected void onTick() {
		if(tick > 0) {
			for(Entity e : target) {
				LivingEntity en = (LivingEntity)e;
				ARSystem.giveBuff(en, new Silence(en), 8);
				if(e.getLocation().distance(lc) > 4) {
					Location lc = ULocal.lookAt(this.lc.clone(), en.getLocation());
					lc = lc.add(lc.getDirection().multiply(3.9));
					lc.setYaw(en.getLocation().getYaw());
					lc.setPitch(en.getLocation().getPitch());
					en.setNoDamageTicks(0);
					en.damage(1,player);
					en.teleport(lc);
					en.setVelocity(ULocal.lookAt(en.getLocation().clone(), this.lc).getDirection().multiply(2));
					ARSystem.playSound((Entity)player, "item82b");
				}
			}
			tick--;
		}
		super.onTick();
	}
	


	@Override
	public boolean skillCast(){
		List<Entity> ey = ARSystem.PlayerBeamBox(player, 20, 3, box.TARGET);
		if(ey.size() <= 0) return true;
		target.clear();
		ARSystem.playSound((Entity)player, "item82");
		LivingEntity en = (LivingEntity)ey.get(0);
		target.add(en);
		lc = en.getLocation();
		tick = 60;
		ARSystem.playSound((Entity)en, "item82b");
		ARSystem.spellLocCast(player, lc, "item82");
		if(quest) {
			for(Entity e2 : ARSystem.box(en, new Vector(15,15,15), box.ALL)) {
				if(ARSystem.isTarget(e2, player,box.TARGET) && e2.getLocation().distance(en.getLocation()) < 5) {
					target.add(e2);
					ARSystem.playSound((Entity)e2, "item82b");
				}
			}
			tick += 60;
		}
		return false;
	}
}

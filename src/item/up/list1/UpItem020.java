package item.up.list1;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import ars.ARSystem;
import ars.Rule;
import buff.PowerUp;
import util.AMath;
import util.ItemCreate;
import util.Text;

public class UpItem020 extends upitemBase{
	public UpItem020(Player p){
		super(p);
		itemCode = 20;
	}
	
	@Override
	public void onDeath(Player p, Entity e) {
		if(e != player && Rule.c.get(player) != null) {
			ARSystem.giveBuff(player, new PowerUp(player), 100,1);
			Rule.c.get(player).skillmult += 2;
			delay(()->{
				if(Rule.c.get(player) != null )Rule.c.get(player).skillmult -= 2;
			},100);
		}
	}
	
	@Override
	protected void onStart() {
		if(Rule.c.get(player).number == 100) {
			Rule.c.get(player).setcooldown[1] = -10000;
			Rule.c.get(player).setcooldown[2] = -10000;
			Rule.c.get(player).setcooldown[3] *= 0.4;
			Rule.c.get(player).setcooldown[4] *= 0.4;
		}
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		if(Rule.c.get(player).number == 100) {
			e.setDamage(e.getDamage()* 1.3f);
		}
		return super.onAttack(e);
	}
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		if(Rule.c.get(player).number == 100 && AMath.random(10) <= 8) {
			Location loc = player.getLocation().clone().add(3-AMath.random(6),3-AMath.random(6),3-AMath.random(6));
			int i = 0;
			while(!loc.getBlock().isEmpty() || !loc.clone().add(0,1,0).getBlock().isEmpty()) {
				loc = player.getLocation().clone().add(3-AMath.random(6),3-AMath.random(6),3-AMath.random(6));
				if(i > 100) break;
				i++;
			}
			ARSystem.playSound((Entity)player, "c100s1");
			player.teleport(loc);
			e.setDamage(0);
			e.setCancelled(true);
		}
		return super.onAttack(e);
	}
}

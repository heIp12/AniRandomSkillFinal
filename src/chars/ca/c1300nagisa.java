package chars.ca;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import ars.ARSystem;
import ars.Rule;
import buff.Stun;
import chars.c.c00main;
import types.box;
import util.MSUtil;

public class c1300nagisa extends c00main{
	int ticks = 0;
	int count = 0;
	boolean shdow = false;
	Location loc = null;
	float mult = 1.5f;
	
	public c1300nagisa(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1013;
		MSUtil.setvar(player, "c13", 0);
		load();
		text();
		ARSystem.playSound(player, "c13select");
	}
	
	@Override
	public boolean skill1() {
		count = 0;
		shdows();
		
		skill("c"+number+"_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {

		List<Entity> en = ARSystem.PlayerBeamBox(player, 27, 5,box.TARGET);
		if(en.size() > 0) {
			skill("c"+number+"_s2");
			ARSystem.spellCast(player, en.get(0), "c1013_s2_s");
			count = 0;
			shdows();
		} else {
			cooldown[2] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		cooldown[3] = 0.1f;
		loc = null;
		shdows();
		count = 0;
		shdow = true;

		skill("c"+number+"_s3");
		return true;
	}
	
	@Override
	public boolean skill4() {
		shdows();
		count = 60;
		ARSystem.playSound((Entity)player, "c13sp");
		return true;
	}
	
	public void shdows() {
		if(shdow) {
			shdow = false;
			cooldown[3] = 15* coolm;
			loc = null;
			skill("c"+number+"_s3-2");
		}
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(e == player) {
			ARSystem.heal(player,8);
			cooldown[1] -= setcooldown[1]*0.9;
			cooldown[2] -= setcooldown[2]*0.9;
			cooldown[3] -= setcooldown[3]*0.9;
			cooldown[4] -= setcooldown[4]*0.9;
		}
	}
	
	@Override
	public boolean tick() {
		ticks++;

		if(count > 0) {
			ARSystem.giveBuff(player, new Stun(player), 2);
			count--;
			skill("c"+number+"_s4");
		}
		if(shdow) {
			ARSystem.potion(player, 14, 10, 10);
		}
		
		ticks%=20;
		if(shdow) {
			if(loc != null && player.getLocation().distance(loc) > 0.1) {
				shdows();
			}
			loc = player.getLocation();
		}
		return false;
	}
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			
		} else {

		}
		return true;
	}
}

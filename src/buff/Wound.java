package buff;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import ars.ARSystem;
import ars.Rule;
import types.BuffType;
import util.AMath;

public class Wound extends Buff{
	Location loc;
	
	int time = 20;
	int timer = time;
	Entity damager;
	String effect = "";
	
	
	public Wound(LivingEntity target) {
		super(target);
		bufftype.add(BuffType.DEBUFF);
		bufftype.add(BuffType.DAMAGE);
		buffName = "wound";
		color = "§a";
		isScore = true;
		order = 40;
	}
	
	public void setDelay(Entity e ,int i,int delay) {
		time = i;
		damager = e;
		timer = delay;
	}

	public void setEffect(String s) {
		effect = s;
	}
	
	public String getText() {
		String s = color+"[" +buffName +"] :";
		if(tick > 0) {
			s += " &7"+AMath.round(tick*0.05,1)+":"+AMath.round(time*0.05,1)+"(s)";
		}
		if(value > 0){
			if(isValueP) {
				s += " &7"+AMath.round(value*100,0) + "%";
			} else {
				s += " &7"+AMath.round(value,1)+"D";
			}
		}
		return s;
	}

	public boolean onTicks() {
		timer--;
		if(timer <= 0) {
			if(!effect.equals("") && damager instanceof Player) {
				ARSystem.spellLocCast((Player) damager, target.getLocation(), effect);
			}
			timer = time;
			target.setNoDamageTicks(0);
			target.damage(value,damager);
		}
		return false;
	}
}

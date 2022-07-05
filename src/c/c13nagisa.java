package c;

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
import buff.Silence;
import buff.Stun;
import types.modes;
import util.MSUtil;

public class c13nagisa extends c00main{
	int ticks = 0;
	int count = 0;
	float mult = 1.5f;
	
	public c13nagisa(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 13;
		MSUtil.setvar(player, "c13", 0);
		load();
		text();
	}
	
	@Override
	public boolean skill1() {
		skill("c"+number+"_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		skill("c"+number+"_s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		skill("c"+number+"_s3");
		return true;
	}
	
	@Override
	public boolean skill4() {
		skill("c"+number+"_s4");
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			ARSystem.addBuff(target,new Stun(target), 60);
			ARSystem.addBuff(target,new Silence(target), 60);
		}
	}
	
	@Override
	public boolean tick() {
		ticks++;
		if(ticks%5==1) {
			if(MSUtil.getvar(player, "c13") == 3&&!isps && !spben) {
				spskillen();
				spskillon();
				cooldown[1] = 0;
				cooldown[2] = 0;
				cooldown[3] = 0;
				cooldown[4] = 0;
				mult = 3f;
				skill("c"+number+"_sp");
			}
		}
		ticks%=20;
		return false;
	}
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage()*3);
			if(e.getEntityType() != EntityType.ARMOR_STAND) {
				Location lc = e.getEntity().getLocation().clone();
				Location plc = e.getDamager().getLocation().clone();
				lc.setPitch(0);
				plc.setPitch(0);
				
				float targetFaceAngle = lc.clone().getDirection().angle(new Vector(1, 1, 1));
				float diffAngle = lc.toVector().subtract(plc.toVector()).angle(new Vector(1, 1, 1));
				float diff = Math.abs(targetFaceAngle - diffAngle);
				if(diff <= 0.5 && ((LivingEntity)e.getEntity()).getNoDamageTicks() <= 0) {
					((LivingEntity)e.getEntity()).setNoDamageTicks(0);
					e.setDamage(e.getDamage()*mult);
					float damage = (float) (Math.round(e.getFinalDamage()*10)/10.0);
					
					player.sendTitle("§4Critical!","§cDamage : " + damage,10,10,20);
					if(e.getEntityType() == EntityType.PLAYER) {
						count++;
						if(count > 7) {
							Rule.playerinfo.get(player).tropy(13,1);
						}
					}
					if(mult > 2) {
						cooldown[1] -= damage/4;
						cooldown[2] -= damage/4;
						cooldown[3] -= damage/4;
						cooldown[4] -= damage/4;
					}
					if(((LivingEntity)e.getEntity()).getHealth() - e.getFinalDamage() < 0) {
						skill("c"+number+"_crit0");
					} else {
						skill(" c"+number+"_crit");
					}
				}
			}
		} else {

		}
		return true;
	}
}

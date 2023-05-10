package chars.c;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import types.box;

import util.AMath;
import util.MSUtil;

public class c08yuuki extends c00main{
	double cooldowns = skillmult;
	
	public c08yuuki(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 8;
		load();
		text();
	}
	public void cool() {
		skillmult = cooldowns + cooldowns*(3-((player.getHealth()/player.getMaxHealth())*3));
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
		if(player.getHealth() > 8) {
			cooldown[1] = 0;
			cooldown[2] = 0;
			cooldown[3] = 0;
		} else {
			if(skillCooldown(0)) {
				spskillen();
				ARSystem.giveBuff(player, new Silence(player), 80);
				ARSystem.giveBuff(player, new Stun(player), 80);
				ARSystem.giveBuff(player, new Nodamage(player), 140);
				skill("c"+number+"_sp");
				if(player.getHealth() == 1) Rule.playerinfo.get(player).tropy(8,1);
			}
		}
		return true;
	}
	
	@Override
	protected boolean skill9() {
		List<Entity> el = ARSystem.box(player, new Vector(10,10,10),box.ALL);
		String is = "";
		for(Entity e : el) {
			if(Rule.c.get(e) != null) {
				if(Rule.c.get(e) instanceof c02kirito) {
					is = "kirito";
					break;
				}
				if(Rule.c.get(e) instanceof c47ren){
					is = "ren";
					break;
				}
			}
		}
		
		if(is.equals("kirito")) {
			ARSystem.playSound((Entity)player, "c8kirito");
		} else if(is.equals("ren")) {
			ARSystem.playSound((Entity)player, "c8ren");
		} else  {
			ARSystem.playSound((Entity)player, "c8db");
		}
		
		return true;
	}
	
	@Override
	public boolean tick() {
		if(player.getHealth() <= 7) {
			if(cooldown[4] <= 0) {
				if(!isps) {
					spskillon();
				}
			}
		}
		cool();
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*2);
			cool();
		} else {
			cool();
		}
		return true;
	}
}

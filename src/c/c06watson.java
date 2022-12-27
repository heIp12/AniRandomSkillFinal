package c;

import org.bukkit.GameMode;
import org.bukkit.Location;
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
import buff.Dancing;
import buff.Rampage;
import types.box;
import types.modes;
import util.AMath;
import util.MSUtil;

public class c06watson extends c00main{
	int count = 0;
	int c = 0;
	int timer;
	
	public c06watson(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 6;
		load();
		text();
	}
	
	@Override
	public boolean skill1() {
		skill("c"+number+"_s1");
		c++;
		if(c >= 20) {
			Rule.playerinfo.get(player).tropy(6,1);
		}
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
		if(timer >= 400) {
			if(skillCooldown(0)) {
				spskillen();
				skill("c"+number+"_sp");
				for(Entity e : ARSystem.box(player, new Vector(300,300,300), box.TARGET)) {
					ARSystem.addBuff((LivingEntity) e, new Dancing((LivingEntity) e), 300);
				}
				for(Player p :Rule.c.keySet()) {
					if(p != player && Rule.c.get(p) instanceof c06watson) {
						Rule.c.get(p).cooldown[0] += 15;
					}
				}
			}
		}
		return true;
		
	}
	
	@Override
	public boolean tick() {
		if(!isps && timer > 400) {
			spskillon();
		}
		if(!player.isOnGround()) {
			timer++;
		}
		count++;
		if(tk%20==0 && psopen) {
			scoreBoardText.add("&c ["+Main.GetText("c6:sk0")+ "]&f" + Math.round(timer*5)/100.0f);
		}
		count%=20;
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage()*3);
			ARSystem.giveBuff((LivingEntity)e.getEntity(), new Rampage((LivingEntity)e.getEntity()), 20);
			((LivingEntity)e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,200,10));
		}
		return true;
	}
}

package chars.c;

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
import buff.TimeStop;
import types.box;

import util.AMath;
import util.MSUtil;

public class c06watson extends c00main{
	long timers = System.currentTimeMillis() - 80000;
	int count = 0;
	int c = 0;
	int timer;
	float spcool = 0;
	
	int wt = 0;
	
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
			if(skillCooldown(0) && (System.currentTimeMillis()  - timers)*0.001 > spcool) {
				spskillen();
				skill("c"+number+"_sp");
				for(Entity e : ARSystem.box(player, new Vector(300,300,300), box.TARGET)) {
					ARSystem.addBuff((LivingEntity) e, new Dancing((LivingEntity) e), 300);
				}
				for(Player p :Rule.c.keySet()) {
					if(p != player && Rule.c.get(p) instanceof c06watson) {
						Rule.c.get(p).cooldown[0] = 60;
						if(((c06watson)Rule.c.get(p)).timers < System.currentTimeMillis() - 20000) ((c06watson)Rule.c.get(p)).timers = System.currentTimeMillis() - 20000;
					}
				}
				timers = System.currentTimeMillis();
			} else {
				cooldown[0] = spcool - (System.currentTimeMillis()  - timers)*0.001f;
			}
		}
		return true;
		
	}
	
	@Override
	public boolean tick() {
		if(spcool == 0) {
			spcool = setcooldown[0];
		}
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
		if(player.getLocation().clone().add(0,0,0).getBlock().getType().toString().contains("WATER")) {
			wt++;
			if(wt == 420) {
				ARSystem.playSound(player, "c1006sp");
			}
			if(wt >= 600) {
				wt = -99999;
				ARSystem.giveBuff(player, new TimeStop(player), 60);
				skill("c6_es");
				delay(()->{
					hpCost(5, false);
				},10);
				delay(()->{
					hpCost(10, false);
					Rule.playerinfo.get(player).tropy(6,2);
				},65);
			}
		} else {
			wt = 0;
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			
			ARSystem.giveBuff((LivingEntity)e.getEntity(), new Rampage((LivingEntity)e.getEntity()), 4);
			((LivingEntity)e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,200,10));
		}
		return true;
	}
}

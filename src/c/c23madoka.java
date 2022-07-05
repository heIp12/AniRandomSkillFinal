package c;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Buff;
import buff.Nodamage;
import buff.TimeStop;
import event.Skill;
import types.BuffType;
import util.MSUtil;

public class c23madoka extends c00main{
	int ticks = 0;
	int stack = 0;
	double damage = 1.0;
	boolean start = false;

	
	public c23madoka(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 23;
		damage = 1.0;
		load();
		text();
	}
	
	@Override
	public void setStack(float f) {
		damage = (int) f;
	}
	
	@Override
	public boolean skill1() {
		if(ticks > 0) {
			cooldown[1] = 0;
			return false;
		}
		skill("c"+number+"_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(ticks > 0) {
			cooldown[2] = 0;
			return false;
		}
		skill("c"+number+"_s2");
		stack = 0;
		ticks = 190;
		delay(new Runnable() {
			
			@Override
			public void run() {
				ArrayList<Player> pls = new ArrayList<Player>();
				for(Player p : Rule.c.keySet() ) {
					double hp = p.getHealth() / p.getMaxHealth();
					if(hp < 0.33*damage&&!spben) {
						ARSystem.giveBuff(p, new TimeStop(p), 200);
						pls.add(p);
					}
				}
				
				if(pls.size() >= 3&&!spben) {
					spskillon();
					spskillen();
					sp();
				} else {
					for(Player p : pls) {
						Skill.death(p,player);
					}
				}
			}
		},185);
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(ticks > 0) {
			cooldown[3] = 0;
			return false;
		}
		ARSystem.potion(player,1,60,2);
		ARSystem.potion(player,8,40,4);
		ARSystem.playSound((Entity)player, "c23s3");
		return true;
	}
	
	public void sp(){
		start = false;
		ARSystem.giveBuff(player, new Nodamage(player), 160);
		skill("c"+number+"_sp");
		for(Player p : Rule.c.keySet() ) {
			ARSystem.giveBuff(p, new TimeStop(p), 160);
		}
	}


	@Override
	public boolean firsttick() {
		if(start) {
			if(Rule.buffmanager.selectBuffType(player, BuffType.HEADCC) != null) {
				for(Buff buff : Rule.buffmanager.selectBuffType(player, BuffType.HEADCC)) {
					buf(buff);
				}
			}
			if(Rule.buffmanager.selectBuffType(player, BuffType.CC) != null) {
				for(Buff buff : Rule.buffmanager.selectBuffType(player, BuffType.CC)) {
					if(!buff.getName().equals("silence")) {
						buf(buff);
					}
				}
			}
		}
		
		return false;
	}
	
	public void buf(Buff bf) {
		if(bf.getTime() > 0) {
			double val = bf.getTime()/20;
			ARSystem.heal(player, val);
			skillmult += val*0.01;
			damage += val*0.04;
			ARSystem.addBuff(player, new Nodamage(player), bf.getTime()/4);
			bf.setTime(0);
		}
	}
	
	@Override
	public boolean tick() {
		if(ticks > 0) ticks--;
		if(!start) start = true;
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c23:ps")+ "]&f : "+ Math.round((damage-1)*100)+"%");
			scoreBoardText.add("&c ["+Main.GetText("c23:sk2")+ "]&f : "+ Math.round((0.33*damage)*100)+"%");
			if(Math.round((0.2*damage)*100) >= 50) {
				Rule.playerinfo.get(player).tropy(23,1);
			}
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			e.setDamage(e.getDamage() * damage);
		} else {

		}
		return true;
	}
}

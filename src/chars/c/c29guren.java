package chars.c;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
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
import buff.Noattack;
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import event.Skill;
import types.box;

import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c29guren extends c00main{
	int ticks = 0;
	int mana = 0;
	int type = 1;
	int cast = 0;
	boolean arukana = false;
	int count = 0;
	
	public c29guren(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 29;
		load();
		text();
	}
	
	@Override
	public void setStack(float f) {
		mana = (int) f;
	}
	
	@Override
	public boolean skill1() {
		if(arukana) {
			cooldown[1] = 0;
			return false;
		}
		if(mana >= 500) {
			mana-=500;
			skill("c29_s1");
		}
		return true;
	}
	@Override
	public boolean skill2() {
		if(type == 1) {
			if(player.isSneaking()) {
				skill("c29_s2_1");
				ARSystem.playSound((Entity)player,"0attack4");
			} else {
				player.setVelocity(player.getLocation().getDirection().multiply(2));
				delay(()->{
					skill("c29_s2_1");
					ARSystem.playSound((Entity)player,"0attack4");
				},5);
			}
		}
		if(type == 2) {
			skill("c29_s2_2");
			ARSystem.playSound((Entity)player,"0attack3");
		}
		if(type == 3) {
			skill("c29_s2_3");
			ARSystem.playSound((Entity)player,"0attack2");
		}
		if(type == 4) {
			skill("c29_s2_4");
		}
		type++;
		if(type == 5) {
			type = 1;
			cooldown[2] = 6;
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(arukana) {
			arukana = false;
		} else {
			arukana = true;
			skill("c29_s3");
		}
		return true;
	}
	
	@Override
	public boolean skill4() {
		if(arukana) {
			cooldown[4] = 0;
			return false;
		}
		if(mana >= 800 -(skillmult+sskillmult)) {
			mana-=800-(skillmult+sskillmult);
			cast++;
			ARSystem.giveBuff(player, new Stun(player), (int) ((4 - (skillmult+sskillmult))*20));
			ARSystem.giveBuff(player, new Silence(player), (int) ((4 - (skillmult+sskillmult))*20));
			if(cast == 7) {
				spskillon();
			}
			if(cast > 7) {
				ARSystem.giveBuff(player, new Stun(player), 80);
				ARSystem.giveBuff(player, new Nodamage(player), 80);
				spskillen();
				cast = 0;
				mana = 0;
				ARSystem.playSound((Entity)player,"c29sp8");
				skill("c29_sp");
			}
			else {
				ARSystem.playSound((Entity)player,"c29sp"+cast);
			}
			skill("c29_s4");
		}
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			target.damage(2,player);
			ARSystem.giveBuff(target, new Stun(target), 9);
		}
		if(n.equals("2")) {
			target.setNoDamageTicks(0);
			target.damage(2,player);
			target.setVelocity(new Vector(0,1,0));
			ARSystem.giveBuff(target, new Noattack(target), 50);
		}
		if(n.equals("3")) {
			target.setNoDamageTicks(0);
			target.damage(2,player);
			Vector loc = player.getLocation().getDirection();
			target.setVelocity(loc.clone().multiply(2));
			delay(()->{
				target.setVelocity(loc.multiply(-2));
			},10);
		}
		if(n.equals("4")) {
			target.setNoDamageTicks(0);
			target.damage(4,player);
		}
	}
	

	@Override
	public boolean tick() {
		if(tk%20==0) {
			scoreBoardText.add("&c ["+Main.GetText("c29:ps")+ "]&f : "+ mana + "/1865");
			if(psopen) scoreBoardText.add("&c ["+Main.GetText("c29:sk0")+ "]&f : "+ cast + "/8");
		}
		if(ticks%40==0) {
			if(mana < 1865) {
				if(!ARSystem.gameMode2) mana += 182 *(skillmult+sskillmult);
				
				mana += 121 *(skillmult+sskillmult);
			}
			if(mana > 1865) {
				mana = 1865;
			}
		}
		
		if(arukana && mana > 15) {
			mana -= 15;
			count++;
			if(count >= 400) {
				Rule.playerinfo.get(player).tropy(29,1);
			}
		} else if(arukana) {
			arukana = false;
		}
		
		if(arukana) {
			for(Entity e : ARSystem.box(player, new Vector(10, 8, 10),box.TARGET)) {
				if(Rule.c.get(e) != null) {
					for(int i = 0; i< 10; i++ ) if(Rule.c.get(e).cooldown[i] <= 0.1) Rule.c.get(e).cooldown[i] = 0.1f;
				}
				ARSystem.giveBuff((LivingEntity) e, new Silence((LivingEntity) e), 4);
			}
		}
		ticks++;
		
		ticks %= 40;
		return true;
	}

}

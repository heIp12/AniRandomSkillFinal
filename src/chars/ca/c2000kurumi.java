package chars.ca;

import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
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
import buff.Silence;
import buff.Stun;
import chars.c.c00main;
import chars.c2.c68origami;
import event.Skill;
import types.box;

import util.AMath;
import util.GetChar;
import util.MSUtil;

public class c2000kurumi extends c00main{
	int ticks = 0;
	int sk2 = 0;
	int count;
	
	public c2000kurumi(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1020;
		load();
		text();
		ARSystem.playSound(p, "c20select");
	}
	
	
	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c20sji",1,2);
		ARSystem.playSound((Entity)player, "c20shot",1,0.5f);
		skill("c"+number+"_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(sk2 <= 0) {
			skill("c"+number+"_s2");
			cooldown[2] = 0;
			sk2 = 60;
		} else {
			sk2 = 0;
			skill("c"+number+"_s2-0");
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		Location local = player.getLocation().clone();
		ARSystem.playSound((Entity)player, "c20sht");
		for(int i =0; i<5;i++) {
			delay(()->{
				Location loc = local.clone();
				loc.add(new Vector(1.0-AMath.random(20)*0.1,0.5-AMath.random(10)*0.1,1.0-AMath.random(20)*0.1));
				ARSystem.spellLocCast(player, loc, "c1020_shdow2");
			},i*5);
		}
		return true;
	}

	@Override
	public boolean skill4() {
		ARSystem.playSound((Entity)player, "c20bb",1,2);
		ARSystem.playSound((Entity)player, "c20shot",1,0.5f);
		skill("c"+number+"_s4");
		return true;
	}
	
	@Override
	public boolean tick() {
		if(sk2 > 0) {
			sk2--;
			if(sk2 == 0) {
				cooldown[2] = setcooldown[2];
			}
		}
		if(ARSystem.AniRandomSkill != null) {
			int time = ARSystem.AniRandomSkill.time;
			skillmult = 1 + time*0.01;
			Rule.buffmanager.selectBuffValue(player, "buffac", 1f + time*0.01f);
		}
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			ARSystem.addBuff(target, new Stun(target), 40);
			ARSystem.addBuff(target, new Silence(target), 40);
			for(int i = 0; i<4 ; i++) {
				int j = i;
				delay(()->{
					Location loc = target.getLocation();
					Location loc2 = target.getLocation();
					loc.setPitch(0);
					loc2.setPitch(0);
					
					loc.setYaw(loc.getYaw() + 180 - (j*45));
					ARSystem.spellLocCast(player, loc, "c1020_shdow");
					
					loc2.setYaw(loc2.getYaw() + 180 + (j*45));
					ARSystem.spellLocCast(player, loc2, "c1020_shdow");
				},i*5);
			}
		}
		if(n.equals("2")) {
			target.setHealth(target.getMaxHealth()/2);
			if(Rule.c.get(target) != null) {
				Skill.TimeLoop(target);
			}
		}
	}

	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			
		} else {
			for(int i =0; i <cooldown.length;i++) {
				cooldown[i] -= 0.5;
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
				if(Rule.c.get(e) instanceof c6800origami || Rule.c.get(e) instanceof c68origami) {
					is = "origami";
					break;
				}

			}
		}
		
		if(is.equals("origami")) {
			ARSystem.playSound((Entity)player, "c20origami");
		} else {
			ARSystem.playSound((Entity)player, "c20db");
		}
		
		return true;
	}
}

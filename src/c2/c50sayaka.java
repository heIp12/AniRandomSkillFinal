package c2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.MagicSpells;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Nodamage;
import buff.Stun;
import buff.TimeStop;
import c.c00main;
import event.Skill;
import manager.AdvManager;
import types.MapType;
import types.modes;
import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c50sayaka extends c00main{
	int tick = 0;
	boolean ps = false;
	float damage = 0;
	
	int speed = 0;

	public c50sayaka(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 50;
		load();
		text();
	}
	
	@Override
	public void setStack(float f) {
		speed = (int)f;
		skillmult = 1+speed*0.05;
	}

	@Override
	public boolean skill1() {
		int delay = 14;
		delay -= speed/2;
		if(delay <= 0) {
			skill("c50_s1");
		} else {
			ARSystem.playSound((Entity)player, "0slash5",2.f);
			delay(()->{skill("c50_s1");},delay);
		}
		return true;
	}
	@Override
	public boolean skill2() {
		player.setVelocity(player.getLocation().getDirection().multiply(1.4 + (speed*0.02)).setY(0.2));
		skill("c50_s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		int delay = 8;
		delay -= speed/3;
		if(delay < 1) delay = 1;
		
		int d = delay;
		ARSystem.playSound((Entity)player, "c50s3");
		int size = 5;
		if(speed > 5) size += (speed-3)*0.15;
		for(int i=0;i<size;i++) {
			delay(()->{
				ARSystem.playSound((Entity)player, "0slash2",1+((8-d)*0.1f),0.1f);
				ARSystem.giveBuff(player, new Stun(player), d+1);
				skill("c50_s3");
			},delay*i);
		}
		return true;
	}
	
	@Override
	public boolean skill4() {
		if(speed < 10) {
			skillmult+= 0.05;
			speed+=1;
		}
		skillmult+= 0.05;
		speed+=1;
		return true;
	}
	
	boolean son = false;
	@Override
	public boolean tick() {
		tick++;
		int delay = 20;
		delay-= speed/4;
		if(delay < 4) delay = 4;
		if(tick%delay==0) {
			ARSystem.heal(player, 1);
		}
		if(tk%20==0) {
			if(psopen) scoreBoardText.add("&c ["+Main.GetText("c50:sk0")+ "] : "+ damage +"/100");
			scoreBoardText.add("&c ["+Main.GetText("c50:sk4")+ "] : "+ (speed*5) +"%");
		}
		if(damage >= 100 && !isps && ARSystem.gameMode != modes.LOBOTOMY) {
			spskillon();
			spskillen();
			Rule.playerinfo.get(player).tropy(50,1);
			ARSystem.playSoundAll("c50sp",(float) 1);
			ARSystem.giveBuff(player, new TimeStop(player), 120);
			tpsdelay(new Runnable() {
				@Override
				public void run() {
					player.setGameMode(GameMode.SPECTATOR);
					player.setMaxHealth(1000);
					player.setHealth(1000);
					Map.mapType = MapType.NORMAL;
					Map.getMapinfo(1008);
					if(ARSystem.gameMode2) {
						for(Player p : Bukkit.getOnlinePlayers()) {
							Map.playeTp(p);
						}
					}
					Map.spawn("c50_1",new Location(player.getWorld(),169,35,69), 1);
					tpsdelay(()->{
						son = true;
					},30);
				}
			},100);
		}
		if(tick%10==0) {
			if(isps && son) {
				boolean is = true;
				for(LivingEntity e : player.getWorld().getLivingEntities()) {
					if(e.getCustomName() != null) {
						if(e.getCustomName().equals("Oktavia Von Seckendorff")) {
							if(!Map.inMap(e.getLocation())) {
								e.teleport(Map.randomLoc());
							}
							is = false;
						}
					}
				}
				if(is) {
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"mm m killall");
					Skill.remove(player, player);
				}
			}
		}
		return true;
	}


	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage()*2);
			if(Rule.c.get(e.getEntity()) != null) cooldown[4] -= e.getDamage()*3;
		} else {
			if(Rule.c.get(e.getDamager()) != null) cooldown[4] -= 3;
		}
		if(damage >= 100 && !isps && ARSystem.gameMode != modes.LOBOTOMY) {
			e.setCancelled(true);
			e.setDamage(0);
			spskillon();
			spskillen();
			Rule.playerinfo.get(player).tropy(50,1);
			ARSystem.playSoundAll("c50sp",(float) 1);
			ARSystem.giveBuff(player, new TimeStop(player), 120);
			tpsdelay(new Runnable() {
				@Override
				public void run() {
					player.setGameMode(GameMode.SPECTATOR);
					player.setMaxHealth(1000);
					player.setHealth(1000);
					Map.mapType = MapType.NORMAL;
					Map.getMapinfo(1008);
					if(ARSystem.gameMode2) {
						for(Player p : Bukkit.getOnlinePlayers()) {
							Map.playeTp(p);
						}
					}
					Map.spawn("c50_1",new Location(player.getWorld(),169,35,69), 1);
					tpsdelay(()->{
						son = true;
					},30);
				}
			},100);
			return false;
		}
		return true;
	}
	
	@Override
	public boolean damage(EntityDamageEvent e) {
		damage += e.getDamage();
		return super.damage(e);
	}
}

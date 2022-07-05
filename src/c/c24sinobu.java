package c;

import java.util.ArrayList;
import java.util.List;

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

import com.nisovin.magicspells.events.SpellTargetEvent;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Noattack;
import buff.Nodamage;
import buff.Silence;
import types.modes;
import util.AMath;
import util.MSUtil;
import util.Text;
import util.Var;

public class c24sinobu extends c00main{
	boolean passive;
	boolean ps;
	boolean drain;
	
	int time = 30;
	int ticks = 0;
	int heal = 0;
	
	int shdow = -1;
	LivingEntity target;
	
	public c24sinobu(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 24;
		load();
		text();
		passive = true;
		ps = false;
		drain = false;
		Rule.buffmanager.selectBuffTime(player, "silence",0);
	}
	
	@Override
	public boolean skill1() {
		if(shdow == -1) {
			skill("c"+number+"_s1");
		} else {
			cooldown[1] = 0;
		}
		return true;
	}
	
		
	
	@Override
	public boolean skill2() {
		if(shdow == -1) {
			skill("c"+number+"_s2");
		} else {
			cooldown[1] = 0;
		}
		return true;
	}

	
	@Override
	public boolean skill3() {
		if(shdow == -1 && player.getLocation().getBlock().getLightLevel() < 12) {
			shdow = 15;
			ARSystem.potion(player, 14, 300, 0);
			ARSystem.giveBuff(player, new Nodamage(player), 300);
			ARSystem.giveBuff(player, new Noattack(player), 300);
			cooldown[3] = 0;
			ARSystem.playSound((Entity)player, "c24s3");
		}
		else if(player.getLocation().getBlock().getLightLevel() < 12){
			shdow = -1;
			Rule.buffmanager.selectBuffTime(player, "nodamage",0);
			Rule.buffmanager.selectBuffTime(player, "noattack",0);
			ARSystem.potion(player, 14, 0, 0);
			ARSystem.playSound((Entity)player, "c24s3");
			
		} else {
			cooldown[3] = 0;
		}
		return true;
	}

	@Override
	public void TargetSpell(SpellTargetEvent e, boolean mycaster) {
		if(e.getSpell().getName().equals("c24_s1_a")) {
			drain = true;
		}
	}


	@Override
	public boolean tick() {
		ticks++;
		if(heal > 30&&!spben) {
			spskillen();
			spskillon();
			Rule.Var.open(player.getName()+".c"+(number%1000)+"Sp",isps);
			Rule.playerinfo.get(player).tropy(24,1);
			Rule.c.put(player, new c2400sinobu(player,Rule.gamerule, this));
		}
		if(tk%20 == 0 && psopen) {
			scoreBoardText.add("&c ["+Main.GetText("c24:sk0")+ "]&f : "+ heal + " / 30");
		}
		if(ticks < 20 && ARSystem.getPlayerCount() < 4) {
			ticks+=20;
		}
		if(tk%20 == 0 && shdow > 0) {
			shdow--;
			scoreBoardText.add("&c ["+Main.GetText("c24:sk3")+ "]&f : "+ shdow);
		}
		if(shdow == 0) {
			shdow = -1;
			cooldown[3] = setcooldown[3];
			Rule.buffmanager.selectBuffTime(player, "nodamage",0);
			Rule.buffmanager.selectBuffTime(player, "noattack",0);
			ARSystem.potion(player, 14, 0, 0);
			if(player.getLocation().getBlock().getLightLevel() > 11) player.damage(10,player);
			ARSystem.playSound((Entity)player, "c24s3");
		}
		if(ticks%10==0 && passive) {
			for(Entity e : player.getNearbyEntities(8, 8, 8)) {
				if(e instanceof Player) {
					if(Rule.c.get(e) != null && ((Player)e).getGameMode() == GameMode.ADVENTURE) {
						passive = false;
						player.setGameMode(GameMode.SPECTATOR);
						ARSystem.playSound((Entity)player, "c24p");
						ps = true;
						target = (LivingEntity) e;
						break;
					}
				}
			}
		}
		if(!passive && ps && Rule.c.get(target) == null) {
			ps = false;
			player.teleport(target);
			player.setGameMode(GameMode.ADVENTURE);
			ARSystem.giveBuff(player, new Nodamage(player), 50);
			ARSystem.giveBuff(player, new Silence(player), 50);
			ARSystem.playSound((Entity)player, "c24p2");
		}
		if(!passive && ps && time <= 0) {
			ps = false;
			player.teleport(target);
			player.setGameMode(GameMode.ADVENTURE);
			ARSystem.giveBuff(player, new Nodamage(player), 50);
			ARSystem.giveBuff(player, new Silence(player), 50);
			ARSystem.playSound((Entity)player, "c24p2");
		}
		if(time > 0 && !passive && ps && ARSystem.getPlayerCount() < 3) {
			ticks = 40;
		}
		if(!passive && ps && ticks%40 == 0 && time > 0) {
			time--;
			ARSystem.heal((Player) target,3);
			player.setMaxHealth(player.getMaxHealth()+1);
			player.setHealth(player.getMaxHealth());
			skillmult += 0.04;
		}
		ticks%=40;
		return false;
	}

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage()*2);
			if(Rule.c.get(e.getEntity()) != null) {
				String s = Main.GetText("c"+Rule.c.get(e.getEntity()).getCode()+":tag");
				if(s.indexOf("tg4") == -1) {
					e.setDamage(e.getDamage()*1.5);
				}
			}
			if(drain) {
				drain = false;
				if(player.getMaxHealth() - player.getHealth() < e.getDamage()) {
					heal += e.getDamage() - (player.getMaxHealth() - player.getHealth());
				}
				ARSystem.heal(player, e.getDamage());
			}
		} else {

		}
		return true;
	}
}

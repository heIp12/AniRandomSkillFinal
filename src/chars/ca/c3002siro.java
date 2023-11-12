package chars.ca;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c00main;
import chars.c2.c51zenos;
import chars.c2.c86iriya;
import event.Skill;
import manager.AdvManager;
import types.MapType;
import types.box;

import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c3002siro extends c00main{
	boolean sp = false;
	int timer = 0;
	int s1 = 0;
	
	public c3002siro(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 2030;
		load();
		text();
		sp();
	}

	@Override
	public boolean skill1() {
		if(s1 > 0) {
			ARSystem.playSound((Entity)player, "c2030s4");
			skill("c2030_s1-2");
		} else {
			delay(()->{
				cooldown[1] = 0;
				s1 = 20;
			},8);
			ARSystem.playSound((Entity)player, "c2030s1");
			ARSystem.giveBuff(player, new Stun(player), 10);
			skill("c2030_s1");
		}
		return true;
	}
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c2030s2");
		skill("c2030_s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c2030s3");
		ARSystem.giveBuff(player, new Nodamage(player), 60);
		ARSystem.giveBuff(player, new Stun(player), 60);
		skill("c30_s3");
		for(int i =0; i<3;i++) {
			delay(()->{
				ARSystem.heal(player, 5);
			},20*i);
		}
		return true;
	}
	
	@Override
	public boolean skill4() {
		skill("c2030_s4");
		return true;
	}
	
	public void sp() {
		s_score += 1000;
		ARSystem.potion(player, 14, 540, 1);
		skill("c2030_ef");
		
		for(Player p: Rule.c.keySet()) {
			Rule.c.get(p).PlayerSpCast(player);
		}

		delay(new Runnable() {
			@Override
			public void run() {
				player.performCommand("tm anitext all SUBTITLE true 30 c2030:t1/"+player.getName());
				ARSystem.playSoundAll("c2030sp1");
			}
		},0);
		delay(new Runnable() {
			@Override
			public void run() {
				player.performCommand("tm anitext all SUBTITLE true 50 c2030:t2/"+player.getName());
				ARSystem.playSoundAll("c2030sp2");
			}
		},40);
		delay(new Runnable() {
			@Override
			public void run() {
				player.performCommand("tm anitext all SUBTITLE true 50 c2030:t3/"+player.getName());
				ARSystem.playSoundAll("c2030sp3");
			}
		},120);
		delay(new Runnable() {
			@Override
			public void run() {
				player.performCommand("tm anitext all SUBTITLE true 40 c2030:t4/"+player.getName());
				ARSystem.playSoundAll("c2030sp4");
			}
		},190);
		
		delay(new Runnable() {
			@Override
			public void run() {
				player.performCommand("tm anitext all SUBTITLE true 60 c2030:t5/"+player.getName());
				ARSystem.playSoundAll("c2030sp5");
			}
		},250);
		delay(new Runnable() {
			@Override
			public void run() {
				player.performCommand("tm anitext all SUBTITLE true 60 c2030:t6/"+player.getName());
				ARSystem.playSoundAll("c2030sp6");
			}
		},340);
		delay(new Runnable() {
			@Override
			public void run() {
				player.performCommand("tm anitext all SUBTITLE true 60 c2030:t7/"+player.getName());
				ARSystem.playSoundAll("c2030sp7");
				delay(()->{
					ARSystem.playSoundAll("c2030sp8");
				},30);
			}
		},430);
		if(ARSystem.gameMode2) {
			delay(new Runnable() {
				@Override
				public void run() {
					String n = Main.GetText("c"+number+":name1") + " ";
					if(n.equals("-")) {
						n = "";
					}
					n +=Main.GetText("c"+number+":name2");
					Rule.buffmanager.selectBuffAddValue(player, "plushp",30);
					player.performCommand("tm anitext all TITLE true 20 c2030:sk0/c30:t8");
					isps = true;
					if(ARSystem.AniRandomSkill != null) ARSystem.AniRandomSkill.modes.clear();
					for(Player p : Bukkit.getOnlinePlayers()) {
						AdvManager.set(p, 399, 0 , "§f§l"+player.getName() +"§a("+ n +") §f§l" + Main.GetText("c"+number+":ps"));
						Map.getMapinfo(1012);
						Map.playeTp(p);
						player.setHealth(player.getMaxHealth());
						sp = true;
					}
				}
			},540);
		} else {
			delay(new Runnable() {
				@Override
				public void run() {
					String n = Main.GetText("c"+number+":name1") + " ";
					if(n.equals("-")) {
						n = "";
					}
					Map.mapType = MapType.NORMAL;
					n +=Main.GetText("c"+number+":name2");
					player.performCommand("tm anitext all TITLE true 20 c30:sk0/c30:t8");
					isps = true;
					for(Player p : Bukkit.getOnlinePlayers()) {
						AdvManager.set(p, 399, 0 , "§f§l"+player.getName() +"§a("+ n +") §f§l" + Main.GetText("c"+number+":sk0"));
						player.setHealth(player.getMaxHealth());
						sp = true;
					}
				}
			},540);
		}
		delay(new Runnable() {
			@Override
			public void run() {
				for(int i = 0; i < 51; i++) {
					Location loc = Map.randomLoc();
					loc.setY((float) (loc.getY() - 0.8 + (AMath.random(8)*0.1)));
					ARSystem.spellLocCast(player, loc, "c2030_p");
				}
			}
		},550);
		if(ARSystem.gameMode2) {
			delay(new Runnable() {
				@Override
				public void run() {
					for(int i = 0; i < 21; i++) {
						Location loc = Map.randomLoc();
						loc.setY((float) (loc.getY() - 0.8 + (AMath.random(8)*0.1)));
						ARSystem.spellLocCast(player, loc, "c2030_p");
					}
				}
			},560);
			delay(new Runnable() {
				@Override
				public void run() {
					for(int i = 0; i < 21; i++) {
						Location loc = Map.randomLoc();
						loc.setY((float) (loc.getY() - 0.8 + (AMath.random(8)*0.1)));
						ARSystem.spellLocCast(player, loc, "c2030_p");
					}
				}
			},570);
		}
	}

	@Override
	public boolean tick() {
		if(s1 > 0) s1--;
		if(!sp && tk%2 == 0) {
			ARSystem.heal(player, 1);
			Rule.buffmanager.selectBuffAddValue(player, "barrier",0.1f);
			ARSystem.giveBuff(player, new Stun(player), 10);
			ARSystem.giveBuff(player, new Silence(player), 10);
		}
		if(sp) {
			timer++;
			if(timer >= 10 && ARSystem.gameMode2) {
				skill("c30_sp");
				Location loc = Map.randomLoc();
				int i = 0;
				while(loc.distance(player.getLocation()) < 10 || loc.distance(player.getLocation()) > 20) {
					loc = Map.randomLoc();
					i++;
					if(i > 300) break;
				}
				loc.setY((float) (loc.getY() - 0.8 + (AMath.random(8)*0.1)));
				ARSystem.spellLocCast(player, loc, "c2030_p");
			
				timer = 0;
			}
		}
		return true;
	}

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			
		} else {

		}
		return true;
	}
	
	@Override
	protected boolean skill9() {

		ARSystem.playSound((Entity)player, "c2030db");
		
		return true;
	}
}

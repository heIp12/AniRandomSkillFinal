package c;

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
import buff.Stun;
import buff.TimeStop;
import c2.c51zenos;
import c2.c86iriya;
import event.Skill;
import manager.AdvManager;
import types.MapType;
import types.box;
import types.modes;
import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c30siro extends c00main{
	int passive = 0;
	int timer = 0;
	int time = 0;
	int count = 0;
	
	boolean sp = false;
	public c30siro(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 30;
		load();
		text();
	}
	
	@Override
	public void setStack(float f) {
		time = (int) f;
	}
	
	public void pasive() {
		count++;
		if(count >= 20) Rule.playerinfo.get(player).tropy(30,1);
		passive++;
		if(passive > 2) {
			ARSystem.playSound((Entity)player, "c30p");
		} else {
			ARSystem.playSound((Entity)player, "c30s");
		}
	}
	
	@Override
	public boolean skill1() {
		pasive();
		if(sp) skill("c30_sp2");
		if(passive > 2) {
			skill("c30_s1t");
			passive = 0;
		} else {
			skill("c30_s1");
		}
		return true;
	}
	@Override
	public boolean skill2() {
		pasive();
		if(sp) skill("c30_sp2");
		if(passive > 2) {
			skill("c30_s2t");
			passive = 0;
		} else {
			skill("c30_s2");
			skill("c30_s2_"+AMath.random(5));
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		pasive();
		if(sp) skill("c30_sp2");
		ARSystem.playSound((Entity)player, "c30s3");
		if(passive > 2) {
			skill("c30_s3");
			cooldown[3] = 3;
			passive = 0;
			ARSystem.giveBuff(player, new Stun(player), 60);
			ARSystem.giveBuff(player, new Nodamage(player), 60);
		} else {
			skill("c30_s3");
			ARSystem.giveBuff(player, new Stun(player), 60);
			ARSystem.giveBuff(player, new Nodamage(player), 60);
		}
		return true;
	}
	
	
	public void sp() {
		s_score += 1000;
		for(Player p: Rule.c.keySet()) {
			Rule.c.get(p).PlayerSpCast(player);
		}
		ARSystem.giveBuff(player, new TimeStop(player), 540);

		ARSystem.playSoundAll("c30sp");
		skill("c30_sp_e");
		delay(new Runnable() {
			@Override
			public void run() {
				player.performCommand("tm anitext all SUBTITLE true 40 c30:t1/"+player.getName());
			}
		},0);
		delay(new Runnable() {
			@Override
			public void run() {
				player.performCommand("tm anitext all SUBTITLE true 60 c30:t2/"+player.getName());
			}
		},40);
		delay(new Runnable() {
			@Override
			public void run() {
				player.performCommand("tm anitext all SUBTITLE true 80 c30:t3/"+player.getName());
			}
		},100);
		delay(new Runnable() {
			@Override
			public void run() {
				player.performCommand("tm anitext all SUBTITLE true 100 c30:t4/"+player.getName());
			}
		},180);
		
		delay(new Runnable() {
			@Override
			public void run() {
				player.performCommand("tm anitext all SUBTITLE true 120 c30:t5/"+player.getName());
			}
		},260);
		delay(new Runnable() {
			@Override
			public void run() {
				player.performCommand("tm anitext all SUBTITLE true 60 c30:t6/"+player.getName());
			}
		},380);
		delay(new Runnable() {
			@Override
			public void run() {
				player.performCommand("tm anitext all SUBTITLE true 100 c30:t7/"+player.getName());
			}
		},440);
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
					player.performCommand("tm anitext all TITLE true 20 c30:sk0/c30:t8");
					isps = true;
					for(Player p : Bukkit.getOnlinePlayers()) {
						AdvManager.set(p, 399, 0 , "§f§l"+player.getName() +"§a("+ n +") §f§l" + Main.GetText("c"+number+":sk0"));
						Map.getMapinfo(1001);
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
					Rule.buffmanager.selectBuffAddValue(player, "plushp",30);
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
				for(int i = 0; i < 33; i++) {
					Location loc = Map.randomLoc();
					loc.setY((float) (loc.getY() - 0.8 + (AMath.random(8)*0.1)));
					ARSystem.spellLocCast(player, loc, "c30_spimg_"+AMath.random(5));
				}
			}
		},550);
		if(ARSystem.gameMode2) {
			delay(new Runnable() {
				@Override
				public void run() {
					for(int i = 0; i < 33; i++) {
						Location loc = Map.randomLoc();
						loc.setY((float) (loc.getY() - 0.8 + (AMath.random(8)*0.1)));
						ARSystem.spellLocCast(player, loc, "c30_spimg_"+AMath.random(5));
					}
				}
			},560);
			delay(new Runnable() {
				@Override
				public void run() {
					for(int i = 0; i < 33; i++) {
						Location loc = Map.randomLoc();
						loc.setY((float) (loc.getY() - 0.8 + (AMath.random(8)*0.1)));
						ARSystem.spellLocCast(player, loc, "c30_spimg_"+AMath.random(5));
					}
				}
			},570);
		}
	}

	@Override
	public boolean tick() {
		if(tk%20==0) {
			scoreBoardText.add("&c ["+Main.GetText("c30:ps")+ "]&f : "+ passive + "/ 3");
			if(s_score + s_damage + s_kill == 200 && ARSystem.gameMode != modes.ONE) {
				time+= (skillmult + sskillmult);
				if(!ARSystem.gameMode2) time+= (skillmult + sskillmult);
				if(psopen) {
					scoreBoardText.add("&c ["+Main.GetText("c30:sk0")+ "]&f");
					scoreBoardText.add("&f : "+ time + " / 100");
				}
				if(time >= 100 && !sp) {
					time = 0;
					sp();
				}
			}
		}
		if(sp) {
			timer++;
			if(timer >= 10 && ARSystem.gameMode2) {
				skill("c30_sp");
				for(int i = 0; i < 2; i++) {
					Location loc = Map.randomLoc();
					loc.setY((float) (loc.getY() - 0.8 + (AMath.random(8)*0.1)));
					ARSystem.spellLocCast(player, loc, "c30_spimg_"+AMath.random(5));
				}
				timer = 0;
			}
			if(timer >= 40 && !ARSystem.gameMode2) {
				skill("c30_sp");
				for(int i = 0; i < 2; i++) {
					Location loc = Map.randomLoc();
					loc.setY((float) (loc.getY() - 0.8 + (AMath.random(8)*0.1)));
					ARSystem.spellLocCast(player, loc, "c30_spimg_"+AMath.random(5));
				}
				timer = 0;
			}
		}
		return true;
	}

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			
		} else {
			if(isps) {
				if(Rule.c.get(e.getDamager()).number == 60 && AMath.random(100) <= 75) {
					e.setCancelled(true);
					e.setDamage(0);
					if(AMath.random(10) <= 5) {
						skill("c30_sp");
						for(int i = 0; i < 2; i++) {
							Location loc = Map.randomLoc();
							loc.setY((float) (loc.getY() - 0.8 + (AMath.random(8)*0.1)));
							ARSystem.spellLocCast(player, loc, "c30_spimg_"+AMath.random(5));
						}
					}
				}
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
				if(Rule.c.get(e) instanceof c86iriya) {
					is = "ir";
					break;
				}
			}
		}
		
		if(is.equals("ir")) {
			ARSystem.playSound((Entity)player, "c30iriya");
		} else {
			ARSystem.playSound((Entity)player, "c30db");
		}
		
		return true;
	}
}

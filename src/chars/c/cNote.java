package chars.c;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.events.SpellTargetEvent;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.TimeStop;
import chars.c2.c62shinon;
import event.Skill;
import manager.Bgm;
import manager.MusicGame;
import types.box;
import util.AMath;
import util.GetChar;
import util.Holo;
import util.MSUtil;
import util.MagicSpellVar;
import util.ULocal;

public class cNote extends c00main{
	int nextTick = 0;
	String txt = "";
	String bgm = "";
	boolean start = false;
	public boolean noInput = false;
	public boolean isPlayer = false;
	public boolean isMove = false;
	public MusicGame music;
	
	boolean metaton = false;
	public Player duet;
	public Location startloc = player.getLocation();
	public cNote(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 100003;
		load();
		text();
		ARSystem.playSound(player, "humendb1");
		music = new MusicGame(player);
	}
	
	@Override
	public boolean skill9(){
		ARSystem.playSound((Entity)player, "humendb"+(AMath.random(5)+1));
		return true;
	}
	
	@Override
	public boolean key(PlayerItemHeldEvent e) {
		int n = e.getNewSlot()+1;
		if(music.isPlay()) {
			if(isMove) {
				
			} else {
				music.key(n);
			}
		} else {
			if(start) {
				if(n != 8) {
					e.setCancelled(true);
					player.getInventory().setHeldItemSlot(7);
					skill("music_ip"+n);
					txt += nextTick+":"+n+";";
					nextTick = 0;
				}
			}
		}
		return super.key(e);
	}
	
	@Override
	public boolean key_f() {
		if(!noInput) {
			if(!start) {
				nextTick = 0;
				txt = "";
				Bgm.setForceBgm(bgm);
			} else {
				Bgm.setForceBgm("no");
			}
			start = !start;
		}
		return super.key_f();
	}
	
	@Override
	public boolean tick() {
		if(music.isPlay()) {
			if(isMove && player.getLocation().distance(startloc) > 0.001) {
				double range = player.getLocation().distance(ULocal.offset(startloc.clone(), new Vector(0,0,-0.001)));
				int type = 1;
				double range2 = player.getLocation().distance(ULocal.offset(startloc.clone(), new Vector(0.001,0,0)));
				if(range > range2) {
					range = range2;
					type = 2;
				}
				range2 = player.getLocation().distance(ULocal.offset(startloc.clone(), new Vector(0,0,0.001)));
				if(range > range2) {
					range = range2;
					type = 3;
				}
				range2 = player.getLocation().distance(ULocal.offset(startloc.clone(), new Vector(-0.001,0,0)));
				if(range > range2) {
					range = range2;
					type = 4;
				}
				range2 = player.getLocation().distance(startloc.clone());
				if(range > range2) {
					range = range2;
					type = 0;
				}
				if(type!=0) {
					music.key(type);
				}
				player.teleport(startloc);
			}
			music.tick();
		} else {
			if(isPlayer) {
				isPlayer = false;
				delay(()->{skill("removemyall");},100);
				if(music.isFullCombo()) {
					spskillon();
					spskillen();
				} else {
					if(duet != null) {
						cNote tg = (cNote)Rule.c.get(duet);
						for(Player p : Bukkit.getOnlinePlayers()) {
							p.sendTitle("§4§l【§6§l" + player.getName()+ "§4§l】 §e§lVS §4§l【§6§l" +duet.getName()+"§4§l】","§f"+ music.score() + "  §f§l|§f  " + tg.music.score(),20,40,100);
						}
						tpsdelay(()->{Rule.c.get(duet).skill("removemyall");},100);
						Rule.c.put(duet, new c000humen(duet, plugin, null));
						duet = null;
					} else {
						for(Player p : Bukkit.getOnlinePlayers()) {
							delay(()->{p.sendTitle("", "§c§lScore : " + music.score(),40,60,20);},10);
						}
					}
				}
			}
		}
		
		if(start) {
			nextTick++;
			player.sendTitle("§c§l< §e§l" + nextTick+ "§c§l >", "",0,10,0);
		}
		return super.tick();
	}
	
	@Override
	public boolean chat(PlayerChatEvent e) {
		if(e.getPlayer() == player) {
			if(e.getMessage().contains("-play")) {
				Location loc = player.getLocation().clone();
				loc.setPitch(0);
				loc = ULocal.offset(loc, new Vector(8,0,0));
				skill("removemyall");
				delayEvent.clear();
				
				if(duet != null) {
					loc = ULocal.offset(loc, new Vector(0,0,-3));
					Rule.c.put(duet, new cNote(duet,plugin,null));
					cNote tg = (cNote)Rule.c.get(duet);
					tg.music.Start(ULocal.offset(loc, new Vector(0,0,6)), txt, bgm);
					if(metaton) ARSystem.spellLocCast(duet,ULocal.offset(loc, new Vector(0,0,6)),"metaton_sp");
					tg.isPlayer = true;
					tg.noInput = true;
					duet.teleport(ULocal.offset(loc, new Vector(-5,0,6)));
				}
				player.teleport(ULocal.offset(loc, new Vector(-5,0,0)));
				music.Start(loc, txt, bgm);
				if(metaton) ARSystem.spellLocCast(player,loc,"metaton_sp");
				startloc = player.getLocation();
				isPlayer = true;
			} else if(e.getMessage().contains("-bgm")) {
				bgm = e.getMessage().replace("-bgm", "").replace(" ", "");
			} else if(e.getMessage().contains("-load")) {
				String load = e.getMessage().replace("-load", "").replace(" ", "");
				Bukkit.broadcastMessage("load Note : " + load);
				
				txt = (String)Rule.Var.Load(load);
				bgm = (String)Rule.Var.Load(load+"=bgm");

			} else if(e.getMessage().contains("-save")) {
				String save = e.getMessage().replace("-save", "").replace(" ", "");
				Rule.Var.Save(save, txt);
				Rule.Var.Save(save+"=bgm", bgm);
				Bukkit.broadcastMessage("Save Note : " + save);
				
			} else if(e.getMessage().contains("-info")) {
				Bukkit.broadcastMessage("Load : " + txt);
				Bukkit.broadcastMessage("Bgm : " + bgm);
				
			} else if(e.getMessage().contains("-speed")) {
				music.speed = !music.speed;
				Bukkit.broadcastMessage("Speed x2 : " + music.speed);
			} else if(e.getMessage().contains("-metaton")) {
				metaton = !metaton;
				Bukkit.broadcastMessage("Spawn metaton : " + metaton);
			} else if(e.getMessage().contains("-move")) {
				isMove = !isMove;
				Bukkit.broadcastMessage("Move Key : " + isMove);
			} else if(e.getMessage().contains("-pvp")) {
				duet = Bukkit.getPlayer(e.getMessage().replace("-pvp", "").replace(" ", ""));
				if(duet == player) {
					duet = null;
				}
				if(duet != null) {
					for(Player p : Bukkit.getOnlinePlayers()) {
						p.sendTitle("", "§4§l【§6§l" + player.getName()+ "§4§l】 §e§lVS §4§l【§6§l" +duet.getName()+"§4§l】",20,20,100);
					}
					Bukkit.broadcastMessage("§4§l【§6§l" + player.getName()+ "§4§l】 §e§lVS §4§l【§6§l" +duet.getName()+"§4§l】");
				}
			}
		}
		return super.chat(e);
	}
}

package chars.ch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.events.SpellTargetEvent;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Buff;
import buff.MapVoid;
import buff.MindControl;
import buff.Noattack;
import buff.Nodamage;
import buff.PowerUp;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c000humen;
import chars.c.c00main;
import chars.c.cNote;
import event.Skill;
import event.WinEvent;
import manager.AdvManager;
import manager.Bgm;
import manager.MusicGame;
import types.BuffType;
import types.MapType;
import types.box;

import util.AMath;
import util.Holo;
import util.MSUtil;
import util.Map;
import util.NpcPlayer;
import util.Text;
import util.ULocal;

public class h008metaton extends c00main{
	Location loc;
	Entity s1;
	MusicGame music;
	Player duet;
	Location startloc = null;
	boolean isPlayer = false;
	String txt;
	String bgm;
	public h008metaton(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 992;
		load();
		text();
		ARSystem.playSoundAll("metatonselect");
		txt = Text.get("c992:sp");
		bgm = Text.get("c992:spbgm");
	}
	
	@Override
	public boolean skill1() {
		if(isPlayer) {
			cooldown[1] = 0;
			return true;
		}
		s1 = null;
		skill("metatoncansel");
		skill("metatons1");
		delay(()->{
			skill("metatons1a");
		},10);
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(isPlayer) {
			cooldown[2] = 0;
			return true;
		}
		skill("metatons2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(isPlayer) {
			cooldown[3] = 0;
			return true;
		}
		for(int i = 0; i<3; i++) skill("metatons3");
		return true;
	}
	
	public boolean sp() {
		for(Entity e : ARSystem.box(player, new Vector(999,999,999), box.MYALL)) {
			LivingEntity p = (LivingEntity)e;
			ARSystem.giveBuff(p, new TimeStop(p),400);
			if(e == player) ARSystem.removeItemAll((Player)p);
		}
		ARSystem.removeItemAll(player);
		ARSystem.giveBuff(player, new TimeStop(player),400);
		
		ARSystem.playSoundAll("0butten1");
		delay(()->{
			NpcPlayer.npc(player.getLocation()).performCommand("tm sound all metatons1");
			NpcPlayer.npc(player.getLocation()).performCommand("tm anitext all SUBTITLE false 10 "+Text.get("c992:t1")+"/["+Text.get("c992:name2")+"]");
		},20);
		delay(()->{
			NpcPlayer.npc(player.getLocation()).performCommand("tm sound all metatons1");
			NpcPlayer.npc(player.getLocation()).performCommand("tm anitext all SUBTITLE false 15 "+Text.get("c992:t2")+"/["+Text.get("c992:name2")+"]");
		},35);
		delay(()->{
			NpcPlayer.npc(player.getLocation()).performCommand("tm sound all metatons1");
			NpcPlayer.npc(player.getLocation()).performCommand("tm anitext all SUBTITLE false 25 "+Text.get("c992:t3")+"/["+Text.get("c992:name2")+"]");
		},60);
		delay(()->{
			ARSystem.giveBuff(player, new TimeStop(player),0);
			ARSystem.potion(player, 14, 4000, 1);
			music = new MusicGame(player);
			Entity target = ARSystem.boxSPlayerOne(player, new Vector(999,999,999), box.ALL);
			if(target != null) duet = (Player)target;
			Map.getMapinfo(1011);
			Location loc = ULocal.offset(Map.getCenter(),new Vector(5,0,0));
			loc.setY(4);
			loc.setYaw(0);
			loc.setPitch(0);
			for(Player p : Bukkit.getOnlinePlayers()) {
				p.teleport(loc);
			}
	
			loc.setPitch(0);
			loc = ULocal.offset(loc, new Vector(8,0,0));
			skill("removemyall");
			delayEvent.clear();
			
			if(duet != null) {
				loc = ULocal.offset(loc, new Vector(0,0,-3));
				Rule.c.put(duet, new cNote(duet,plugin,null));
				cNote tg = (cNote)Rule.c.get(duet);
				tg.music.Start(ULocal.offset(loc, new Vector(0,0,6)), txt, bgm);
				tg.isPlayer = true;
				duet.teleport(ULocal.offset(loc, new Vector(-5,0,6)));
				tg.noInput = true;
				tg.startloc = duet.getLocation();
			}
			for(Player p : Bukkit.getOnlinePlayers()) {
				p.hidePlayer(player);
			}
			this.loc = ULocal.offset(loc, new Vector(-3,1,0));
			player.teleport(ULocal.offset(loc, new Vector(-10,3,2)));
			music.Start(loc, txt, bgm);
			ARSystem.spellLocCast(player,loc,"metaton_sp");
			startloc = player.getLocation();
			startloc.setYaw(0);
			startloc.setPitch(20);
			isPlayer = true;
			player.sendMessage("§4§l※ §c§l"+Text.get("c992:t4"));
			duet.sendMessage("§4§l※ §c§l"+Text.get("c992:t4"));
		},100);
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			s1 = target;
		}
	}
	
	@Override
	public void LocmakerSkill(Location loc, String name) {
		if(name.equals("metagarp") && s1 != null) {
			s1.teleport(loc.clone().add(0,1,0));
			s1.setVelocity(new Vector(0,0.04,0));
			LivingEntity tg = (LivingEntity)s1;
			ARSystem.giveBuff(tg, new Nodamage(tg), 2);
		}
	}
	
	@Override
	public boolean tick() {
		if(tk%20 == 0) {
			String s = Bgm.bgmcode;
			if(!(s.equals("bc992")||s.equals("bc1992") ) && Bgm.rep) {
				Bgm.setlockBgm("c992");
			}
		}
		if(player.isSneaking()) {
			skill("metatoncansel");
			if(s1 != null) {
				s1.setVelocity(player.getLocation().getDirection().multiply(2));
				ARSystem.playSound(s1, "0boom2", 1.5f ,0.5f);
				LivingEntity tg = (LivingEntity)s1;
				s1 = null;
				delay(()->{
					tg.setNoDamageTicks(0);
					tg.damage(4,player);
				},3);
			}
		}
		if(isPlayer && music.isPlay()) {
			if(player.getLocation().distance(startloc) > 0.001) {
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
				if(duet != null) {
					cNote tg = (cNote)Rule.c.get(duet);
					for(Player p : Bukkit.getOnlinePlayers()) {
						p.sendTitle("§4§l【§6§l" + player.getName()+ "§4§l】 §e§lVS §4§l【§6§l" +duet.getName()+"§4§l】","§f"+ music.score() + "  §f§l|§f  " + tg.music.score(),20,40,100);
					}
					tpsdelay(()->{ARSystem.spellCast(duet,"removemyall");},100);
					Rule.c.put(duet, new c000humen(duet, plugin, null));
					delay(()->{
						if(music.score() >= tg.music.score()) {
							Skill.win(player);
							tpsdelay(()->{
								ARSystem.playSoundAll("metatonsp1");
							},100);
						} else {
							Skill.win(duet);
							tpsdelay(()->{
								ARSystem.playSoundAll("metatonselect");
							},100);
						}
					},20);
				} else {
					for(Player p : Bukkit.getOnlinePlayers()) {
						delay(()->{p.sendTitle("", "§c§lScore : " + music.score(),40,60,20);},10);
					}
				}
			}
			
		}
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(p == player) Bgm.bgmlock = false;
		super.PlayerDeath(p, e);
	}
	boolean isback(Location target, Location attaker,float size) {
		Location lc = target.clone();
		Location plc = attaker.clone();
		lc.setPitch(0);
		plc.setPitch(0);
		float targetFaceAngle = lc.clone().getDirection().angle(new Vector(1, 1, 1));
		float diffAngle = lc.toVector().subtract(plc.toVector()).angle(new Vector(1, 1, 1));
		float diff = Math.abs(targetFaceAngle - diffAngle);
		if(diff <= size) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			LivingEntity tg = (LivingEntity) e.getEntity();
			e.setDamage(e.getDamage() * (tg.getHealth()/tg.getMaxHealth()));
		} else {
			e.setDamage(e.getDamage()*0.2f);
			if(Rule.c.size() <= 2 && isback(player.getLocation(),e.getDamager().getLocation(), 0.4f) && AMath.random(10) == 1 &&!isps) {
				spskillon();
				spskillen();
				sp();
			}
		}
		return true;
	}
	
	@Override
	public boolean skill9(){
		ARSystem.playSound((Entity)player, "metatondb");
		return true;
	}
	
	@Override
	public boolean chat(PlayerChatEvent e) {
		if(e.getPlayer() == player) {
			 if(e.getMessage().contains("-load")) {
				String load = e.getMessage().replace("-load", "").replace(" ", "");
				Bukkit.broadcastMessage("load Note : " + load);
				
				txt = (String)Rule.Var.Load(load);
				bgm = (String)Rule.Var.Load(load+"=bgm");

			} else if(e.getMessage().contains("-speed")) {
				music.speed = !music.speed;
				Bukkit.broadcastMessage("Speed x2 : " + music.speed);
			}
		}
		if(Bgm.bgmcode.equals("bc1992") && isPlayer) {
			Holo.create(ULocal.offset(loc.clone() ,new Vector((AMath.random(20)*0.2)-2,(AMath.random(20)*0.2)-2,-3)), "§f"+e.getMessage(), 100 + AMath.random(100),new Vector(-(10+AMath.random(40))*0.003,0,0));
			e.setCancelled(true);
			e.setMessage("");
			return false;
		}
		return super.chat(e);
	}

}

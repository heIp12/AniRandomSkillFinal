package chars.ca;

import java.util.ArrayList;
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
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.events.SpellTargetEvent;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Airborne;
import buff.Fascination;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.PowerUp;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c000humen;
import chars.c.c00main;
import event.Skill;
import manager.Bgm;
import types.box;

import util.AMath;
import util.ULocal;
import util.MSUtil;
import util.MagicSpellVar;
import util.Map;
import util.NpcPlayer;
import util.Text;

public class c1000gay extends c00main{
	double cooldowns = skillmult;
	public Player frist = null;
	
	public c1000gay(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1000;
		load();
		text();
		ARSystem.playSound(player, "c1000db");
		Bgm.setForceBgm("gay");
	}

	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c1000s1");
		ARSystem.potion(player, 1, 20, 10);
		ARSystem.potion(player, 8, 20, 10);
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound(player, "c1000db");
		for(Entity e : ARSystem.box(player, new Vector(12,12,12), box.TARGET)) {
			LivingEntity en = (LivingEntity)e;
			ARSystem.addBuff(en, new Fascination(en,player), 100, 0.2);
		}
		return true;
	}
	
	public void sp() {
		spskillon();
		spskillen();
		Map.getMapinfo(1011);
		Location center = Map.getCenter();
		center.setY(4);
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(p != player && Rule.c.get(p) != null) Rule.c.put(p,new c000humen(p, plugin, null));
			ARSystem.giveBuff(p, new Airborne(p), 400);
			ARSystem.giveBuff(p, new TimeStop(p), 400);
			for(Player pl : Bukkit.getOnlinePlayers()) {
				p.hidePlayer(pl);
			}
		}
		ARSystem.spellLocCast(player, center.clone(), "gay");
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.teleport(ULocal.lookAt(ULocal.offset(center.clone(), new Vector(5,0,0)),center.clone()));
			delay(()->{
				ARSystem.spellLocCast(p, center, "ratio40");
			},60);
		}
		for(int i = 0; i<39; i++) {
			delay(()->{
				ARSystem.spellLocCast(player, center, "gayheart");
			},60+i);
		}
		delay(()->{
			ARSystem.playSoundAll("c1000sp");
		},40);
		delay(()->{
			for(Player p : Bukkit.getOnlinePlayers()) {
				ARSystem.potion(player, 15, 20, 1);
				ARSystem.potion(player, 16, 20, 1);
			}
		},98);
		delay(()->{
			Map.getMapinfo(1014);
			NpcPlayer.npc(startLoc).performCommand("as gdespawn gay");
			NpcPlayer.npc(startLoc).performCommand("as gspawn gay");
			String txt = Text.get("c1000:move");
			int t = 0;
			for(String s : txt.split(";")) {
				String[] l = s.split(",");
				Location lc = player.getLocation().clone();
				lc.setX(Double.parseDouble(l[0]));
				lc.setY(Double.parseDouble(l[1]));
				lc.setZ(Double.parseDouble(l[2]));
				lc.setYaw(Float.parseFloat(l[3]));
				lc.setPitch(Float.parseFloat(l[4]));
				Location tloc = lc.clone();
				for(int i = 0; i < Integer.parseInt(l[5]);i++) {
					delay(()->{
						for(Player p : ARSystem.getPlayers()) {
							p.teleport(tloc);
						}
					},t++);
				}
			}
			delay(()->{
				ARSystem.playSoundAll("c1000sp");
				delay(()->{
					ARSystem.playSoundAll("c1000ang");
				},15);
				delay(()->{
					ARSystem.playSoundAll("c1000ang");
				},25);
				delay(()->{
					ARSystem.playSoundAll("c1000ang");
				},35);
				for(Player p : Bukkit.getOnlinePlayers()) {
					ARSystem.potion(player, 15, 100, 1);
					ARSystem.potion(player, 16, 100, 1);
				}
			},160);
			delay(()->{
				Skill.win(player);
				tpsdelay(()->{
					for(Player p : Bukkit.getOnlinePlayers()) {
						p.sendTitle("§c§l《§e§lGay Over§c§l》", "",20,60,60);
					}
					ARSystem.playSoundAll("c1000db");
				},40);
			},200);
		},99);
	}
	
	
	
	@Override
	public boolean tick() {
		if(tk%20 == 0 && frist == player) {
			boolean sp = true;
			for(Player p : Rule.c.keySet()) {
				if(Rule.c.get(p).number != 1000) sp = false;
			}
			if(sp) {
				sp();
			}
		}
		return true;
	}
	
	public void cool() {
		skillmult = cooldowns + cooldowns*(3-((player.getHealth()/player.getMaxHealth())*3));
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			double damage = (1-player.getHealth()/player.getMaxHealth())*2;
			e.setDamage(e.getDamage() + e.getDamage()*damage);
			
			LivingEntity en = (LivingEntity)e.getEntity();
			if(Rule.c.get(e.getEntity()) != null) {
				if(Rule.c.get(e.getEntity()).number == 1000) {
					e.setDamage(e.getDamage() + 4);
					en.teleport(ULocal.offset(player.getLocation(), new Vector(-1.5,0,0)));
					ARSystem.giveBuff(player, new PowerUp(player), 100, 1);
				} else {
					String s = Main.GetText("c"+Rule.c.get(en).getCode()+":tag");
					if(s.indexOf("tg2") != -1) {
						ARSystem.heal(player, 2);
						if(en.getHealth() / en.getMaxHealth() <= 0.5f) {
							ARSystem.giveBuff(en, new TimeStop(en), 100);
							ARSystem.heal(player, 100);
							for(int i=0;i<10;i++) {
								delay(()->{ARSystem.playSound((Entity)e.getEntity(), "c1000ang");},10*i);
							}
							delay(()->{
								if(frist == null) frist = player;
								Rule.c.put(((Player)e.getEntity()), new c1000gay(((Player)e.getEntity()), plugin, null));
								((c1000gay)Rule.c.get(e.getEntity())).frist = frist;
							},100);
						} else {
							ARSystem.addBuff(en, new Stun(en), 2);
							ARSystem.giveBuff(en, new Silence(en), 10);
							ARSystem.addBuff(en, new Panic(en), 4);
							delay(()->{en.setNoDamageTicks(0);},0);
							player.sendTitle(en.getHealth() +" "+ en.getMaxHealth(), "boy가 약해지고 있어!",0,20,0);
						}
						return false;
					} else {
						e.setDamage(e.getDamage()*1.5f);
						ARSystem.addBuff(en, new Panic(en), 40);
						e.getEntity().setVelocity(player.getLocation().getDirection().multiply(2.5));
					}
				}
			}
			cool();
			ARSystem.playSound((Entity)player, "c1000a"+AMath.random(4),1,2);
		} else {
			e.setDamage(e.getDamage()*0.3f);
			if(e.getDamage() > 15) {
				ARSystem.giveBuff(player, new Nodamage(player), 40);
				e.setDamage(15);
			}
			cool();
			ARSystem.playSound((Entity)player, "c1000p"+AMath.random(3),1,2);
		}
		return true;
	}
}

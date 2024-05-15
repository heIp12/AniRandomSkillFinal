package chars.ca;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.ConsoleCommandSender;
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
import com.nisovin.magicspells.events.SpellTargetEvent;

import Main.Main;
import aliveblock.ABlock;
import ars.ARSystem;
import ars.Rule;
import buff.Buff;
import buff.Cindaella;
import buff.Curse;
import buff.Exposure;
import buff.Fascination;
import buff.NoHeal;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import buff.Timeshock;
import buff.Wound;
import chars.c.c000humen;
import chars.c.c00main;
import chars.c.c10bell;
import chars.c.c30siro;
import chars.c2.c60gil;
import event.Skill;
import event.WinEvent;
import manager.AdvManager;
import manager.Bgm;
import types.BuffType;
import types.box;

import util.AMath;
import util.BlockUtil;
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.ULocal;
import util.MSUtil;
import util.Map;
import util.NpcPlayer;
import util.Text;

public class c1394matan extends c00main{
	int tan = 0;
	
	Location loc;
	int sp = 0;
	boolean mt = true;
	double hp = 0;
	
	public c1394matan(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 4139;
		load();
		text();
		c = this;
		if(ch != null) {
			ccset(ch);
		}
		ARSystem.playSoundAll("c1139select");
		skill("c1139_p");
		tpsdelay(()->{
			if(player.getMaxHealth() > 50) {
				setcooldown[3] *= 0.3f;
			} else {
				ARSystem.giveBuff(player, new NoHeal(player), 200000);
			}
		},20);

		Rule.playerinfo.get(player).tropy(139, 3);
	}
	
	@Override
	public boolean skill1() {
		Location l = player.getLocation();
		l.setPitch(0);
		player.teleport(l);
		
		if(tan == 6) {
			spskillon();
			spskillen();
			ARSystem.giveBuff(player, new TimeStop(player), 60);
			ARSystem.giveBuff(player, new Stun(player), 40);
			ARSystem.giveBuff(player, new Silence(player), 40);
			
			sp++;
			mt = true;
			ARSystem.playSound((Entity)player, "c1139s12");
			if(sp <= 2) {
				ARSystem.playSound((Entity)player, "c1139sp"+sp);
			} else {
				ARSystem.playSound((Entity)player, "c139sp"+(sp-2));
			}
			skill("c1139_s1e");
			tan = 0;
			
			delay(()->{
				ARSystem.playSound((Entity)player, "c1139s1");
				Location lc = player.getLocation();
				lc = ULocal.offset(lc, new Vector(8,0,0));
				Location lcc = lc;
				delay(()->{
					ARSystem.spellLocCast(player, ULocal.lookAt(lcc, player.getLocation()), "c1139_s1e2");
					ARSystem.playSound((Entity)player, "c1139s12");
					delay(()->{
						hpCost(sp*15, true);
					},20);
				},20);
			},40);
			return true;
		}
		float rag = 1;
		if(player.getMaxHealth() > 50) rag*=2;
		List<Entity> t = ARSystem.PlayerBeamBox(player, 60*rag, 5*rag, box.TARGET);
		if(t.size() > 0) {
			ARSystem.giveBuff(player, new Stun(player), 40);
			ARSystem.giveBuff(player, new Silence(player), 40);
			ARSystem.addBuff(player, new Nodamage(player), 20);
			tan++;
			ARSystem.playSound((Entity)player, "c1139s12");
			skill("c1139_s1e");
			delay(()->{
				ARSystem.giveBuff((LivingEntity)t.get(0), new Stun((LivingEntity)t.get(0)), 20);
				ARSystem.playSound((Entity)player, "c1139s1");
				mt(t.get(0));
			},20);
		} else {
			cooldown[1] = 0;
		}
		return true;
	}
	
	void mt(Entity taget){
		delay(()->{
			Location loc = taget.getLocation();
			for(Entity e : ARSystem.box(taget, new Vector(12,12,12), box.TARGET)){
				if(e != taget && e != player) {
					loc = e.getLocation();
					break;
				}
			}
			ARSystem.spellLocCast(player, ULocal.offset(ULocal.lookAt(taget.getLocation().clone(), loc),new Vector(-3,0,0)), "c1139_s1e2");
			ARSystem.playSound((Entity)taget, "c1139s12");
		},20);
	}
	
	@Override
	public boolean skill2() {
		if(tan < 6) {
			if(mt == false) return false;
			mt = false;

			Location l = player.getLocation();
			l.setPitch(0);
			player.teleport(l);
			
			float rag = 1;
			if(player.getMaxHealth() > 50) rag*=2;
			
			List<Entity> t = ARSystem.PlayerBeamBox(player, 80*rag, 20*rag, box.TARGET);
			if(t.size() > 0) {
				ARSystem.playSound((Entity)player, "c1139s12");
				ARSystem.giveBuff(player, new TimeStop(player), 40);
				ARSystem.giveBuff(player, new Stun(player), 40);
				ARSystem.giveBuff(player, new Silence(player), 40);
				ARSystem.addBuff(player, new Nodamage(player), 20);
				skill("c1139_s1e3");
				tan++;
				int c = 0;
				for(Entity en : t) {
					ARSystem.addBuff(player, new Nodamage(player), 5);
					ARSystem.giveBuff((LivingEntity)en, new Stun((LivingEntity)en), 40 + (5*t.size()));
					c++;
					delay(()->{
						ARSystem.playSound((Entity)player, "c1139s1");
						mt(en);
					},40 + (c++)*5);
				}
				delay(()->{
					Location lc = player.getLocation();
					lc = ULocal.offset(lc, new Vector(8,0,0));
					Location lcc = lc;
					ARSystem.spellLocCast(player, ULocal.lookAt(lcc, player.getLocation()), "c1139_s1e2");
					delay(()->{
						makerSkill(player,"1");
					},20);
				},40);
			} else {
				cooldown[2] = 0;
			}
		} else {
			skill1();
		}
		return true;
	}
	

	@Override
	public boolean skill3() {
		loc = player.getLocation();
		ARSystem.playSound((Entity)player, "c1139s31");
		ARSystem.playSound(player, "c1139s31");
		Location lc = player.getLocation();
		lc.setPitch(lc.getPitch() - 10);
		if(player.getMaxHealth() > 50) hp = player.getHealth();
		for(int i =0; i<21; i++) {
			lc = ULocal.offset(lc.clone(), new Vector(1,0,0));
			if(!BlockUtil.isPathable(lc.getBlock())) {
				lc = ULocal.offset(lc.clone(), new Vector(-1,0,0));
				break;
			}
		}
		MSUtil.buffoff(player, "c1139_p");
		player.removePassenger(player.getPassenger());
		player.teleport(lc);

		return true;
	}
	
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			if(tan == 1) {
				for(int i =0; i<8;i++) {
					delay(()->{
						target.setNoDamageTicks(0);
						target.damage(1,player);
						if(target == player) hpCost(1, true);
					},i*2);
				}
			} else if(tan == 2) {
				for(Buff buff : Rule.buffmanager.getBuffs(target).getBuff()) {
					buff.stop();
				}
				delay(()->{
					target.setNoDamageTicks(0);
					target.damage(8,player);
					if(target == player) hpCost(8, true);
				},0);
			} else if(tan == 3) {
				ARSystem.giveBuff(target, new Fascination(target,player), 140, 0.1f);
				target.setNoDamageTicks(0);
				target.damage(2,player);
				if(target == player) hpCost(2, true);
			} else if(tan == 4) {
				if(player.getMaxHealth() > 50) {
					ARSystem.fixedDamage(target, player, 20);
				} else {
					ARSystem.fixedDamage(target, player, 10);
				}
			} else if(tan == 5) {
				ARSystem.giveBuff(target, new Silence(target), 100);
				target.setNoDamageTicks(0);
				target.damage(5,player);
				if(target == player) hpCost(5, true);
			} else if(tan == 6) {
				target.setNoDamageTicks(0);
				target.damage(1,player);
				if(target == player) hpCost(1, true);
				if(player.getMaxHealth() > 50) {
					ARSystem.giveBuff(target, new Exposure(target) , 30000, 9);
				} else {
					ARSystem.giveBuff(target, new Exposure(target) , 600, 5);
				}
			}
			delay(()->{
				if(target == player) {
					Wound w = new Wound(NpcPlayer.player);
					w.setValue(1);
					w.setEffect("c1139_pe");
					w.setDelay(player,40,0);
					ARSystem.giveBuff(target, w, 160);
				} else {
					Wound w = new Wound(target);
					w.setEffect("c1139_pe");
					w.setValue(1);
					if(player.getMaxHealth() > 50) {
						w.setDelay(player,20,0);
						ARSystem.giveBuff(target, w, 200);
					} else {
						w.setValue(1);
						w.setDelay(player,40,0);
						ARSystem.giveBuff(target, w, 160);
					}
					
				}
			},0);
		}
	}
	
	
	@Override
	public boolean tick() {
		if(!MSUtil.isbuff(player, "c1139_p")) {
			skill("c1139_p");
		}
		if(loc != null && player.isSneaking()) {
			ARSystem.playSound((Entity)player, "c1139s32");
			ARSystem.playSound(player, "c1139s32");

			if(player.getMaxHealth() > 50 && hp > player.getHealth()) player.setHealth(hp);
			MSUtil.buffoff(player, "c1139_p");
			player.removePassenger(player.getPassenger());
			player.teleport(loc);
			loc = null;
		}
		
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c4139:ps")+ "] : &f" + Text.get("c4139:t"+(tan+1)) +" 탄환");
		}
		return true;
	}

	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(player.getMaxHealth() > 50) {
				e.setDamage(e.getDamage()*1.5f);
			}
			
		} else {
			if(player.getMaxHealth() > 50) {
				e.setDamage(e.getDamage()*0.6f);
			}
		}
		return true;
	}
	
	@Override
	protected boolean skill9() {
		ARSystem.playSound((Entity)player, "c1139db");
		return true;
	}
	
	@Override
	public String getBgm() {
		if(player.getMaxHealth() > 50) return "m3";
		return "m2";
	}
}

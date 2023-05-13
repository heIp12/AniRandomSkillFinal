package chars.c3;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
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
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.mysql.fabric.xmlrpc.base.Data;
import com.nisovin.magicspells.MagicSpells;
import com.nisovin.magicspells.events.SpellTargetEvent;

import Main.Main;
import aliveblock.ABlock;
import ars.ARSystem;
import ars.Rule;
import buff.Barrier;
import buff.Buff;
import buff.Cindaella;
import buff.Curse;
import buff.NoHeal;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.PowerUp;
import buff.Rampage;
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
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.ULocal;
import util.MSUtil;
import util.Map;
import util.Text;

public class c115stunk extends c00main{
	int s1 = 0;
	int s2 = 0;
	Location loc = null;
	int sp = 0;
	LivingEntity sps = null;
	
	int p1 = 0;
	String chating = "";
	
	int spp = 0;
	
	public c115stunk(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 115;
		load();
		text();
		c = this;
		LocalTime now = LocalTime.now();
		if(now.getHour() > 0 && now.getHour() < 7) {
			spskillon();
			spskillen();
			spp = 6974;
			Rule.playerinfo.get(player).tropy(115, 1);
		}
	}

	@Override
	public void setStack(float f) {
		spp = (int)f;
	}
	
	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c115s1");
		skill("c115_s1");
		s1 = AMath.random(3);
		loc = player.getLocation();
		player.setVelocity(player.getLocation().getDirection().multiply(2).setY(0));
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(s2 <= 0) {
			cooldown[2] = 0;
			return true;
		}
		ARSystem.playSound((Entity)player, "c115s2");
		ARSystem.spellLocCast(player, loc, "c115_s2b");
		skill("c115_s2");
		return true;
	}

	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c115s3");
		ARSystem.giveBuff(player, new Rampage(player), 160);
		ARSystem.giveBuff(player, new Nodamage(player), 40);
		ARSystem.giveBuff(player, new PowerUp(player), 160, 1);
		ARSystem.heal(player, 12);
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			ARSystem.giveBuff(target, new Stun(target), 20);
			s2 = 20;
			for(int i =0; i<s1; i++) {
				delay(()->{
					ARSystem.playSound(target, "0katana",0.6f);
					ARSystem.spellCast(player, target, "c115e");
				},i*4);
			}
			int s0 = s1;
			delay(()->{
				for(int i = 0; i < s0; i++) {
					target.setNoDamageTicks(0);
					target.damage(3,player);
					ARSystem.playSound(target, "0katana6",0.5f);
				}
			},20);
		}
		
		if(n.equals("2") && spp == 6974) {
			boolean targetz = false;
			if(!(target instanceof Player)) targetz = true;
			
			if(Rule.c.get(target) != null) {
				String s = Main.GetText("c"+Rule.c.get(target).getCode()+":tag");
				if(s.indexOf("tg1") != -1 && s.indexOf("tg3") == -1) targetz = true;
			}
			
			if(targetz && skillCooldown(0)) {
				ARSystem.giveBuff(target, new Nodamage(target), 400);
				ARSystem.giveBuff(player, new Nodamage(player), 400);
				ARSystem.giveBuff(target, new Stun(target), 400);
				ARSystem.giveBuff(player, new Stun(player), 400);
				ARSystem.giveBuff(target, new Silence(target), 400);
				ARSystem.giveBuff(player, new Silence(player), 400);
				int time = 20;
				if(!(target instanceof Player)) {
					time = 80;
					ARSystem.playSound((Entity)player, "c115sp");
					ARSystem.giveBuff(player, new TimeStop(player), 100);
				} else {
					((Player)target).sendTitle(Text.get("c115:p2"),Text.get("c115:p3"));
				}
				
				delay(()->{
					ARSystem.spellLocCast(player, target.getLocation(), "c115_s2b");
				}, time - 20);
				
				delay(()->{
					player.sendTitle(Text.get("c115:p1"),"");
					ARSystem.giveBuff(target, new Nodamage(target), 200);
					ARSystem.giveBuff(player, new Nodamage(player), 200);
					ARSystem.giveBuff(target, new Stun(target), 200);
					ARSystem.giveBuff(player, new Stun(player), 200);
					ARSystem.giveBuff(target, new Silence(target), 200);
					ARSystem.giveBuff(player, new Silence(player), 200);
					ARSystem.potion(target, 14, 200, 1);
					ARSystem.potion(player, 14, 200, 1);
					skill("c115_sp");
					ARSystem.spellLocCast(player, player.getLocation().clone().add(0,1,0), "c115_info");
					ARSystem.spellLocCast(player, player.getLocation().clone().add(0,0.8,0), "c115_info2");
					sps = target;
					p1 = 0;
				},time);
				sp = 200+time;
			}
		}
	}
	
	@Override
	public boolean tick() {
		if(tk%20 == 0 && s2 > 0) scoreBoardText.add("&c ["+Main.GetText("c115:sk2")+ "]");
		if(s2 > 0) s2--;
		if(sp > 0) {
			sp--;
			if(sps instanceof Player) {
				if(((Player) sps).isSneaking()) {
					((Player) sps).setSneaking(false);
					ARSystem.playSound(sps, "0attack3",1.3f);
					p1++;
				}
			} else {
				if(AMath.random(3) <= 1) p1++;
			}
			if(sp == 0) {
				player.performCommand("tm sound all 0gun/2.0/0.5");
				if(chating.length() > 20) chating = chating.substring(0,20);
				int time = chating.length()*2;
				if(time > 200) time = 200;
				int damage = 70 - p1;
				if(damage > 0) {
					Holo.create(sps.getLocation(), ""+damage,200,new Vector(0,0.01,0));
					if(sps.getHealth()-damage < 1) {
						sps.setHealth(1);
					} else {
						sps.setHealth(sps.getHealth() - damage);
						ARSystem.giveBuff(sps, new Stun(sps), 200);
						ARSystem.giveBuff(sps, new Silence(sps), 200);
					}
				}
				player.performCommand("tm anitext all SUBTITLE true "+time+" "+ chating +"/[" + player.getName()+"]");
				delay(()->{
					int start = p1 / 20;
					String s = "★";
					for(;start>0;start--) s+="★";
					player.performCommand("tm sound all minecraft:entity.experience_orb.pickup/2/0.2");
					player.performCommand("tm anitext all TITLE true "+(20+start*20)+" &e"+ s +"/&a" + sps.getName()+"");
					delay(()->{player.performCommand("tm sound all no");},start*20);
				},time+20);
			}
		}
		return true;
	}
	
	@Override
	public boolean chat(PlayerChatEvent e) {
		if(sp > 0) {
			if(e.getPlayer() == player) {
				chating = e.getMessage();
				e.setCancelled(true);
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*3);
			makerSkill((LivingEntity) e.getEntity(), "2");
		} else {

		}
		return true;
	}
}

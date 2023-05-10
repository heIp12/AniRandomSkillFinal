package chars.c2;

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
import org.bukkit.event.player.PlayerChatEvent;
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
import buff.Iiari;
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
import chars.c.c11yasuo;
import chars.c.c12conan;
import chars.c.c18misogi;
import chars.c.c20kurumi;
import chars.c.c22byakuya;
import chars.c.c25Accelerator;
import chars.c.c31ichigo;
import chars.c.c35miruk;
import chars.c.c37subaru;
import chars.c.c40megumin;
import chars.ca.c1800misogi;
import chars.ca.c2000kurumi;
import event.Skill;
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

public class c78ruichi extends c00main{
	Player target = null;
	String chat = null;
	String tc = null;
	
	public c78ruichi(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 78;
		load();
		text();
		c = this;
	}

	@Override
	public boolean skill1() {
		for(Entity e : ARSystem.box(player, new Vector(20,20,20), box.TARGET)) {
			ARSystem.giveBuff((LivingEntity) e, new Iiari((LivingEntity) e, player), 60);
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSoundAll("c78s2");
		for(Entity e : ARSystem.box(player, new Vector(999,999,999), box.TARGET)) {
			ARSystem.giveBuff((LivingEntity) e, new Noattack((LivingEntity) e), 80);
			ARSystem.spellLocCast(player, e.getLocation().add(0,1,0), "c78_e2");
		}
		return true;
	}
	
	@Override
	public boolean chat(PlayerChatEvent e) {
		if(chat != null) {
			if(chat == "p" && e.getPlayer() == player) {
				player.performCommand("tm anitext all SUBTITLE true 20 "+e.getMessage()+"/" + e.getPlayer().getName()+"["+Main.GetText("c78:o3")+"]");
			}
			if(chat == "t" && e.getPlayer() == target) {
				player.performCommand("tm sound all h");
				player.performCommand("tm anitext all SUBTITLE true 20 "+e.getMessage()+"/" + e.getPlayer().getName()+"["+Main.GetText("c78:o4")+"]");
				tc = e.getMessage();
			}
			if(e.getPlayer() == target || e.getPlayer() == player) {
				return false;
			}
		}
		return true;
	}
	
	public void sp(Player target) {
		Rule.playerinfo.get(player).tropy(78,1);
		this.target = target;
		Location loc = target.getLocation();
		loc.setPitch(0);
		target.teleport(loc);
		spskillen();
		spskillon();
		tc = null;
		ARSystem.spellLocCast(player, target.getLocation().add(0,1,0), "c78_sp");
		ARSystem.giveBuff(player, new TimeStop(player), 700);
		ARSystem.giveBuff(target, new TimeStop(target), 700);
		
		loc = ULocal.offset(target.getLocation(), new Vector(2,0.5,-3));
		loc.setYaw(loc.getYaw()+90);
		loc.setPitch(0);
		player.teleport(loc);
		player.performCommand("tm sound all minecraft:block.stone.break");
		player.performCommand("tm anitext all SUBTITLE true 40 "+Main.GetText("c78:t1")+"/["+Main.GetText("c78:o2")+"]");
		chat = "npc";
		int id = Rule.c.get(target).getCode()%1000;
		delay(()->{ 
			player.performCommand("tm sound all 0attack");
			if(Rule.c.get(target).getCode() == 1118) {
				player.performCommand("tm anitext all SUBTITLE true 40 "+Main.GetText("c78:t2-1118")+"/["+ player.getName()+"["+Main.GetText("c78:o3")+"]");
			}
			else if(Main.GetText("c78:t2-"+id) != null) {
				player.performCommand("tm anitext all SUBTITLE true 40 "+Main.GetText("c78:t2-"+id)+"/["+ player.getName()+"["+Main.GetText("c78:o3")+"]");
			} else {
				player.performCommand("tm anitext all SUBTITLE true 40 "+Main.GetText("c78:t2")+"/["+ player.getName()+"["+Main.GetText("c78:o3")+"]");
			}
		},80);
		if(Rule.c.get(target).getCode() != 1118) {
			delay(()->{ 
				player.performCommand("tm sound all 0tc");
				player.performCommand("tm anitext all SUBTITLE true 30 "+Main.GetText("c78:t3")+"/["+Main.GetText("c78:o1")+"]");
			},160);
			delay(()->{ 
				player.performCommand("tm sound all 0tc");
				player.performCommand("tm anitext all SUBTITLE true 20 "+ player.getName()+Main.GetText("c78:t4")+"/["+Main.GetText("c78:o1")+"]");
			},220);
			delay(()->{ 
				player.performCommand("tm sound all 0attack");
				player.performCommand("tm anitext all SUBTITLE true 40 "+Main.GetText("c78:t5")+"/"+ player.getName()+"["+Main.GetText("c78:o3")+"]");
			},260);
			delay(()->{ 
				player.performCommand("tm sound all minecraft:block.stone.break");
				player.performCommand("tm anitext all SUBTITLE true 40 "+Main.GetText("c78:t6")+"/["+Main.GetText("c78:o2")+"]");
			},340);
			delay(()->{ 
				chat = "t";
			},420);
			delay(()->{ 
				chat = null;
				if(tc != null) {
					ARSystem.playSoundAll("c78s1");
					Location locs = ULocal.offset(player.getLocation(), new Vector(1,1.4,-2));
					locs.setYaw(locs.getYaw()+45);
					ARSystem.spellLocCast(player, locs, "c78_e1");
					player.performCommand("tm sound all 0attack");
					player.performCommand("tm anitext all SUBTITLE true 10 "+ Main.GetText("c78:sk1")+"/["+Main.GetText("c78:o3")+"]");
				}
			},480);
	
			delay(()->{ 
				if(tc != null) {
					player.performCommand("tm sound all minecraft:block.stone.break");
					player.performCommand("tm anitext all SUBTITLE true 40 "+Main.GetText("c78:t8")+"/["+ Main.GetText("c78:o2")+"]");
				} else {
					player.performCommand("tm sound all minecraft:block.stone.break");
					player.performCommand("tm anitext all SUBTITLE true 40 "+Main.GetText("c78:t-1")+"/["+ Main.GetText("c78:o2")+"]");
				}
			},500);
			delay(()->{ 
				if(tc != null) {
					ARSystem.playSoundAll("c78win");
					player.performCommand("tm sound all 0attack");
					player.performCommand("tm anitext all SUBTITLE true 60 "+Main.GetText("c78:t9")+"/["+ player.getName()+"["+Main.GetText("c78:o3")+"]");
				} else {
					player.performCommand("tm sound all minecraft:block.stone.break");
					player.performCommand("tm anitext all SUBTITLE true 40 "+Main.GetText("c78:t-2")+"/["+ Main.GetText("c78:o2")+"]");
				}
			},540);
			delay(()->{ 
				if(id == 83) {
					player.performCommand("tm sound all h");
					player.performCommand("tm anitext all SUBTITLE true 10 "+Main.GetText("c78:t7")+"/" + target.getName()+"["+Main.GetText("c78:o4")+"]");
					Location locs = ULocal.offset(player.getLocation(), new Vector(1,1.4,-2));
					locs.setYaw(locs.getYaw()+45);
					ARSystem.spellLocCast(player, locs, "c78_e1");
					ARSystem.playSoundAll("c83ruichi");
				} else {
					player.performCommand("tm sound all minecraft:block.stone.break");
					player.performCommand("tm anitext all SUBTITLE true 40 "+Main.GetText("c78:t10")+"/["+ Main.GetText("c78:o2")+"]");
					ARSystem.playSoundAll("c78last");
				}
			},620);
			
			delay(()->{
				if(id == 83) {
					player.performCommand("tm sound all h");
					player.performCommand("tm anitext all SUBTITLE true 40 "+Main.GetText("c78:t9-1")+"/" + target.getName()+"["+Main.GetText("c78:o4")+"]");
				} else {
					boolean win = true;
					if(tc == null) {
						win = true;
					} else if(AMath.random(Rule.c.get(target).s_kill+2) == 1){
						win = false;
					}
					player.performCommand("tm sound all minecraft:block.stone.break");
					if(win) {
						player.performCommand("tm anitext all SUBTITLE false 40 "+Main.GetText("c78:t11")+"/["+Main.GetText("c78:o2")+"]");
						delay(()->{ this.target.setHealth(0); },40);
					} else {
						player.performCommand("tm anitext all SUBTITLE false 40 "+Main.GetText("c78:t12")+"/["+Main.GetText("c78:o2")+"]");
					}
				}
			},680);
			delay(()->{ 
				player.performCommand("tm sound all no");
				ARSystem.playSoundAll("c78end");
			},720);
			delay(()->{ chat = null; },720);
		} else {
			delay(()->{ 
				skill("removemyall");
				player.performCommand("tm sound all no");
				ARSystem.playSoundAll("c78end");
				ARSystem.giveBuff(player, new TimeStop(player), 0);
				ARSystem.giveBuff(target, new TimeStop(target), 0);
				chat = null;
			},160);
		}
	}
	
	@Override
	public boolean tick() {
		
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(Rule.c.get(e.getEntity()) != null) {
				int id = Rule.c.get(e.getEntity()).getCode()%1000;
				if( Main.GetText("c78:t2-"+id) != null || Rule.c.get(e.getEntity()).s_kill >= 3) {
					if(skillCooldown(0)) {
						sp((Player) e.getEntity());
					}
				}
			}
		} else {
			if(Rule.c.get(e.getDamager()) != null && Rule.c.get(e.getDamager()).s_kill > 0) {
				for(int i=0; i<Rule.c.get(e.getDamager()).s_kill;i++) {
					e.setDamage(e.getDamage()*0.5);
				}
			}
		}
		return true;
	}
	
}

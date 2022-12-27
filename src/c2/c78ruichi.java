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
import c.c000humen;
import c.c00main;
import c.c11yasuo;
import c.c12conan;
import c.c18misogi;
import c.c20kurumi;
import c.c22byakuya;
import c.c25Accelerator;
import c.c31ichigo;
import c.c35miruk;
import c.c37subaru;
import c.c40megumin;
import ca.c1800misogi;
import ca.c2000kurumi;
import event.Skill;
import manager.AdvManager;
import manager.Bgm;
import manager.Holo;
import types.BuffType;
import types.box;
import util.AMath;
import util.GetChar;
import util.InvSkill;
import util.Inventory;
import util.Local;
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
	
	public void sp(Player target,int id) {
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
		
		loc = Local.offset(target.getLocation(), new Vector(2,0.5,-3));
		loc.setYaw(loc.getYaw()+90);
		loc.setPitch(0);
		player.teleport(loc);
		player.performCommand("tm sound all minecraft:block.stone.break");
		player.performCommand("tm anitext all SUBTITLE true 40 "+Main.GetText("c78:t1")+"/["+Main.GetText("c78:o2")+"]");
		chat = "npc";
		delay(()->{ 
			player.performCommand("tm sound all 0attack");
			if(id==0) player.performCommand("tm anitext all SUBTITLE true 40 "+Main.GetText("c78:t2")+"/["+ player.getName()+"["+Main.GetText("c78:o3")+"]");
			if(id==12) player.performCommand("tm anitext all SUBTITLE true 40 "+Main.GetText("c78:t2-1")+"/["+ player.getName()+"["+Main.GetText("c78:o3")+"]");
			if(id==18) player.performCommand("tm anitext all SUBTITLE true 40 "+Main.GetText("c78:t2-2")+"/["+ player.getName()+"["+Main.GetText("c78:o3")+"]");
			if(id==20) player.performCommand("tm anitext all SUBTITLE true 40 "+Main.GetText("c78:t2-3")+"/["+ player.getName()+"["+Main.GetText("c78:o3")+"]");
			if(id==25) player.performCommand("tm anitext all SUBTITLE true 40 "+Main.GetText("c78:t2-4")+"/["+ player.getName()+"["+Main.GetText("c78:o3")+"]");
			if(id==52) player.performCommand("tm anitext all SUBTITLE true 40 "+Main.GetText("c78:t2-5")+"/["+ player.getName()+"["+Main.GetText("c78:o3")+"]");
			if(id==54) player.performCommand("tm anitext all SUBTITLE true 40 "+Main.GetText("c78:t2-6")+"/["+ player.getName()+"["+Main.GetText("c78:o3")+"]");
			if(id==64) player.performCommand("tm anitext all SUBTITLE true 40 "+Main.GetText("c78:t2-7")+"/["+ player.getName()+"["+Main.GetText("c78:o3")+"]");
			if(id==70) player.performCommand("tm anitext all SUBTITLE true 40 "+Main.GetText("c78:t2-8")+"/["+ player.getName()+"["+Main.GetText("c78:o3")+"]");
			if(id==71) player.performCommand("tm anitext all SUBTITLE true 40 "+Main.GetText("c78:t2-9")+"/["+ player.getName()+"["+Main.GetText("c78:o3")+"]");
			if(id==79) player.performCommand("tm anitext all SUBTITLE true 40 "+Main.GetText("c78:t2-10")+"/["+ player.getName()+"["+Main.GetText("c78:o3")+"]");
			if(id==35) player.performCommand("tm anitext all SUBTITLE true 40 "+Main.GetText("c78:t2-11")+"/["+ player.getName()+"["+Main.GetText("c78:o3")+"]");
			
		},80);
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
				Location locs = Local.offset(player.getLocation(), new Vector(1,1.4,-2));
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
			player.performCommand("tm sound all minecraft:block.stone.break");
			player.performCommand("tm anitext all SUBTITLE true 40 "+Main.GetText("c78:t10")+"/["+ Main.GetText("c78:o2")+"]");
			ARSystem.playSoundAll("c78last");
		},620);
		
		delay(()->{ 
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
		},680);
		delay(()->{ 
			player.performCommand("tm sound all no");
			ARSystem.playSoundAll("c78end");
		},720);
		delay(()->{ chat = null; },720);
	}
	
	@Override
	public boolean tick() {
		
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(Rule.c.get(e.getEntity()) != null) {
				int target = 0;
				if(Rule.c.get(e.getEntity()) instanceof c12conan) target = 12;
				if(Rule.c.get(e.getEntity()) instanceof c22byakuya) target = 12;
				if(Rule.c.get(e.getEntity()) instanceof c31ichigo) target = 12;
				if(Rule.c.get(e.getEntity()) instanceof c18misogi) target = 18;
				if(Rule.c.get(e.getEntity()) instanceof c1800misogi) target = 18;
				if(Rule.c.get(e.getEntity()) instanceof c20kurumi) target = 20;
				if(Rule.c.get(e.getEntity()) instanceof c2000kurumi) target = 20;
				if(Rule.c.get(e.getEntity()) instanceof c25Accelerator) target = 25;
				if(Rule.c.get(e.getEntity()) instanceof c35miruk) target = 35;
				if(Rule.c.get(e.getEntity()) instanceof c37subaru) target = 54;
				if(Rule.c.get(e.getEntity()) instanceof c40megumin) target = 52;
				if(Rule.c.get(e.getEntity()) instanceof c52toby) target = 52;
				if(Rule.c.get(e.getEntity()) instanceof c53cabuto) target = 52;
				if(Rule.c.get(e.getEntity()) instanceof c54patel) target = 54;
				if(Rule.c.get(e.getEntity()) instanceof c64hisoka) target = 64;
				if(Rule.c.get(e.getEntity()) instanceof c70raito) target = 70;
				if(Rule.c.get(e.getEntity()) instanceof c71diabolo) target = 71;
				if(Rule.c.get(e.getEntity()) instanceof c79kate) target = 79;
				if(Rule.c.get(e.getEntity()) instanceof c83sora) target = 35;
				if(Rule.c.get(e.getEntity()) instanceof c89kazuma) target = 35;
				if(Rule.c.get(e.getEntity()) instanceof c90arabe) target = 35;
				if(Rule.c.get(e.getEntity()) instanceof c96iki) target = 64;
				if(Rule.c.get(e.getEntity()) instanceof c65cohina) target = 64;
				
				if( target != 0 || Rule.c.get(e.getEntity()).s_kill >= 3) {
					if(skillCooldown(0)) {
						sp((Player) e.getEntity(),target);
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

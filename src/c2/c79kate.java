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
import ars.TeamInfo;
import buff.Buff;
import buff.Cindaella;
import buff.Curse;
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
import event.Skill;
import manager.AdvManager;
import manager.Bgm;
import manager.Holo;
import types.BuffType;
import types.box;
import types.modes;
import util.AMath;
import util.GetChar;
import util.InvSkill;
import util.Inventory;
import util.Local;
import util.MSUtil;
import util.Map;

public class c79kate extends c00main{
	HashMap<LivingEntity,Float> attack;
	int sk3 = 0;
	
	public c79kate(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 79;
		load();
		text();
		c = this;
		Rule.team.teamRemove("Zvezda");
		Rule.team.teamCreate("Zvezda");
		Rule.team.getTeam("Zvezda").setTeamColor("e");
		Rule.team.getTeam("Zvezda").setTeamName("Zvezda");
		attack = new HashMap<>();
	}

	@Override
	public boolean skill1() {
		delay(()->{skill("c79_s1");},80);
		ARSystem.giveBuff(player, new Stun(player), 80);
		ARSystem.giveBuff(player, new Silence(player), 80);
		ARSystem.giveBuff(player, new Nodamage(player), 80);
		ARSystem.playSound((Entity)player, "c79s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c79s2");
		ARSystem.heal(player, 12);
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c79s3");
		sk3 = 100;
		return true;
	}
	
	@Override
	public boolean skill4() {
		skill("c71_s4");
		ARSystem.playSound((Entity)player, "c71s4");
		return true;
	}
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(p == player) {
			Rule.team.teamRemove("Zvezda");
		}
	}

	@Override
	public boolean tick() {
		if(Rule.team.getTeam("Zvezda") != null) {
			for(LivingEntity e : attack.keySet()) {
				if(e.getHealth() <= attack.get(e) && attack.get(e) < 10000) {
					attack.put(e,99999f);
					Rule.team.teamJoin("Zvezda", player);
					Rule.team.teamJoin("Zvezda", (Player)e);
					if(Rule.team.getTeam("Zvezda").getPlayer().size() != Rule.c.size()) {
						ARSystem.giveBuff(e, new TimeStop(e), 100);
						ARSystem.playSound((Player)e, "c79p");
						ARSystem.playSound(player, "c79p");
					}
					
				}
				if(((Player)e).getGameMode() != GameMode.ADVENTURE) {
					attack.put(e, -9999f);
				}
				if(psopen){
					for(Entity p : attack.keySet()) {
						if(attack.get(p) > 0 && attack.get(e) < 10000) {
							scoreBoardText.add("&c ["+Main.GetText("c79:ps")+ "] : "+p.getName() +":"+ attack.get(p));
						}
					}
				}
			}
			if(tk%20 == 0) {
				boolean allteam = true;
				for(Player p : Rule.c.keySet()) {
					if(Rule.team.getTeam("Zvezda").getPlayer().contains(p)) {
						
					} else {
						allteam = false;
					}
				}
				if(allteam && skillCooldown(0) && !isps) {
					Rule.playerinfo.get(player).tropy(79,1);
					player.performCommand("tm sound all minecraft:block.stone.break");
					spskillen();
					spskillon();
					score+=1000000;
					ARSystem.playSoundAll("c79sp");
					
					Rule.c.get(player).info();
					for(Player p :Rule.c.keySet()) {
						Rule.playerinfo.get(p).addcradit(Rule.c.size()*2,Main.GetText("main:msg103"));
					}
					
					int number = Rule.c.get(player).getCode();
					Rule.Var.addInt(player.getName()+".c"+(number%1000)+"Win",1);
					Rule.Var.addInt("ARSystem.c"+(number%1000)+"Win",1);
					
					for(Player p : Rule.c.keySet()) {
						ARSystem.giveBuff(p,new TimeStop(p), 100);
						delay(()->{
							player.performCommand("tm sound all no");
							player.performCommand("tm anitext all SUBTITLE true 60 c79:t1/"+player.getName());
						},40);
					}
					
					delay(()->{
						Rule.team.getTeam("Zvezda").setTeamWin(true);
						List<String> removelist = new ArrayList<>();
						for(TeamInfo t : Rule.team.getTeams()) {
							if(!t.getTeamName().equals("Zvezda")) {
								removelist.add(t.getTeamName());
							}
						}
						for(String t : removelist) {
							Rule.team.teamRemove(t);
						}
						ARSystem.gameMode = modes.TEAM;
						ARSystem.Stop();
					},120);
				}
			}
		}
		if(sk3 > 0) {
			sk3--;
		}
		return true;
	}
	
	@Override
	public boolean remove(Entity caster) {
		if(caster instanceof Player && Rule.team.isTeam(player, (Player)caster)) return false;
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(Rule.team.getTeam("Zvezda") != null) {
				if(e.getEntity() instanceof Player) {
					if(attack.get(e.getEntity()) == null) {
						attack.put((LivingEntity) e.getEntity(), (float) 0);
					}
					attack.put((LivingEntity) e.getEntity(), (float) (attack.get(e.getEntity()) + e.getDamage()));
					e.setDamage(0);
				}
			}
		} else {
			if(e.getDamager() instanceof Player && Rule.team.isTeam(player, (Player) e.getDamager())) e.setDamage(0);
			if(sk3 > 0) {
				if(e.getDamager() != player) {
					((LivingEntity)e.getDamager()).damage(e.getDamage(),player);
					e.setDamage(0);
					ARSystem.playSound((Entity)player, "0miss");
				}
			}
		}
		return true;
	}
	
}

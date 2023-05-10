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
import buff.Noattack;
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c00main;
import event.Skill;
import manager.AdvManager;
import types.box;
import util.AMath;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c53cabuto extends c00main{
	HashMap<Player, c00main> players;
	List<Player> death = new ArrayList<Player>();
	Player team = null;
	int count = 0;
	
	public c53cabuto(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 53;
		load();
		text();
	}
	

	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c53s1");
		skill("c53_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(team == null) {
			if(death.size() > 0) {
				Player target = null;
				boolean deathplayer = true;
				for(Player p : death) {
					if(Rule.c.containsKey(p)) {
						deathplayer = false;
					}
				}
				if(deathplayer) {
					for(int i= 0; i<300;i++) {
						int a = AMath.random(death.size())-1;
						if(!Rule.c.containsKey(death.get(a))) {
							target = death.get(a);
							i = 300;
						}
					}
				}
				if(target != null) {
					Rule.team.teamRemove("Cabuto");
					Rule.team.teamCreate("Cabuto");
					Rule.team.teamJoin("Cabuto", player);
					Rule.team.getTeam("Cabuto").setTeamColor("6");
					count++;
					if(count > 2) Rule.playerinfo.get(player).tropy(53,1);
					if(AMath.random(10) == 2 && skillCooldown(0)) {
						spskillon();
						spskillen();
						ARSystem.playSoundAll("c53sp");
						delay(()->{ARSystem.playSoundAll("c53s2");},40);
						target.teleport(player.getLocation().clone().add(player.getVelocity()));
						Rule.c.put(target, players.get(target));
						if(Rule.c.get(target) != null) {
							Rule.c.get(target).hp*=1.6;
							Rule.c.get(target).skillmult += 2;
							target.setGameMode(GameMode.ADVENTURE);
							target.setMaxHealth(Rule.c.get(target).hp);
							target.setHealth(Rule.c.get(target).hp);
							Rule.team.teamJoin("Cabuto", target);
							team = target;
						}
					} else {
						ARSystem.playSoundAll("c53s2");
						target.teleport(player.getLocation().clone().add(player.getVelocity()));
						Rule.c.put(target, players.get(target));
						if(Rule.c.get(target) != null) {
							Rule.c.get(target).hp*=0.6;
							target.setMaxHealth(Rule.c.get(target).hp);
							target.setHealth(Rule.c.get(target).hp);
							target.setGameMode(GameMode.ADVENTURE);
							Rule.team.teamJoin("Cabuto", target);
							team = target;
						}
					}
					return true;
				}
			}
		}
		cooldown[2] = 0;
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(team != null) {
			ARSystem.playSoundAll("c53s3");
			if(ARSystem.box(team, new Vector(8,6,8),box.TARGET).size() > 0) {
				for(Entity e : ARSystem.box(team, new Vector(8,6,8),box.TARGET)) {
					((LivingEntity)e).setNoDamageTicks(0);
					((LivingEntity)e).damage(14,player);
				}
			}
			Skill.death(team, team);
			Rule.team.teamRemove("Cabuto");
			team = null;
		} else {
			cooldown[3] = 0;
		}
		return true;
	}


	@Override
	public boolean tick() {
		if(players == null) {
			players = (HashMap<Player, c00main>) Rule.c.clone();
		}
		List<Player> Removes = new ArrayList<>();
		for(Player p : death) if(!Bukkit.getOnlinePlayers().contains(p)) Removes.add(p);
		for(Player p : Removes) death.remove(p);
		if(tk%20 == 0 && team != null) {
			scoreBoardText.add("&c ["+Main.GetText("c53:sk2")+ "] : "+ team.getName());
		}
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(team != null && p == team) team = null;
		
		death.add(p);
	}

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {

			
		} else {
			e.setDamage(e.getDamage() *0.8);
			if(AMath.random(10) == 2) {
				e.setDamage(0);
				ARSystem.playSound((Entity)player,"0miss");
			}
		}
		return true;
	}
}

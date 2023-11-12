package chars.c;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
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

import com.nisovin.magicspells.MagicSpells;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Nodamage;
import buff.Panic;
import chars.ca.c4300yuno;
import event.Skill;
import manager.AdvManager;
import types.box;
import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c43yuno extends c00main{
	int tick = 0;
	
	float co[] = new float[4];
	Player yuki = null;
	double heal = 0;
	int c = 0;
	boolean ps = true;
	double score = 0;
	boolean start = true;
	
	public c43yuno(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 43;
		load();
		text();
	}
	
	public void team() {
		for(int i = 0; i<4;i++) co[i] = setcooldown[i];
		if(Rule.team.getTeam("Yuno") != null) {
			Rule.c.put(player,new c4300yuno(player, plugin, null));
			return;
		}
		Rule.team.teamCreate("Yuno");
		Rule.team.teamJoin("Yuno", player);
		Rule.team.getTeam("Yuno").setTeamColor("d");
		invskill = new InvSkill(player) {
			
			@Override
			public void Start(String st) {
				player.closeInventory();
				if(Rule.c.get(Bukkit.getPlayer(st)) != null) {
					yuki = Bukkit.getPlayer(st);
				} else {
					yuki = ARSystem.RandomPlayer(player);
				}
				heal = yuki.getHealth();
				yuki.sendTitle(Main.GetText("c43:t1"), "",5,15,20);
				ARSystem.playSound(yuki, "c43p");
				ARSystem.playSound(player, "c43p");
				Rule.team.teamJoin("Yuno", yuki);
			}
		};
		

		delay(new Runnable() {
			@Override
			public void run() {
				Set<Player> Player = ((HashMap<Player, c00main>) Rule.c.clone()).keySet();
				Player.remove(player);
				Inventory.getlist(invskill,player,Player);
				invskill.openInventory(player);
			}
		},40);
		
		delay(new Runnable() {
			@Override
			public void run() {
				if(player.getOpenInventory().getTitle().equals(invskill.inventory.getTitle())) {
					player.closeInventory();
					if(yuki == null || yuki == player) {
						Rule.c.put(player,new c4300yuno(player, plugin, null));
					}
				}
			}
		},200);
	}

	@Override
	public boolean skill1() {
		if(yuki == null) {
			cooldown[1] = 0;
			return true;
		}
		player.teleport(yuki);
		tick = 160;
		skill("c43_s1");
		ARSystem.playSound((Player)player, "c43s");
		return true;
	}
	@Override
	public boolean skill2() {
		ARSystem.playSound((Player)player, "c43s2");
		for(int i=0;i<10;i++) {
			Rule.c.get(yuki).cooldown[i] -= 3;
			if(Rule.c.get(yuki).cooldown[i] < 0) Rule.c.get(yuki).cooldown[i] = 0;
		}
		ARSystem.heal(yuki, 10000);
		s_score +=yuki.getMaxHealth();
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(yuki != null && yuki.getGameMode() != GameMode.SPECTATOR) {
			ARSystem.playSound((Player)player, "c43s3");
			if(yuki != null) {
				for(Entity p : ARSystem.box(yuki, new Vector(8,8,8),box.TARGET)) {
					if(p != yuki && p != player) {
						ARSystem.spellCast(player, p, "c43_s2a");
					}
				}
			}
		} else {
			cooldown[3] = 0;
		}
		return true;
	}
	

	@Override
	public boolean tick() {
		if(start) {
			start = false;
			team();
		}
		if(yuki == null && ARSystem.AniRandomSkill != null && ARSystem.AniRandomSkill.time > 10) {
			Rule.c.put(player,new c4300yuno(player, plugin, null));
		}
		else if(yuki != null) {
			if(tk%20==0) {
				scoreBoardText.add("&c ["+Main.GetText("c43:ps")+ "] : "+ yuki.getName());
			}
			if(ps && yuki.getHealth() < heal) {
				ps = false;
				player.teleport(yuki);
				ARSystem.giveBuff(player, new Nodamage(player), 60);
				ARSystem.giveBuff(yuki, new Nodamage(yuki), 60);
			}
			if(yuki.getHealth() < heal && tick > 0) {
	
				player.damage(heal - yuki.getHealth());
				s_score +=(heal - yuki.getHealth())*5;
				if(heal < yuki.getMaxHealth()) {
					yuki.setHealth(heal);
				} else {
					yuki.setHealth(yuki.getMaxHealth());
				}
			}
			if(Rule.c.get(yuki) != null && score <= Rule.c.get(yuki).s_damage*1.8) {
				s_score +=((Rule.c.get(yuki).s_damage*1.8)-score)*10;
				score += (Rule.c.get(yuki).s_damage*1.8)-score;
			}
			heal = yuki.getHealth();
		}
		if(tick > 0) tick--;
		return true;
	}
	
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(p == yuki) {
			delay(new Runnable() {
				public void run() {
					ARSystem.Death(player,player);
				}
			}, 20);
		} else if(p != player && ARSystem.getPlayerCount() == 3) {
			spskillon();
			spskillen();
			if(AMath.random(100) <= 50 || Rule.c.get(yuki) instanceof c18misogi) {
				ARSystem.playSoundAll("c43sp");
				delay(new Runnable() {
					public void run() {
						Skill.remove(player, player);
					}
				}, 60);
			} else {
				Rule.playerinfo.get(player).tropy(43,1);
				ARSystem.playSoundAll("c43sp2");
				delay(new Runnable() {
					public void run() {
						Skill.remove(yuki, yuki);
					}
				}, 60);
			}
		}
	}

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {

		} else {
			if(tick > 0) {
				e.setDamage(e.getDamage()*0.5);
			}
		}
		return true;
	}
}

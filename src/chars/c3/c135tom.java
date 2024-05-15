package chars.c3;

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
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
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
import buff.Airborne;
import buff.Barrier;
import buff.Buff;
import buff.Cindaella;
import buff.Curse;
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
import chars.c.c09youmu;
import chars.c.c10bell;
import chars.c.c30siro;
import chars.c2.c60gil;
import event.Skill;
import event.WinEvent;
import manager.AdvManager;
import manager.Bgm;
import types.BuffType;
import types.TargetMap;
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

public class c135tom extends c00main{
	int zt = 600;
	int s2t = 0;
	int za = 0;
	Player zr;
	boolean p = false;

	int damage = 1;
	int dg = 0;
	
	public c135tom(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 135;
		load();
		text();
		c = this;
	}

	@Override
	public void setStack(float f) {

	}

	@Override
	public boolean skill1() {
		ARSystem.potion(player, 1, 10, 50);
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c135s2");
		for(Entity e : ARSystem.box(player, new Vector(8,4,8), box.TARGET)) {
			if(Rule.c.get(e) != null) {
				for(int i=0;i<10;i++) Rule.c.get(e).cooldown[i] +=3;
			}
		}
		player.setVelocity(new Vector(0,0.5,0));
		ARSystem.giveBuff(player, new Airborne(player), 20);
		s2t = 60;
		return true;
	}
	
	public boolean tick() {
		zt++;
		score = 200;
		s_score = 0;
		if(s2t > 0) {
			s2t--;
		}
		if(zt > 600) {
			zt = 0;
			if(Rule.c.size() >= 2) {
				if(zr != null) zr.sendTitle("","제리가 변경되었습니다.");
				zr = player;
				while(zr == player) zr = ARSystem.RandomPlayer();
				za = 0;
				zr.sendTitle("","당신은 제리입니다");
				player.sendTitle("New zery", zr.getName());
			}
		}
		if(tk%20 == 0) {
			if(zr != null) scoreBoardText.add("&e[제리] &f" + zr.getName()+ "&c["+ARSystem.getloc(zr.getLocation(),player.getLocation())+"]");
			scoreBoardText.add("&e["+Text.get("c135:sk0")+"] &f" + za);
			scoreBoardText.add("&e[Damage] &f" + damage);
		}
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			target.damage(77,player);
		}
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(e.getDamage() < 10) {
				if(e.getEntity() != zr) {
					e.setDamage(0);
					e.setCancelled(true);
					return false;
				} else {
					e.setDamage(damage);
					damage++;
				}
			}
		} else {
			dg+= e.getDamage();
			damage = 1;
			if(s2t > 0) {
				s2t = 0;
				cooldown[2] = 8;
			}
			if(e.getDamager() == zr) {
				za++;
				if(za >= 4 && skillCooldown(0)) {
					spskillon();
					spskillen();
					ARSystem.playSound((Entity)player, "c135sp");
					player.teleport(zr);
					LivingEntity z = (LivingEntity)zr;
					z.setNoDamageTicks(0);
					Bgm.setlockBgm("c135");
					ARSystem.giveBuff(z, new Stun(z), 300);
					skill("c135_sp");
					player.setGameMode(GameMode.SPECTATOR);
					delay(()->{
						player.setGameMode(GameMode.ADVENTURE);
						cooldown[0] = setcooldown[0];
					},300);

					e.setDamage(0);
					e.setCancelled(true);
					return false;
				}
				if(e.getDamage() >= 2) {
					e.setDamage(2);
					if(p == false) {
						p = true;
						delay(()->{
							ARSystem.giveBuff(player, new Stun(player), 40);
							ARSystem.giveBuff(player, new Nodamage(player), 60);
							p = false;
						},0);
						ARSystem.potion(player, 14, 40, 40);
						ARSystem.playSound((Entity)player, "c135p"+(AMath.random(2)+1));
						skill("c135_p"+AMath.random(5));
					} else {
						e.setDamage(0);
						e.setCancelled(true);
					}
				}
			}
			else if(e.getDamage() >= 1) {
				e.setDamage(1);
				if(p == false) {
					p = true;
					delay(()->{
						ARSystem.giveBuff(player, new Stun(player), 40);
						ARSystem.giveBuff(player, new Nodamage(player), 60);
						p = false;
					},0);
					ARSystem.potion(player, 14, 40, 40);
					ARSystem.playSound((Entity)player, "c135p"+(AMath.random(2)+1));
					skill("c135_p"+AMath.random(5));
				} else {
					e.setDamage(0);
					e.setCancelled(true);
				}
			}
			if(dg > 50 && player.getHealth() - e.getDamage() >= 1) {
				Rule.playerinfo.get(player).tropy(135, 1);
			}
		}
		return true;
	}

}

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

public class c92susu extends c00main{
	LivingEntity owner;
	boolean start = true;
	int psound = 0;
	int notick = 0;
	int sk1 = 0;
	int tick = 0;
	int maxtick = 600;
	List<LivingEntity> sk1tg;
	public c92susu(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 92;
		load();
		text();
		c = this;
		ARSystem.potion(player, 14, 100000, 1);
		if(ARSystem.isGameMode("lobotomy")) maxtick = 1800;
	}
	

	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c92s1");
		sk1 = 20;
		sk1tg = new ArrayList<>();
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c92s2");
		if(!(owner instanceof Player)) {
			owner.teleport(player);
		} else {
			player.teleport(owner);
		}
		skill("c92_attack");
		for(int i=0;i<3;i++) {
			delay(()->{
				for(Entity e : ARSystem.box(player, new Vector(3,3,3), box.TARGET)) {
					((LivingEntity)e).setNoDamageTicks(0);
					((LivingEntity)e).damage(3,player);
				}
			},5+i*10);
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(!(owner instanceof Player)) owner.teleport(player);
		ARSystem.playSound((Entity)player, "c92s3");
		ARSystem.heal(owner, 8);
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			ARSystem.giveBuff(target, new NoHeal(target), 400);
			for(int i = 0; i<3;i++) {
				delay(()->{
					target.setNoDamageTicks(0);
					target.damage(2,player);
				},3*i);
			}
		}

	}
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(p == owner){
			Skill.remove(player, player);
			ARSystem.Stop();
		}
	}
	@Override
	public boolean remove(Entity caster) {
		if(owner != null) return false;
		return true;
	}
	@Override
	public boolean tick() {
		inGame = true;
		if(!isps && AMath.random(5000) == 2) {
			spskillon();
			spskillen();
			for(int i=0; i<14;i++)skill("c92_sp");
		}
		if(start) {
			start = false;
			skill("c92");
		}
		if(sk1 > 0) {
			sk1--;
			player.setFallDistance(0);
			player.setVelocity(player.getLocation().getDirection().multiply(1.2));
			for(Entity e : ARSystem.box(player, new Vector(3,3,3), box.TARGET)) {
				if(!sk1tg.contains(e)) {
					sk1tg.add((LivingEntity) e);
					((LivingEntity)e).setNoDamageTicks(0);
					((LivingEntity)e).damage(2,player);
				}
			}
		}
	
		if(owner == null) {
			notick++;
			if(notick == 300) ARSystem.playSound(player, "c92p1");
			ARSystem.giveBuff(player, new Stun(player), 4);
			ARSystem.giveBuff(player, new Silence(player), 4);
			if(tk%10 == 0) {
				for(Entity e : ARSystem.box(player, new Vector(14,14,14), box.ALL)) {
					if(e.getLocation().distance(player.getLocation()) < 3) {
						ARSystem.playSound(player, "c92p3");
						owner = (LivingEntity)e;
						skill("c92_move");
						if(!(owner instanceof Player)) owner.setMaxHealth(owner.getMaxHealth() + 200);
						owner.setMaxHealth(owner.getMaxHealth()*1.6);
						owner.setHealth(owner.getMaxHealth());
						if(Rule.c.get(owner) != null) {
							for(int i=0;i<10;i++) Rule.c.get(owner).setcooldown[i] *= 0.6;
						}
					} else {
						if(psound <= 0) {
							psound = 100;
							ARSystem.playSound((Entity)e, "c92p2");
						}
					}
				}
			}
		} else {
			if(owner.getLocation().distance(player.getLocation()) > 20) {
				player.teleport(owner);
			}
			if((owner instanceof Player)) {
				if(Rule.c.get(owner) == null) {
					owner = null;
				}
			} else {
				if(owner.isDead() || !owner.isValid()) {
					owner = null;
				}
			}
			if(owner == null) Skill.remove(player, player);
			tick++;
			if(tick > maxtick) {
				Skill.death(player,player);
			}
		}
		if(psound > 0) psound--;
		if(tk%20 == 0 && owner != null) {
			scoreBoardText.add("&c ["+Main.GetText("c92:ps")+"] : life:" + AMath.round((maxtick-tick)*0.05, 2));
			scoreBoardText.add("&c ["+Main.GetText("c92:ps")+"] : range:" + AMath.round((owner.getLocation().distance(player.getLocation())), 2) + " / 20");
		}
		return true;
	}
	
	@Override
	public boolean damage(EntityDamageEvent e) {
		if(owner != null) {
			e.setDamage(0);
			e.setCancelled(true);
			return false;
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*3);
			if(e.getEntity() == owner && Rule.c.size() > 2) {
				e.setDamage(0);
				e.setCancelled(true);
			} else {
				tick = 0;
			}
		} else {
			if(owner != null) {
				e.setDamage(0);
				e.setCancelled(true);
			} else if(player.getHealth() - e.getDamage() <= 1) {
				Rule.playerinfo.get(player).tropy(92,1);
			}
		}
		return true;
	}
}

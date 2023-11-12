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
import buff.Cindaella;
import buff.Curse;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import buff.Wound;
import chars.c.c00main;
import event.Skill;
import manager.AdvManager;
import types.box;

import util.AMath;
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.ULocal;
import util.MSUtil;
import util.Map;

public class c6600akame extends c00main{
	Location playerloc;
	Location loc2;
	int sk3 = 0;
	int count = 0;
	
	public c6600akame(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1066;
		load();
		text();
		c = this;
		ARSystem.playSound(player, "c66select");
	}
	
	public void cuers(LivingEntity e) {
		Curse cs = new Curse(e);
		cs.setCaster(player);

		ARSystem.giveBuff(e, cs,60,0.1);
		cs = new Curse(e);
		cs.setCaster(player);
		ARSystem.giveBuff(e, cs,120,0.1);
		cs = new Curse(e);
		cs.setCaster(player);
		ARSystem.giveBuff(e, cs,180,0.1);
	}

	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c66s1");
		playerloc = player.getLocation();
		skill("c1066_s1");
		player.setVelocity(player.getLocation().getDirection().multiply(2));
		delay(()->{
			for(Entity e : ARSystem.box(player, new Vector(3,3,3), box.TARGET)) {
				((LivingEntity)e).setNoDamageTicks(0);
				((LivingEntity)e).damage(4,player);
				cuers((LivingEntity) e);
				player.setVelocity(new Vector(0,0,0));
				if(player.isSneaking()) {
					ARSystem.heal(player, 3);
				} else {
					if(sk3 > 0) {
						ARSystem.spellLocCast(player, player.getLocation().add(0,1,0).add(player.getLocation().getDirection().multiply(0.5)), "c1066_s3");
						ARSystem.spellLocCast(player, player.getLocation().add(0,1,0).add(player.getLocation().getDirection().multiply(1)), "c1066_s3");
						ARSystem.spellLocCast(player, player.getLocation().add(0,1,0).add(player.getLocation().getDirection().multiply(1.5)), "c1066_s3");
						ARSystem.spellLocCast(player, player.getLocation().add(0,1,0).add(player.getLocation().getDirection().multiply(2)), "c1066_s3");
						ARSystem.spellLocCast(player, player.getLocation().add(0,1,0).add(player.getLocation().getDirection().multiply(2.5)), "c1066_s3");
						ARSystem.spellLocCast(player, player.getLocation().add(0,1,0).add(player.getLocation().getDirection().multiply(3)), "c1066_s3");
					}
					player.teleport(playerloc);
				}
			}
		},5);

		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c66s2");
		for(int i =0; i<6;i++) {
			int j = i;
			delay(()->{
				if(j%2 == 1) skill("c1066_s2-"+((j+1)/2));
				for(Entity e : ARSystem.box(player, new Vector(8,3,8), box.TARGET)) {
					((LivingEntity)e).setNoDamageTicks(0);
					((LivingEntity)e).damage(1,player);
					cuers((LivingEntity) e);
				}
			},i*2);
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		loc2 = player.getLocation();
		ARSystem.playSound((Entity)player, "c66sp");
		sk3 = 120;
		return true;
	}

	@Override
	public boolean tick() {
		if(sk3>0)sk3--;
		if(sk3 > 0 && loc2.distance(player.getLocation()) > 0.5) {
			skill("c1066_s3");
			loc2 = player.getLocation();
		}
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(s_kill > 3) {
			Rule.playerinfo.get(player).tropy(66,1);
		}
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			target.damage(1,player);
			Curse cs = new Curse(target);
			cs.setCaster(player);
			ARSystem.giveBuff(target, cs,60,0.1);
		}
	}

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			
		} else {
			
		}
		return true;
	}
	
}

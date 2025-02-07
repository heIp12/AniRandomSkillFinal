package chars.c4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import org.bukkit.event.player.PlayerItemHeldEvent;
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
import buff.Boom;
import buff.Buff;
import buff.Cindaella;
import buff.Curse;
import buff.Fascination;
import buff.NoHeal;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.Rampage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import buff.Timeshock;
import buff.Wound;
import buffs.BuffBase;
import chars.c.c000humen;
import chars.c.c00main;
import event.Skill;
import io.lumine.xikage.mythicmobs.util.BlockUtil;
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

public class c159sakamoto extends c00main{
	int p = 0;
	int p2 = 0;
	List<Entity> s3list = new ArrayList<Entity>();
	int s3 = 0;
	Location s3lc;
	Entity spe;
	int sp = 0;
	int sp2 = 0;
	Location spl;

	public c159sakamoto(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 159;
		load();
		text();
		c = this;
	}

	@Override
	public boolean skill1() {
		if(sp > 0) {
			cooldown[1] = 0;
			return false;
		}
		Location l = player.getLocation().clone();
		l.setPitch(0);
		if(!BlockUtil.isPathable(ULocal.offset(l.clone(), new Vector(1,0,0)).getBlock())) {
			player.setVelocity(player.getLocation().getDirection().multiply(0.4).setY(0.7));
		} else {
			player.setVelocity(player.getLocation().getDirection().multiply(0.4).setY(-1.5));
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(sp > 0) {
			cooldown[2] = 0;
			return false;
		}
		List<Entity> e = ARSystem.PlayerBeamBox(player, 7, 1, box.TARGET);
		if(e.size() > 0) {
			ARSystem.playSound((Entity)player, "c159s2");
			ARSystem.giveBuff(player,new Stun(player), 40);
			ARSystem.giveBuff(player,new Silence(player), 40);
			Location lc = e.get(0).getLocation();
			lc = ULocal.offset(lc.clone(), new Vector(0,0,-2));
			lc = ULocal.lookAt(lc.clone(), e.get(0).getLocation());
			player.teleport(lc);
			ARSystem.giveBuff((LivingEntity)e.get(0),new Stun((LivingEntity)e.get(0)), 60);
			ARSystem.giveBuff((LivingEntity)e.get(0),new Silence((LivingEntity)e.get(0)), 60);
		} else {
			cooldown[2] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(sp > 0) {
			cooldown[3] = 0;
			return false;
		}
		ARSystem.playSound((Entity)player, "c159s3");
		s3 = 100;
		skill("c159_s3");
		s3lc = player.getLocation();
		s3lc.setPitch(0);
		s3list.clear();
		s3list.add(player);
		return true;
	}


	@Override
	public boolean skill4() {
		if(p2 > 2 && skillCooldown(0)) {
			spskillon();
			spskillen();
			p2 = 0;
			sp = 100;
			sp2 = 0;
			spl = player.getLocation();
			spl.setPitch(-5);
			ARSystem.playSound((Entity)player, "c159sp");
		}
		return true;
	}

	@Override
	public void LocmakerSkill(Location loc, String name) {
		if(spe != null && name.equals("saka")) {
			spe.setVelocity(new Vector(0,0,0));
			spe.teleport(loc);
		}
	}
	
	
	int spt = 0;
	
	@Override
	public boolean tick() {
		if(sskillmult + skillmult >= 1) {
			double t = (sskillmult + skillmult) - 1;
			sskillmult -= t;
			hp += t*10;
			player.setMaxHealth(hp);
			ARSystem.heal(player, t*10);
		}
		if(sp > 0) {
			player.teleport(spl);
			spt++;
			Entity en = ARSystem.boxSOne(player, new Vector(2,2,2), box.TARGET);
			if(en != null) {
				spt = 0;
				LivingEntity e = (LivingEntity)en;
				en.teleport(en.getLocation().clone().add(player.getLocation().getDirection()));
				e.setNoDamageTicks(0);
				e.damage(1,player);
				sp2++;
				ARSystem.playSound((Entity)player, "0attack"+(AMath.random(3)+1), 0.5f+AMath.random(10)*0.1f);
				ARSystem.giveBuff(e, new Fascination(e, player), 40, 0.3);
				if(sp2 >= 15) {
					sp = 0;
					ARSystem.giveBuff(player, new TimeStop(player), 60);
					spe = e;
					ARSystem.playSound((Entity)player, "c159sp2");
					skill("c159_sp");
				}
			}
			if(spt > 40) {
				sp = 0;
				spt = 0;
			}
			sp--;
		}
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c159:ps")+ "dfc] : " + p*30+"%");
			if(psopen) scoreBoardText.add("&c ["+Main.GetText("c159:sk0")+ "] : " + p2+" / 3");
		}
		if(s3 > 0) {
			for(Entity e : ARSystem.locEntity(s3lc, new Vector(7,7,7), player)) {
				if(ARSystem.isTarget(e, player, box.TARGET) && !s3list.contains(e)) {
					s3list.add(e);
				}
			}
			s3--;
			int j = s3%20;
			
			int i = 0;
			float rng = 2 + (s3list.size()*0.5f);
			
			for(Entity e : s3list) {
				ARSystem.giveBuff((LivingEntity)e,new Stun((LivingEntity)e), 2);
				ARSystem.giveBuff((LivingEntity)e,new Silence((LivingEntity)e), 2);
				Location l = s3lc.clone();
				l.setYaw((360/s3list.size()) * i);
				l = ULocal.offset(l, new Vector(rng,0.1+AMath.flip(j,5)*0.2,AMath.flip(j,10)*0.3));
				l = ULocal.lookAt(l, s3lc);
				e.teleport(l);
				i++;
			}
		}
		return true;
	}
	int tropy = 0;
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			
		} else {
			if(AMath.random(100) <= 20) {
				tropy++;
				if(tropy >= 5) Rule.playerinfo.get(this.player).tropy(159,1);
				e.setDamage(0);
				e.setCancelled(true);
				ARSystem.playSound((Entity)player, "0miss");
				Location lc = player.getLocation();
				lc.setPitch(0);
				ARSystem.heal(player, 2);
				player.teleport(ULocal.offset(lc, new Vector(-2,0,0)));
				p++;
				p2++;
			} else {
				e.setDamage(e.getDamage() * (1-0.3*p));
				p = 0;
				tropy = 0;
			}
		}
		return true;
	}

}
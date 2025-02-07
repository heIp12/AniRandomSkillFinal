package chars.c4;

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
import buff.Boom;
import buff.Buff;
import buff.Cindaella;
import buff.Curse;
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
import manager.AdvManager;
import manager.Bgm;
import types.BuffType;
import types.TargetMap;
import types.box;

import util.AMath;
import util.BlockUtil;
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.ULocal;
import util.MSUtil;
import util.Map;

public class c157sizuo extends c00main{
	
	boolean sp = false;
	Entity s2e;
	int s2 = 0;
	Location s2l;
	int s2t = 0;
	List<Entity> s2es = new ArrayList<Entity>();
	
	Entity s3e;
	int s3 = 0;
	boolean s3u = false;
	int tropy = 0;
	
	@Override
	public void setStack(float f) {
		s_kill = (int)f;
	}
	
	public c157sizuo(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 157;
		load();
		text();
		c = this;
	}

	@Override
	public boolean skill1() {
		if(s2 > 0 || s3 > 0) {
			cooldown[1] = 0;
			return true;
		}
		if(sp) {
			sp = false;
			skill("c157_s1-2");
			ARSystem.playSound((Entity)player, "c157s12");
			if(tropy <= 60) Rule.playerinfo.get(this.player).tropy(157,1);
			tropy = 0;
		} else {
			skill("c157_s1");
			ARSystem.playSound((Entity)player, "c157s1");
			for(Entity e : ARSystem.box(player, new Vector(5,2,5), box.TARGET)) {
				LivingEntity en = (LivingEntity)e;
				en.setNoDamageTicks(0);
				skill("c157_s1e");
				en.damage(3,player);
				en.setVelocity(ULocal.lookAt(player.getLocation().clone(), en.getLocation()).getDirection().multiply(2).setY(0.2));
			}
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(BlockUtil.isAirbone(player.getLocation(), 1) || s2 > 0 || s3 > 0) {
			cooldown[2] = 0;
			return true;
		}
		Location loc = player.getLocation();
		loc.setPitch(-30);
		player.setVelocity(loc.getDirection().multiply(1.2f).setY(0.8));
		s2 = 100;
		s2e = null;
		if(sp) {
			ARSystem.playSound((Entity)player, "c157s22");
			if(tropy <= 60) Rule.playerinfo.get(this.player).tropy(157,1);
			tropy = 0;
		} else {
			ARSystem.playSound((Entity)player, "c157s2");
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(BlockUtil.isAirbone(player.getLocation(), 1) || s2 > 0 || s3 > 0) {
			cooldown[3] = 0;
			return true;
		}
		s3e = null;
		s3 = 20;
		Location loc = player.getLocation();
		loc.setPitch(0);
		player.setVelocity(new Vector(0,-2,0));
		player.setVelocity(loc.getDirection().multiply(2.5f));
		ARSystem.playSound((Entity)player, "c157s3");
		return true;
	}

	@Override
	public boolean skill4() {
		if(s_kill >= 1 && skillCooldown(0)) {
			if(!isps) {
				spskillon();
				spskillen();
			}
			sp = true;
			ARSystem.playSound((Entity)player, "c157p");
			return true;
		}
		return true;
	}
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			target.damage(7,player);
			ARSystem.giveBuff(target, new Stun(target), 40);
		}
	}
	
	@Override
	public boolean firsttick() {
		if(Rule.buffmanager.GetBuffTime(player, "stun") > 0) {
			Rule.buffmanager.selectBuffTime(player, "stun", 0);
			Rule.buffmanager.buffs(player, "stun").stop();
		}
		return super.firsttick();
	}
	@Override
	public boolean tick() {
		tropy++;
		if(s3 > 0) {
			s3--;
			if(s3e == null) {
				s3e = ARSystem.boxSOne(player, new Vector(2,2,2), box.TARGET);
				if(s3e != null) {
					if(sp) {
						sp = false;
						s3 = 100100;
						if(tropy <= 60) Rule.playerinfo.get(this.player).tropy(157,1);
						tropy = 0;
					} else {
						s3 = 20;
					}
				}
			} else {
				if(s3 >= 1000) {
					ARSystem.giveBuff(player, new Noattack(player), 2);
					ARSystem.giveBuff((LivingEntity)s3e, new Silence((LivingEntity)s3e), 2);
					s3e.teleport(ULocal.lookAt(ULocal.offset(player.getLocation().clone(), new Vector(1,1.5,0)),player.getLocation()));

					if(player.isSneaking()) s3 = 0;
					if(s3%1000 <= 0) {
						ARSystem.playSound((Entity)player, "c157s32");
						s3e.setVelocity(player.getLocation().getDirection().multiply(6));
						player.setVelocity(new Vector(0,-3,0));
						s3u = true;
					}
				} else {
					ARSystem.giveBuff(player, new Noattack(player), 2);
					ARSystem.giveBuff((LivingEntity)s3e, new Silence((LivingEntity)s3e), 2);
					Location loc = player.getLocation().clone();
					loc.setPitch(0);
					loc.setYaw(player.getLocation().getYaw() + 36);
					player.teleport(loc);
					s3e.teleport(ULocal.lookAt(ULocal.offset(player.getLocation().clone(), new Vector(1,0,0)),player.getLocation()));
					if(player.isSneaking()) s3 = 0;
					if(s3 <= 0) {
						ARSystem.playSound((Entity)player, "c157s31");
						s3e.setVelocity(loc.getDirection().multiply(4).setY(-0.1));
						s3u = false;
					}
				}
			}
		} else if(s3e != null){
			if(s3e.isOnGround()) {
				Entity ey = s3e;
				s3e = null;
				if(s3u) {
					delay(()->{
						ARSystem.spellCast(player,ey,"c157_s3-2");
						for(Entity e : ARSystem.box(ey.getLocation(), player, new Vector(8,8,8), box.TARGET)) {
							LivingEntity en = (LivingEntity)e;
							en.setNoDamageTicks(0);
							en.damage(10,player);
							ARSystem.giveBuff(en, new Stun(en), 40);
						}
					},5);
				} else {
					delay(()->{
						ARSystem.spellCast(player,ey,"c157_s3");
						for(Entity e : ARSystem.box(ey.getLocation(), player, new Vector(4,4,4), box.TARGET)) {
							LivingEntity en = (LivingEntity)e;
							en.setNoDamageTicks(0);
							en.damage(5,player);
							ARSystem.giveBuff(en, new Stun(en), 40);
						}
					},5);
				}
			}
		}
		if(s2 > 0) {
			if(s2e == null) {
				s2e = ARSystem.boxSOne(player, new Vector(2,2,2), box.TARGET);
			} else {
				s2e.teleport(ULocal.offset(player.getLocation().clone(), new Vector(1,0,0)));
			}
			
			if(player.isOnGround()) {
				s2 = 0;
				if(s2e == null) s2e = player;
				
				ARSystem.spellCast(player,s2e,"c157_s2");
				Location loc = s2e.getLocation().clone();
				loc.setY(player.getLocation().getY());
				s2e.teleport(s2e);
				for(Entity e : ARSystem.box(s2e.getLocation(), player, new Vector(5,2,5), box.TARGET)) {
					LivingEntity en = (LivingEntity)e;
					en.setNoDamageTicks(0);
					en.damage(3,player);
					ARSystem.giveBuff(en, new Stun(en), 20);
				}
				if(sp) {
					sp = false;
					s2es.clear();
					s2l = s2e.getLocation();
					s2l.setPitch(0);
					ARSystem.spellLocCast(player,s2l,"c157_s2-1");
					s2es.add(s2e);
					for(Entity e : ARSystem.box(s2e.getLocation(), player, new Vector(8,5,8), box.TARGET)) {
						if(e != s2e && e != player) s2es.add(e);
					}
					s2t = 100;
				}
			}
		}
		if(s2t > 0) {
			s2t--;
			for(Entity e : s2es) {
				Location l = e.getLocation().clone();
				l.setY(s2l.getY());
				if(l.distance(s2l) > 8) {
					Vector v = e.getVelocity().clone();
					v.setX(v.getX()*-1);
					v.setZ(v.getZ()*-1);
					Location loc = ULocal.offset(ULocal.lookAt(s2l.clone(), l),new Vector(8,0,0));
					loc.setY(e.getLocation().getY());
					loc.setYaw(l.getYaw()+180);
					e.teleport(loc);
					e.setVelocity(v);
					delay(()->{e.setVelocity(v);},0);
				}
			}
		}
		if(tk%10 == 0) {
			ARSystem.heal(player, 0.15f);
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(e.getDamage() < 2) {
				e.setDamage(2);
				((LivingEntity)e.getEntity()).setVelocity(player.getLocation().getDirection().multiply(3));
			}
		} else {
			if(isps && cooldown[0] > 0) cooldown[0] -= e.getDamage()*3;
			
			e.setDamage(e.getDamage() * 0.75);
		}
		return true;
	}
	
}

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
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
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
import buff.ArmorUp;
import buff.Boom;
import buff.Buff;
import buff.Cindaella;
import buff.Curse;
import buff.Exposure;
import buff.Ice;
import buff.NoHeal;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.PowerUp;
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
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.ULocal;
import util.MSUtil;
import util.Map;
import util.NpcPlayer;
import util.Text;

public class c158ako extends c00main{
	int s1 = 1,s1t = 0,s2 = 0, s3 = 0, s4 = 0;
	int s2t = 0,dt = 0;
	
	Location loc;
	
	public c158ako(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 158;
		load();
		text();
		c = this;
	}

	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c158s1"+s1);
		skill("c158_s1-"+s1);

		List<Entity> el = ARSystem.box(player, new Vector(6,1,6), box.ALL);
		for(Entity e : el) {
			s1t = 60;
			LivingEntity en = (LivingEntity)e;
			en.setNoDamageTicks(0);
			if(ARSystem.isTarget(e,player, box.TEAM)) {
				en.damage(1.2,NpcPlayer.npc(e.getLocation()));
			} else {
				en.damage(1.2,player);
			}
			if(s1 == 3) {
				ARSystem.giveBuff(en, new Silence(en), 20);
				en.setVelocity(new Vector(0,0.76f,0));
			} else {
				en.setVelocity(ULocal.lookAt(en.getLocation().clone(), player.getLocation().clone().add(0,1,0)).getDirection().multiply(0.2));
			}
		}

		if(el.size() > 0) s1++;
		if(s1 == 4) s1 = 1;
		return true;
	}
	
	@Override
	public boolean skill2() {
		s2 = 10;
		return true;
	}
	
	@Override
	public boolean skill3() {
		s3 = 10;
		return true;
	}

	@Override
	public boolean skill4() {
		if(player.isSneaking()) {
			Location l = player.getLocation();
			List<Entity> e = ARSystem.PlayerBeamBox(player, 10, 3, box.TARGET);
			if(e.size() > 0 && (getScore() >= 1000 || Rule.playerinfo.get(player).gold >= 3000) && skillCooldown(0)) {
				spskillon();
				spskillen();
				if(getScore() >= 1000) {
					s_score -= 1000;
				} else {
					Rule.playerinfo.get(player).gold -= 3000;
				}
				ARSystem.playSound((Entity)player, "c158sp");
				LivingEntity lv = (LivingEntity)e.get(0);
				ARSystem.giveBuff(player, new Nodamage(player), 200);
				ARSystem.giveBuff(player, new Silence(player), 200);
				ARSystem.giveBuff(player, new Stun(player), 200);
				delay(()->{
					plash(lv.getLocation(),lv.getLocation());
					delay(()->{
						ARSystem.spellCast(player, "item37");
						ARSystem.playSound((Entity)player ,"0timer",0.5f,2);
						for(Entity et : ARSystem.box(player, new Vector(10,8,10), box.TARGET)) {
							delay(()->{
								LivingEntity es = (LivingEntity)et;
								ARSystem.giveBuff(es, new Timeshock(es), 140);
							},10);
						}
						delay(()->{
							mimi();
						},15);
						delay(()->{
							Location lc = lv.getLocation().clone();
							lc.setYaw(AMath.random(360));
							plash(lc,lv.getLocation());
							delay(()->{
								mimi();
							},2);
						},40);
						delay(()->{
							Location lc = lv.getLocation().clone();
							lc.setYaw(AMath.random(360));
							plash(lc,lv.getLocation());
							delay(()->{
								mimi();
							},2);
						},60);
						delay(()->{
							Location lc = lv.getLocation().clone();
							lc.setYaw(AMath.random(360));
							plash(lc,lv.getLocation());
							delay(()->{
								mimi();
							},2);
						},80);
						
						delay(()->{
							plash(l,null);
							ARSystem.playSound((Entity)player, "item53");
							ARSystem.playSound(lv, "item53");
							delay(()->{
								ARSystem.spellCast(player, lv, "item53");
								ARSystem.playSound((Entity)player, "boom2");
								LivingEntity en = (LivingEntity)lv;
								en.setNoDamageTicks(0);
								en.damage(10,player);
								for(Entity ey : ARSystem.box(lv, new Vector(8,8,8), box.ALL)) {
									if(ARSystem.isTarget(ey, player, box.TARGET)) {
										en = (LivingEntity)ey;
										en.setNoDamageTicks(0);
										en.damage(10,player);
										
										ARSystem.spellLocCast(player, ey.getLocation(), "item126");
										delay(()->{
											for(Entity et : ARSystem.locEntity(ey.getLocation(), new Vector(6,3,6), player)) {
												if(ARSystem.isTarget(et, player, box.TARGET)) {
													LivingEntity ens = (LivingEntity)et;
													ens.setNoDamageTicks(0);
													ens.damage(10,player);
												}
											}
										},8);
									}
								}
								ARSystem.spellLocCast(player, lv.getLocation(), "item126");
								delay(()->{
									for(Entity et : ARSystem.locEntity(lv.getLocation(), new Vector(6,3,6), player)) {
										if(ARSystem.isTarget(et, player, box.TARGET)) {
											LivingEntity ens = (LivingEntity)et;
											ens.setNoDamageTicks(0);
											ens.damage(10,player);
										}
									}
								},8);
							},120);
						}, 100);
					},5);
				},40);
			} else {
				s4 = 10;
			}
		} else {
			s4 = 10;
		}
		return true;
	}
	void plash(Location e,Location look) {
		ARSystem.playSound((Entity)player, "item44");
		ARSystem.spellCast(player, "item44");
		player.teleport(ULocal.offset(e, new Vector(2,0,0)));
		if(look != null) player.teleport(ULocal.lookAt(player.getLocation(), look));
		ARSystem.spellCast(player, "item44");
	}
	void mimi() {
		ARSystem.spellCast(player, "item28");
		for(Entity ey : ARSystem.box(player, new Vector(8,4,8), box.TARGET)) {
			LivingEntity en = (LivingEntity)ey;
			ARSystem.giveBuff(en, new Stun(en), 15);
			ARSystem.giveBuff(en, new Silence(en), 15);
			delay(()->{
				if(en instanceof Player) {
					ARSystem.spellCast((Player)en, "item28e");
				}
				en.setNoDamageTicks(0);
				en.damage(10,player);
				ARSystem.heal(player, 6);
				ARSystem.giveBuff(en, new PowerUp(en), 60, -0.5f);
			},10);
		}
	}
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			target.damage(2,player);
			ARSystem.giveBuff(target, new Stun(target), 40);
		}
		if(n.equals("2")) {
			target.setNoDamageTicks(0);
			target.damage(2,player);
			ARSystem.giveBuff(target, new Ice(target,player), 30);
		}
		if(n.equals("3")) {
			target.setNoDamageTicks(0);
			target.damage(5,player);
			target.setVelocity(loc.getDirection().multiply(2).setY(0.5));
		}
	}
	int tropy = 0;
	@Override
	public boolean key(PlayerItemHeldEvent e) {
		int key = e.getNewSlot()+1;
		if(key < 4 && key > 0) {
			if(s2 + s3 + s4 > 0) {
				if(s2 > 0) {
					s2 = 0;
					s2t = 40;
					ARSystem.playSound((Entity)player, "c158s2"+key);
					skill("c158_s2-"+key);
					loc = player.getLocation().clone();
					loc.setPitch(-20);
					if(key == 3) {
						ARSystem.giveBuff(player, new Silence(player), 40);
						ARSystem.giveBuff(player, new Stun(player), 10);
						delay(()->{
							ARSystem.giveBuff(player, new ArmorUp(player), 30, 0.95);
						},10);
					}
				} else if(s3 > 0) {
					s3 = 0;
					ARSystem.playSound((Entity)player, "c158s3"+key);
					Entity target = null;
					if(player.isSneaking()) {
						target = ARSystem.boxSOne(player, new Vector(15,15,15), box.TEAM);
						if(target == null) {
							target = ARSystem.boxSOne(player, new Vector(25,25,25), box.TARGET);
						}
					} else {
						target = player;
					}
					if(AMath.random(10) <= 3) {
						tropy++;
						Entity ey = ARSystem.boxSOne(player, new Vector(25,25,25), box.TARGET);
						if(ey != null) {
							Holo.create(player.getLocation(), Text.get("c158:t1"), 40, new Vector(0,0,0));
							target = ey;
						}
						if(tropy >= 3) Rule.playerinfo.get(this.player).tropy(158,1);
					} else {
						tropy = 0;
					}
					if(target == null) target = player;
					ARSystem.spellCast(player, target, "c158_s3-"+key);
					LivingEntity en = (LivingEntity)target;
					if(key == 1) {
						ARSystem.heal(en, 10);
					} else if(key == 2) {
						ARSystem.giveBuff(en, new Exposure(en), 200, -3);
						ARSystem.potion(en, 1, 200, 2);
					} else if(key == 3) {
						ARSystem.giveBuff(en, new PowerUp(en), 200, 0.5f);
					}
				} else if(s4 > 0) {
					s4 = 0;
					if(player.isSneaking() && (Rule.playerinfo.get(player).gold > 3000 ||(score+s_score-200)>1000) && skillCooldown(0)) {
						if(Rule.playerinfo.get(player).gold > 3000) {
							Rule.playerinfo.get(player).gold -= 3000;
						} else {
							score -= 1000;
						}

						skill("c158_sp");
						ARSystem.playSound((Entity)player, "c158sp");
					} else {
						ARSystem.playSound((Entity)player, "c158s4"+key);
						skill("c158_s4-"+key);
						if(key == 3) cooldown[4] += setcooldown[4];
					}
				}
				player.getInventory().setHeldItemSlot(7);
				return false;
			}
		}
		
		return super.key(e);
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(e == player && p != player) {
			ARSystem.playSound((Entity)player, "c158p");
			if(s2t > 0) {
				s2t = 0;
				delay(()->{
					ARSystem.playSound(player, "c158rkill");
				},15);
			}
		}
		super.PlayerDeath(p, e);
	}

	@Override
	public boolean tick() {
		if(s1t > 0) {
			s1t--;
			if(s1t == 0) s1 = 1;
		}
		if(Rule.buffmanager.GetBuffTime(player, "panic") > 0) {
			if(dt <= 0) {
				dt = 100;
				ARSystem.playSound((Entity)player, "c158dbf2");
			}
		}
		if(Rule.buffmanager.GetBuffTime(player, "fascination") > 0) {
			if(dt <= 0) {
				dt = 100;
				ARSystem.playSound((Entity)player, "c158dbf1");
				ARSystem.giveBuff(player, new Panic(player), Rule.buffmanager.GetBuffTime(player, "fascination"));
				Rule.buffmanager.selectBuffTime(player, "fascination", 0);
			}
		}
		if(s2 > 0) {
			s2--;
			if(s2 == 0) cooldown[2] = 0;
		}
		if(s3 > 0) {
			s3--;
			if(s3 == 0) cooldown[3] = 0;
		}
		if(s4 > 0) {
			s4--;
			if(s4 == 0) cooldown[4] = 0;
		}
		if(s2t > 0) s2t--;
		if(dt > 0) dt--;
		return true;
	}

	@Override
	public boolean damage(EntityDamageEvent e) {
		if(e.getCause() != DamageCause.ENTITY_ATTACK) {
			e.setDamage(e.getDamage()*2.5);
			return true;
		}
		return super.damage(e);
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			
		} else {
			
		}
		return true;
	}
	
}
